package at.meroff.itproject.service;

import at.meroff.itproject.domain.Subject;
import at.meroff.itproject.domain.enumeration.LvaType;
import at.meroff.itproject.domain.enumeration.SubjectType;
import at.meroff.itproject.service.dto.*;
import at.meroff.itproject.xml.XMLQueryTemplate;
import at.meroff.itproject.xml.models.Subjects;
import at.meroff.itproject.xml.models.lvas.CourseDate;
import at.meroff.itproject.xml.models.lvas.XmlLvas;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ImportService {

    private final Logger log = LoggerFactory.getLogger(ImportService.class);

    private final CurriculumSemesterService curriculumSemesterService;
    private final SubjectService subjectService;
    private final CurriculumSubjectService curriculumSubjectService;
    private final LvaService lvaService;
    private final AppointmentService appointmentService;
    private final InstituteService instituteService;
    private final ResourceLoader resourceLoader;

    public ImportService(CurriculumSemesterService curriculumSemesterService,
                         SubjectService subjectService,
                         ResourceLoader resourceLoader,
                         CurriculumSubjectService curriculumSubjectService,
                         LvaService lvaService,
                         AppointmentService appointmentService,
                         InstituteService instituteService) {
        this.curriculumSemesterService = curriculumSemesterService;
        this.subjectService = subjectService;
        this.curriculumSubjectService = curriculumSubjectService;
        this.lvaService = lvaService;
        this.resourceLoader = resourceLoader;
        this.appointmentService = appointmentService;
        this.instituteService = instituteService;
    }

    public Set<SubjectDTO> verifySubjects(CurriculumSemesterDTO curriculumSemesterDTO) {

        if (curriculumSemesterDTO.getCurriculumCurId() == null) {
            curriculumSemesterDTO = curriculumSemesterService.findOne(curriculumSemesterDTO.getId());
        }

        Resource resource = resourceLoader.getResource("classpath:xquery/subjects.xq");
        BufferedReader reader = null;

        Set<SubjectDTO> ret = new HashSet<>();
        try {
            reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            String query = "";
            for (String line; (line = reader.readLine()) != null; query += (line + "\n"));

            query = query.replace("INSERTCURID", Integer.toString(curriculumSemesterDTO.getCurriculumCurId()));
            String querySemester = curriculumSemesterDTO.getYear() + curriculumSemesterDTO.getSemester().name().substring(0,1);
            query = query.replace("INSERTYEARSEMESTER", querySemester);

            XMLQueryTemplate<Subjects> xmlQuerySubjects = new XMLQueryTemplate<>(query, Subjects.class);
            Subjects subjects = xmlQuerySubjects.getResult();

            CurriculumSemesterDTO finalCurriculumSemesterDTO = curriculumSemesterDTO;
            ret = subjects.getSubjects().stream()
                .map(this::getSubjectDTO)
                .peek(subjectDTO -> createCurriculumSubject(finalCurriculumSemesterDTO, subjectDTO))
                .collect(Collectors.toSet());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    private SubjectDTO getSubjectDTO(Subject subject) {
        SubjectDTO bySubjectNameAndSubjectType = subjectService.findBySubjectNameAndSubjectType(subject.getSubjectName(), subject.getSubjectType());
        if (bySubjectNameAndSubjectType != null) {
            return bySubjectNameAndSubjectType;
        } else {
            SubjectDTO insert = new SubjectDTO();
            insert.setSubjectName(subject.getSubjectName());
            insert.setSubjectType(subject.getSubjectType());
            return subjectService.save(insert);
        }
    }

    private void createCurriculumSubject(CurriculumSemesterDTO finalCurriculumSemesterDTO, SubjectDTO subjectDTO) {
        CurriculumSubjectDTO cs = new CurriculumSubjectDTO();
        cs.setCurriculumSemesterId(finalCurriculumSemesterDTO.getId());
        cs.setSubjectId(subjectDTO.getId());
        curriculumSubjectService.save(cs);
    }

    public Set<LvaDTO> verifyLvas(CurriculumSemesterDTO curriculumSemesterDTO) {

        if (curriculumSemesterDTO.getCurriculumCurId() == null) {
            curriculumSemesterDTO = curriculumSemesterService.findOne(curriculumSemesterDTO.getId());
        }

        Resource resource = resourceLoader.getResource("classpath:xquery/lvas.xq");
        BufferedReader reader = null;

        Set<LvaDTO> ret = new HashSet<>();
        try {
            reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            String query = "";
            for (String line; (line = reader.readLine()) != null; query += (line + "\n"));

            query = query.replace("INSERTCURID", Integer.toString(curriculumSemesterDTO.getCurriculumCurId()));
            String querySemester = curriculumSemesterDTO.getYear() + curriculumSemesterDTO.getSemester().name().substring(0,1);
            query = query.replace("INSERTYEARSEMESTER", querySemester);

            XMLQueryTemplate<XmlLvas> xmlQuerySubjects = new XMLQueryTemplate<>(query, XmlLvas.class);
            XmlLvas lvas = xmlQuerySubjects.getResult();

            Map<Pair<String, SubjectType>, CurriculumSubjectDTO> collect = curriculumSemesterDTO.getCurriculumSubjects().stream()
                .collect(Collectors.toMap(c -> {
                   return new Pair<>(c.getSubjectSubjectName(), c.getSubjectSubjectType());

                }, c -> c));

            lvas.getLvas().forEach(xmlLva -> {
                CurriculumSubjectDTO curriculumSubjectDTO = collect.get(new Pair<>(xmlLva.getName(), SubjectType.valueOf(xmlLva.getSubjectType())));
                LvaDTO lvaDTO = new LvaDTO();
                lvaDTO.setLvaNr(xmlLva.getId());
                lvaDTO.setYear(xmlLva.getTermYear());
                lvaDTO.setSemester(xmlLva.getTermSemester());
                lvaDTO.setLvaType(LvaType.WEEKLY);
                lvaDTO.setSubjectId(curriculumSubjectDTO.getSubjectId());
                lvaDTO.setInstituteId(instituteService.findByInstituteId(Integer.parseInt(xmlLva.getId().substring(0,3))).getId());
                lvaDTO = lvaService.save(lvaDTO);
                int countAppointments = createAppointments(xmlLva.getCourseDates(), lvaDTO);
                lvaDTO.setCountAppointments(countAppointments);
                lvaDTO = lvaService.save(lvaDTO);
                ret.add(lvaDTO);
                curriculumSubjectDTO.getLvas().add(lvaDTO);
            });

            collect.forEach((stringSubjectTypePair, curriculumSubjectDTO) -> curriculumSubjectService.save(curriculumSubjectDTO));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    private int createAppointments(Set<CourseDate> courseDates, LvaDTO lvaDTO) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        Set<AppointmentDTO> collect = courseDates.stream().map(courseDate -> {
            AppointmentDTO appointmentDTO = new AppointmentDTO();
            appointmentDTO.setStartDateTime(
                ZonedDateTime.of(LocalDateTime.of(
                    LocalDate.parse(courseDate.getDate(), dateFormat), LocalTime.parse(courseDate.getTimebegin(), timeFormat)
                ), ZoneId.of("GMT+2"))
            );
            appointmentDTO.setEndDateTime(
                ZonedDateTime.of(LocalDateTime.of(
                    LocalDate.parse(courseDate.getDate(), dateFormat), LocalTime.parse(courseDate.getTimeend(), timeFormat)
                ), ZoneId.of("GMT+2"))
            );
            appointmentDTO.setRoom(courseDate.getLocation());
            appointmentDTO.setTheme(courseDate.getTheme());
            if (courseDate.getTheme().toLowerCase().matches(".*klausur.*|.*test.*")) {
                if (courseDate.getTheme().toLowerCase().matches(".*einsicht.*|.*vorbereitung.*|.*frage.*")) {
                    appointmentDTO.setIsExam(false);
                } else {
                    appointmentDTO.setIsExam(true);
                }
            } else {
                appointmentDTO.setIsExam(false);
            }

            appointmentDTO.setLvaId(lvaDTO.getId());
            return appointmentService.save(appointmentDTO);
        }).collect(Collectors.toSet());

        return collect.size();
    }

}
