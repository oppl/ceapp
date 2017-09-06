package at.meroff.itproject.service;

import at.meroff.itproject.domain.Subject;
import at.meroff.itproject.domain.enumeration.LvaType;
import at.meroff.itproject.domain.enumeration.SubjectType;
import at.meroff.itproject.helper.Pair;
import at.meroff.itproject.repository.CurriculumSemesterRepository;
import at.meroff.itproject.service.dto.*;
import at.meroff.itproject.service.mapper.CurriculumSemesterMapper;
import at.meroff.itproject.xml.XMLQueryTemplate;
import at.meroff.itproject.xml.models.Subjects;
import at.meroff.itproject.xml.models.lvas.CourseDate;
import at.meroff.itproject.xml.models.lvas.XmlLvas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

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
//@Transactional
public class ImportService {

    private final Logger log = LoggerFactory.getLogger(ImportService.class);

    // private final CurriculumSemesterService curriculumSemesterService;
    private final CurriculumSemesterRepository curriculumSemesterRepository;
    private final SubjectService subjectService;
    private final CurriculumSubjectService curriculumSubjectService;
    private final LvaService lvaService;
    private final AppointmentService appointmentService;
    private final InstituteService instituteService;
    private final ResourceLoader resourceLoader;
    private final CurriculumSemesterMapper curriculumSemesterMapper;

    public ImportService(// CurriculumSemesterService curriculumSemesterService,
                         CurriculumSemesterRepository curriculumSemesterRepository,
                         CurriculumSemesterMapper curriculumSemesterMapper,
                         SubjectService subjectService,
                         ResourceLoader resourceLoader,
                         CurriculumSubjectService curriculumSubjectService,
                         LvaService lvaService,
                         AppointmentService appointmentService,
                         InstituteService instituteService) {
        // this.curriculumSemesterService = curriculumSemesterService;
        this.subjectService = subjectService;
        this.curriculumSubjectService = curriculumSubjectService;
        this.lvaService = lvaService;
        this.resourceLoader = resourceLoader;
        this.appointmentService = appointmentService;
        this.instituteService = instituteService;
        this.curriculumSemesterRepository = curriculumSemesterRepository;
        this.curriculumSemesterMapper = curriculumSemesterMapper;
    }

    public CurriculumSemesterDTO verifySubjects(CurriculumSemesterDTO curriculumSemesterDTO) {

        //if (curriculumSemesterDTO.getCurriculumCurId() == null) {
        //    curriculumSemesterDTO = curriculumSemesterService.findOne(curriculumSemesterDTO.getId());
        //}

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
                .peek(subjectDTO -> {
                    finalCurriculumSemesterDTO.getCurriculumSubjects().add(createCurriculumSubject(finalCurriculumSemesterDTO, subjectDTO));
                    ;
                })
                .collect(Collectors.toSet());
            return finalCurriculumSemesterDTO;
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
        return null;
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

    private CurriculumSubjectDTO createCurriculumSubject(CurriculumSemesterDTO finalCurriculumSemesterDTO, SubjectDTO subjectDTO) {
        CurriculumSubjectDTO cs = new CurriculumSubjectDTO();
        cs.setCurriculumSemesterId(finalCurriculumSemesterDTO.getId());
        cs.setCountLvas(0);
        cs.setSubjectId(subjectDTO.getId());
        cs.setSubjectSubjectName(subjectDTO.getSubjectName());
        cs.setSubjectSubjectType(subjectDTO.getSubjectType());
        return curriculumSubjectService.findOne(curriculumSubjectService.save(cs).getId());
    }

    public Set<LvaDTO> verifyLvas(CurriculumSemesterDTO curriculumSemesterDTO) {

        //if (curriculumSemesterDTO.getCurriculumCurId() == null) {
        //    curriculumSemesterDTO = curriculumSemesterService.findOne(curriculumSemesterDTO.getId());
        //}

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
                    SubjectDTO a = subjectService.findOne(c.getSubjectId());
                    return new Pair<>(a.getSubjectName(), a.getSubjectType());

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
                int countAppointments = createAppointments(xmlLva.getCourseDates(), lvaDTO, xmlLva.getName(), SubjectType.valueOf(xmlLva.getSubjectType()));
                lvaDTO.setCountAppointments(countAppointments);
                lvaDTO = lvaService.save(lvaDTO);
                ret.add(lvaDTO);
                curriculumSubjectDTO.setCountLvas(curriculumSubjectDTO.getCountLvas() + 1);
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

    private int createAppointments(Set<CourseDate> courseDates, LvaDTO lvaDTO, String subjectSubjectName, SubjectType subjectSubjectType) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        Set<AppointmentDTO> collect = courseDates.stream().map(courseDate -> {
            AppointmentDTO appointmentDTO = new AppointmentDTO();
            appointmentDTO.setStartDateTime(
                ZonedDateTime.of(LocalDateTime.of(
                    LocalDate.parse(courseDate.getDate(), dateFormat), LocalTime.parse(courseDate.getTimebegin(), timeFormat)
                ), ZoneId.systemDefault())
            );
            appointmentDTO.setEndDateTime(
                ZonedDateTime.of(LocalDateTime.of(
                    LocalDate.parse(courseDate.getDate(), dateFormat), LocalTime.parse(courseDate.getTimeend(), timeFormat)
                ), ZoneId.systemDefault())
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
            String subs = "";
            if (subjectSubjectName.length() <= 10) {
                subs = subjectSubjectName;
            } else {
                subs = subjectSubjectName.substring(0,9);
            }
            appointmentDTO.setTitle(lvaDTO.getLvaNr() + " " + subs + " " + subjectSubjectType);
            return appointmentService.save(appointmentDTO);
        }).collect(Collectors.toSet());

        return collect.size();
    }

}
