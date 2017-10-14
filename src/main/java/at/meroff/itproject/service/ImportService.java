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

    /**
     * Method checks if all subjects for a curriculum semester are available
     * @param curriculumSemesterDTO curriculum semester to be verified
     * @return modified CurriculumSemester
     */
    public CurriculumSemesterDTO verifySubjects(CurriculumSemesterDTO curriculumSemesterDTO) {

        BufferedReader reader = null;
        Resource resource = resourceLoader.getResource("classpath:xquery/subjects.xq");

        try {
            reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            // read query stream
            String query = "";
            for (String line; (line = reader.readLine()) != null; query += (line + "\n"));

            // replace parameters
            query = query.replace("INSERTCURID", Integer.toString(curriculumSemesterDTO.getCurriculumCurId()));
            query = query.replace("INSERTYEARSEMESTER", curriculumSemesterDTO.getYear()
                + curriculumSemesterDTO.getSemester().name().substring(0,1));

            // transform xquery result into Subjects
            XMLQueryTemplate<Subjects> xmlQuerySubjects = new XMLQueryTemplate<>(query, Subjects.class);
            Subjects subjects = xmlQuerySubjects.getResult();

            // add database subjects to the curriculum subject
            subjects.getSubjects().stream()
                .map(this::getSubjectDTO) // mapping
                .forEach(subjectDTO -> curriculumSemesterDTO
                    .getCurriculumSubjects()
                    .add(createCurriculumSubject(curriculumSemesterDTO, subjectDTO)));

            // return the modified curriculum semester
            return curriculumSemesterDTO;

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

    /**
     * Method to transform XML subject into database subject
     * @param subject XML subject to transform into db subject
     * @return database entity of subject
     */
    private SubjectDTO getSubjectDTO(Subject subject) {
        SubjectDTO dbSubject = subjectService.findBySubjectNameAndSubjectType(subject.getSubjectName(), subject.getSubjectType());

        // return existing subject or create a new one
        if (dbSubject == null) {
            SubjectDTO insert = new SubjectDTO();
            insert.setSubjectName(subject.getSubjectName());
            insert.setSubjectType(subject.getSubjectType());
            return subjectService.save(insert);
        } else {
            return dbSubject;
        }
    }

    /**
     * Method create curriculum subjects for a given semester by linking an existing subject
     * @param curriculumSemesterDTO
     * @param subjectDTO
     * @return newly created curriculum subject
     */
    private CurriculumSubjectDTO createCurriculumSubject(CurriculumSemesterDTO curriculumSemesterDTO, SubjectDTO subjectDTO) {
        CurriculumSubjectDTO curriculumSubjectDTO = new CurriculumSubjectDTO();

        // check if a curriculumsubject allready exists


        // define new curriculum subject
        curriculumSubjectDTO.setCurriculumSemesterId(curriculumSemesterDTO.getId());
        curriculumSubjectDTO.setCountLvas(0);
        curriculumSubjectDTO.setSubjectId(subjectDTO.getId());
        curriculumSubjectDTO.setSubjectSubjectName(subjectDTO.getSubjectName());
        curriculumSubjectDTO.setSubjectSubjectType(subjectDTO.getSubjectType());

        return curriculumSubjectService.save(curriculumSubjectDTO);
    }

    public void verifyLvas(CurriculumSemesterDTO curriculumSemesterDTO) {

        Resource resource = resourceLoader.getResource("classpath:xquery/lvas.xq");
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            String query = "";
            for (String line; (line = reader.readLine()) != null; query += (line + "\n"));

            query = query.replace("INSERTCURID", Integer.toString(curriculumSemesterDTO.getCurriculumCurId()));
            query = query.replace("INSERTYEARSEMESTER", curriculumSemesterDTO.getYear()
                + curriculumSemesterDTO.getSemester().name().substring(0,1));

            XMLQueryTemplate<XmlLvas> xmlQuerySubjects = new XMLQueryTemplate<>(query, XmlLvas.class);
            XmlLvas lvas = xmlQuerySubjects.getResult();

            Map<Pair<String, SubjectType>, CurriculumSubjectDTO> collect = getPairCurriculumSubjectDTOMap(curriculumSemesterDTO);

            lvas.getLvas().forEach(xmlLva -> {
                CurriculumSubjectDTO curriculumSubjectDTO = collect.get(new Pair<>(xmlLva.getName(), SubjectType.valueOf(xmlLva.getSubjectType())));

                // check if LVA already exists
                LvaDTO lva = lvaService.findByLvaNrAndYearAndSemester(xmlLva.getId(), xmlLva.getTermYear(), xmlLva.getTermSemester());

                boolean newLva = false;

                if (lva == null) {
                    newLva = true;
                    lva = new LvaDTO();
                    lva.setLvaNr(xmlLva.getId()); // set the LVA number
                    lva.setYear(xmlLva.getTermYear()); // set the year
                    lva.setSemester(xmlLva.getTermSemester()); //set the semester
                    lva.setLvaType(LvaType.WEEKLY); // set the type
                    lva.setSubjectId(curriculumSubjectDTO.getSubjectId()); // set the linked subject
                    lva.setInstituteId(instituteService.findByInstituteId(Integer.parseInt(xmlLva.getId().substring(0,3))).getId()); // set the institute
                    lva.setCountAppointments(0);
                    lva = lvaService.save(lva);
                }

                Set<AppointmentDTO> newAppointments = createAppointments(xmlLva.getCourseDates(), lva, xmlLva.getName(), SubjectType.valueOf(xmlLva.getSubjectType()));

                Set<AppointmentDTO> appointments = lva.getAppointments();


                int countAppointments = newAppointments.size();

                if (appointments.hashCode() != newAppointments.hashCode() || newLva) {
                    lva.setCountAppointments(countAppointments);
                    appointments.forEach(appointmentDTO -> appointmentService.delete(appointmentDTO.getId()));
                    newAppointments.forEach(appointmentDTO -> appointmentService.save(appointmentDTO));
                    lva.setAppointments(newAppointments);
                    lva = lvaService.save(lva);

                }

                if (newLva) {
                    curriculumSubjectDTO.setCountLvas(curriculumSubjectDTO.getCountLvas() + 1);
                    curriculumSubjectDTO.getLvas().add(lva);
                }

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
    }

    private Map<Pair<String, SubjectType>, CurriculumSubjectDTO> getPairCurriculumSubjectDTOMap(CurriculumSemesterDTO curriculumSemesterDTO) {
        return curriculumSemesterDTO.getCurriculumSubjects().stream()
                    .collect(Collectors.toMap(c -> {
                        SubjectDTO a = subjectService.findOne(c.getSubjectId());
                        return new Pair<>(a.getSubjectName(), a.getSubjectType());

                    }, c -> c));
    }

    private Set<AppointmentDTO> createAppointments(Set<CourseDate> courseDates, LvaDTO lvaDTO, String subjectSubjectName, SubjectType subjectSubjectType) {

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
            if (courseDate.getTheme().toLowerCase().matches(".*klausur.*|.*test.*|.*prüfung.*|.*exam.*|.*assessment.*")) {
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
            return appointmentDTO;
        }).collect(Collectors.toSet());

        return collect;
    }

}
