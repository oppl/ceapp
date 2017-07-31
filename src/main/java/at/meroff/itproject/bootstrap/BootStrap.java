package at.meroff.itproject.bootstrap;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.domain.enumeration.Semester;
import at.meroff.itproject.domain.enumeration.SubjectType;
import at.meroff.itproject.repository.*;
import at.meroff.itproject.service.*;
import at.meroff.itproject.service.dto.*;
import at.meroff.itproject.service.mapper.CurriculumMapper;
import at.meroff.itproject.service.mapper.CurriculumSemesterMapper;
import at.meroff.itproject.service.mapper.IdealPlanMapper;
import at.meroff.itproject.service.mapper.InstituteMapper;
import at.meroff.itproject.xml.XMLQueryTemplate;
import at.meroff.itproject.xml.models.Subjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by fragner on 09.07.17.
 */
@Component
public class BootStrap implements ApplicationListener<ContextRefreshedEvent>{

    private final Logger log = LoggerFactory.getLogger(BootStrap.class);

    private CurriculumService curriculumService;
    private InstituteService instituteService;
    private CurriculumMapper curriculumMapper;
    private InstituteMapper instituteMapper;
    private SubjectRepository subjectRepository;
    private IdealPlanEntriesRepository idealPlanEntriesRepository;
    private CurriculumSubjectRepository curriculumSubjectRepository;
    private ResourceLoader resourceLoader;
    private CurriculumSemesterService curriculumSemesterService;
    private CurriculumSemesterMapper curriculumSemesterMapper;
    private ElasticsearchIndexService elasticsearchIndexService;
    private ImportService importService;
    private IdealPlanService idealPlanService;
    private IdealPlanEntriesService idealPlanEntriesService;
    private CollisionService collisionService;
    private IdealPlanMapper idealPlanMapper;

    public BootStrap(CurriculumService curriculumService,
                     CurriculumMapper curriculumMapper,
                     InstituteService instituteService,
                     InstituteMapper instituteMapper,
                     SubjectRepository subjectRepository,
                     IdealPlanService idealPlanService,
                     IdealPlanEntriesRepository idealPlanEntriesRepository,
                     CurriculumSubjectRepository curriculumSubjectRepository,
                     ResourceLoader resourceLoader,
                     CurriculumSemesterService curriculumSemesterService,
                     CurriculumSemesterMapper curriculumSemesterMapper,
                     ElasticsearchIndexService elasticsearchIndexService,
                     ImportService importService,
                     IdealPlanEntriesService idealPlanEntriesService,
                     CollisionService collisionService,
                     IdealPlanMapper idealPlanMapper) {
        this.curriculumService = curriculumService;
        this.curriculumMapper = curriculumMapper;
        this.instituteService = instituteService;
        this.instituteMapper = instituteMapper;
        this.subjectRepository = subjectRepository;
        this.idealPlanEntriesRepository = idealPlanEntriesRepository;
        this.curriculumSubjectRepository = curriculumSubjectRepository;
        this.resourceLoader = resourceLoader;
        this.curriculumSemesterService = curriculumSemesterService;
        this.curriculumSemesterMapper = curriculumSemesterMapper;
        this.elasticsearchIndexService = elasticsearchIndexService;
        this.importService = importService;
        this.idealPlanService = idealPlanService;
        this.idealPlanEntriesService = idealPlanEntriesService;
        this.collisionService = collisionService;
        this.idealPlanMapper = idealPlanMapper;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        // Institute erzeugen
        createInstitutes();

        // System mit Demodaten befüllen (Wirtschaftsinformatik Bachelor-Studium)
        Curriculum wirtschaftsinformatik = createCurriculum(204, "Wirtschaftsinformatik");

        // Verknüpfung des Curriculums mit Kerninstituten
        Set<Institute> institutes = new HashSet<>();

        institutes.add(instituteMapper.toEntity(instituteService.findByInstituteId(256)));
        institutes.add(instituteMapper.toEntity(instituteService.findByInstituteId(257)));
        institutes.add(instituteMapper.toEntity(instituteService.findByInstituteId(258)));
        institutes.add(instituteMapper.toEntity(instituteService.findByInstituteId(259)));

        wirtschaftsinformatik = wirtschaftsinformatik.institutes(institutes);

        curriculumService.save(curriculumMapper.toDto(wirtschaftsinformatik));

        // Ein Semester für das Curriculum Wirtschaftsinformatik anlegen
        CurriculumSemesterDTO curriculumSemester = new CurriculumSemesterDTO();
        curriculumSemester.setCurriculumId(wirtschaftsinformatik.getId());
        curriculumSemester.setYear(2017);
        curriculumSemester.setSemester(Semester.WS);

        curriculumSemester = curriculumSemesterService.save(curriculumSemester);

        // Idealtypischen Plan für Wirtschaftsinformatik Bachelor
        IdealPlanDTO idealPlanDTO = new IdealPlanDTO();
        idealPlanDTO.setYear(2017);
        idealPlanDTO.setSemester(Semester.WS);
        idealPlanDTO.setActive(true);
        idealPlanDTO.setCurriculumId(wirtschaftsinformatik.getId());

        idealPlanDTO = idealPlanService.save(idealPlanDTO);

        createIdealPlan(idealPlanMapper.toEntity(idealPlanDTO));

        Set<SubjectDTO> subjectDTOS = importService.verifySubjects(curriculumSemester);

        importService.verifyLvas(curriculumSemester);

        collisionService.calculateCollisions(204, 2017, Semester.WS, 2017, Semester.WS);


        // Update Elastic Search Index
        elasticsearchIndexService.reindexAll();

        // Keine erneute Befüllung der Datenbank wenn schon Einträge vorhanden sind
        if (instituteService.findAll().size() > -1) return;





        importService.verifyLvas(curriculumSemester);

        collisionService.calculateCollisions(204, 2016, Semester.WS, 2017, Semester.SS);

        List<Appointment> collect = idealPlanEntriesRepository.findByIdealplan_Id(idealPlanDTO.getId())
            .stream()
            .filter(idealPlanEntries -> idealPlanEntries.getWinterSemesterDefault() == 2)
            .map(idealPlanEntries -> idealPlanEntries.getSubject())
            .map(subject -> {
                return curriculumSubjectRepository.findBySubject(subject);
            })
            .flatMap(curriculumSubject -> curriculumSubject.getLvas().stream())
            .flatMap(lva -> lva.getAppointments().stream())
            .collect(Collectors.toList());

        System.out.println(collect);


        elasticsearchIndexService.reindexAll();

    }

    private List<Subject> createSubjects(int curId) {
        //ClassLoader classLoader = getClass().getClassLoader();
        //File file = new File(classLoader.getResource("xquery/subjects.xq").getFile());
        //System.out.println(file.getAbsolutePath());
        try {
            Resource resource = resourceLoader.getResource("classpath:xquery/subjects.xq");
            //File file = resource.getFile();
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            String response = "";
            for (String line; (line = br.readLine()) != null; response += (line + "\n"));
            XMLQueryTemplate<Subjects> xmlQueryTemplate = new XMLQueryTemplate<>(response, Subjects.class);
            Subjects xqueryResultSubjects = xmlQueryTemplate.getResult();
            return subjectRepository.save(xqueryResultSubjects.getSubjects());
        } catch (Exception e) {

        }
        return null;
    }


    private void createIdealPlan(IdealPlan idealPlan) {
        try {
            //ClassLoader classLoader = getClass().getClassLoader();
            //File file = new File(classLoader.getResource("imports/institute.csv").getFile());

            Resource resource = resourceLoader.getResource("classpath:imports/idealplan.csv");
            InputStream inputStream = resource.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            List<IdealPlanEntries> institutes = br.lines()
                .map(s -> s.split(";"))
                .map(strings -> {
                    IdealPlanEntries entry = new IdealPlanEntries();
                    Subject bySubjectNameAndSubjectType = subjectRepository.findBySubjectNameAndSubjectType(strings[0], SubjectType.valueOf(strings[1]));
                    if (bySubjectNameAndSubjectType == null) {
                        System.out.println(strings[0] + " " + strings[1]);
                        Subject temp = new Subject();
                        temp.setSubjectName(strings[0]);
                        temp.setSubjectType(SubjectType.valueOf(strings[1]));
                        bySubjectNameAndSubjectType = subjectRepository.save(temp);
                    }

                    entry.setSubject(bySubjectNameAndSubjectType);
                    entry.setWinterSemesterDefault(Integer.parseInt(strings[2]));
                    entry.setSummerSemesterDefault(Integer.parseInt(strings[3]));
                    entry.setExclusive(false);
                    entry.setIdealplan(idealPlan);
                    if (strings[4].equals("true")) {
                        entry.setOptionalSubject(true);
                    } else {
                        entry.setOptionalSubject(false);
                    }
                    return entry;
                }).collect(Collectors.toList());
            idealPlanEntriesRepository.save(institutes);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initiale Befüllung der Datenbank mit Instituten aus einer CSV Datei.
     * Diese Informationen können nicht direkt aus KUSSS ausgelesen werden,
     * daher ist dieser Schritt beim ersten Start der Applikation notwendig.
     */
    private void createInstitutes() {
        try {
            Resource resource = resourceLoader.getResource("classpath:imports/institute.csv");
            InputStream inputStream = resource.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            List<Institute> institutes = br.lines()
                .map(s -> s.split(";"))
                .filter(strings -> Integer.parseInt(strings[0].substring(3,4)) == 0)
                .map(strings -> {
                    Institute institute = new Institute();
                    institute.setInstituteId(Integer.parseInt(strings[0].substring(0, 3)));
                    institute.setInstituteName(strings[1].trim());
                    return institute;
                }).collect(Collectors.toList());
            institutes.forEach(institute -> {
                instituteService.save(instituteMapper.toDto(institute));
            });
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Curriculum createCurriculum(int curId, String curName) {
        Curriculum curriculum = new Curriculum();
        curriculum.setCurId(curId);
        curriculum.setCurName(curName);
        return curriculumMapper.toEntity(curriculumService.save(curriculumMapper.toDto(curriculum)));
    }

    private IdealPlanEntriesDTO createIdealPlanEntity(IdealPlanDTO idealPlanDTO, SubjectDTO subjectDTO, int winterSemesterDefault, int summerSemesterDefault) {
        IdealPlanEntriesDTO idealPlanEntries = new IdealPlanEntriesDTO();
        idealPlanEntries.setIdealplanId(idealPlanDTO.getId());
        idealPlanEntries.setSubjectId(subjectDTO.getId());
        idealPlanEntries.setOptionalSubject(false);
        idealPlanEntries.setExclusive(false);
        idealPlanEntries.setWinterSemesterDefault(winterSemesterDefault);
        idealPlanEntries.setSummerSemesterDefault(summerSemesterDefault);

        return idealPlanEntriesService.save(idealPlanEntries);
    }

    public Map<Map<String, SubjectType>, Map<Semester, Integer>> getIdealPath() {
        Map<Map<String, SubjectType>, Map<Semester, Integer>> ret = new HashMap<>();
        // Winter 2. Semester
        ret.put(createKey("Algorithmen und Datenstrukturen", SubjectType.VL), createValue(2,3));
        ret.put(createKey("Algorithmen und Datenstrukturen", SubjectType.UE), createValue(2,3));
        ret.put(createKey("Prozess- und Kommunikationsmodellierung", SubjectType.VL), createValue(2,1));
        ret.put(createKey("Prozess- und Kommunikationsmodellierung", SubjectType.UE), createValue(2,1));
        ret.put(createKey("Betriebssysteme", SubjectType.VO), createValue(2,3));
        ret.put(createKey("Softwareentwicklung 2", SubjectType.VO), createValue(2,3));
        ret.put(createKey("Softwareentwicklung II", SubjectType.UE), createValue(2,3));
        ret.put(createKey("Formale Grundlagen der Wirtschaftsinformatik", SubjectType.VL), createValue(2,1));
        ret.put(createKey("Formale Grundlagen der Wirtschaftsinformatik", SubjectType.UE), createValue(2,1));

        // Winter 4. Semester
        ret.put(createKey("Bilanzierung", SubjectType.KS), createValue(4,3));
        ret.put(createKey("Finanzmanagement", SubjectType.KS), createValue(4,3));
        ret.put(createKey("Kostenmanagement", SubjectType.KS), createValue(4,3));
        ret.put(createKey("Privatrecht für Wirtschaftsinformatik", SubjectType.KV), createValue(4,1));
        ret.put(createKey("Öffentliches Recht für Wirtschaftsinformatik", SubjectType.KV), createValue(4,1));
        ret.put(createKey("Informationsmanagement", SubjectType.VL), createValue(4,5));
        ret.put(createKey("Informationsmanagement", SubjectType.UE), createValue(4,5));
        ret.put(createKey("Software Engineering", SubjectType.PR), createValue(4,5));
        ret.put(createKey("Data & Knowledge Engineering", SubjectType.VL), createValue(4,3));
        ret.put(createKey("Data & Knowledge Engineering", SubjectType.UE), createValue(4,3));

        // Winter 6. Semester
        ret.put(createKey("Soziale Auswirkungen der IT", SubjectType.KS), createValue(6,4));
        ret.put(createKey("Communications Engineering (Kompetenztraining)", SubjectType.KT), createValue(6,5));
        ret.put(createKey("Anwendungen des Communications Engineering", SubjectType.SE), createValue(6,5));

        ret.put(createKey("Spezielle Wirtschaftsinformatik - Theorie und Praxis, inklusive Bachelorarbeit", SubjectType.PE), createValue(6,6));



        ret.put(createKey("Einführung in die Betriebswirtschaftslehre", SubjectType.KS), createValue(1,1));
        ret.put(createKey("Buchhaltung", SubjectType.KS), createValue(1,1));
        ret.put(createKey("Kostenrechnung", SubjectType.KS), createValue(1,1));

        ret.put(createKey("Produktion und Logistik", SubjectType.KS), createValue(3,1));
        ret.put(createKey("Marketing", SubjectType.KS), createValue(3,1));
        ret.put(createKey("Strategie", SubjectType.KS), createValue(3,1));

        ret.put(createKey("Kommunikative Fertigkeiten Englisch (B2)", SubjectType.KS), createValue(1,2));

        ret.put(createKey("IT-Projekt Wirtschaftsinformatik", SubjectType.PJ), createValue(5,6));

        ret.put(createKey("Data & Knowledge Engineering", SubjectType.PR), createValue(5,5));

        ret.put(createKey("Fachsprache Englisch", SubjectType.SE), createValue(5,5));

        // Wahlfächer WIWI
        ret.put(createKey("Einführung Unternehmensgründung und Unternehmensentwicklung", SubjectType.KS), createValue(7,7));
        ret.put(createKey("Geschäftsmodelle und -prozesse", SubjectType.IK), createValue(7,7));
        ret.put(createKey("Kaufverhalten", SubjectType.KS), createValue(7,7));
        ret.put(createKey("Methoden der Betriebswirtschaftlichen Steuerlehre", SubjectType.KS), createValue(7,7));
        ret.put(createKey("Produktion und Logistik", SubjectType.IK), createValue(7,7));
        ret.put(createKey("Sonderfragen des Jahresabschlusses nach UGB", SubjectType.KS), createValue(7,7));
        ret.put(createKey("Strategie", SubjectType.IK), createValue(7,7));
        ret.put(createKey("Umweltmanagement", SubjectType.KS), createValue(7,7));

        ret.put(createKey("Personal- und Unternehmensführung", SubjectType.KS), createValue(7,7));
        ret.put(createKey("Personal- und Unternehmensführung", SubjectType.IK), createValue(7,7));

        ret.put(createKey("Budgetierung", SubjectType.KS), createValue(7,7));
        ret.put(createKey("Jahresabschlussanalyse", SubjectType.KS), createValue(7,7));
        ret.put(createKey("Unternehmensbesteuerung", SubjectType.KS), createValue(7,7));

        ret.put(createKey("Ökonomische Entscheidungen und Märkte", SubjectType.KS), createValue(7,7));
        ret.put(createKey("Ökonomische Entscheidungen und Märkte", SubjectType.IK), createValue(7,7));
        ret.put(createKey("Einkommen, Beschäftigung und Finanzmärkte", SubjectType.KS), createValue(7,7));



        // Wahlfächer WIN
        ret.put(createKey("Strategische IT-Planung", SubjectType.KV), createValue(7,7));

        ret.put(createKey("Service Engineering", SubjectType.KV), createValue(7,7));

        ret.put(createKey("Data Mining", SubjectType.VL), createValue(7,7)); //!!!!!!!!!!!!!!!
        ret.put(createKey("Data Mining", SubjectType.UE), createValue(7,7)); //!!!!!!!!!!!!!!!

        ret.put(createKey("Semantische Technologien", SubjectType.VL), createValue(7,7));
        ret.put(createKey("Semantische Technologien", SubjectType.UE), createValue(7,7));

        ret.put(createKey("Business Engineering & Management", SubjectType.VL), createValue(7,7)); //!!!!!!!!!!!!!!!
        ret.put(createKey("Business Engineering & Management", SubjectType.UE), createValue(7,7)); //!!!!!!!!!!!!!!!


        // ProSeminare
        ret.put(createKey("Information Engineering", SubjectType.PS), createValue(7,7));
        ret.put(createKey("Software Engineering", SubjectType.PS), createValue(7,7)); //!!!!!!!!!!!!!!!
        ret.put(createKey("Communications Engineering", SubjectType.PS), createValue(7,7)); //!!!!!!!!!!!!!!!






        ret.put(createKey("Data & Knowledge Engineering", SubjectType.SE), createValue(7,7)); //!!!!!!!!!!!!!!!
        ret.put(createKey("Information Engineering", SubjectType.SE), createValue(7,7)); //!!!!!!!!!!!!!!!
        ret.put(createKey("Software Engineering", SubjectType.SE), createValue(7,7)); //!!!!!!!!!!!!!!!





        // passt irgendwie net rein
        ret.put(createKey("Softwareentwicklung I", SubjectType.UE), createValue(8,8));
        return ret;
    }

    public static Map<String, SubjectType> createKey(String name, SubjectType subjectType) {
        Map<String, SubjectType> ret = new HashMap<>();
        ret.put(name, subjectType);
        return ret;
    }

    public static Map<Semester, Integer> createValue(int winter, int summer) {
        Map<Semester, Integer> ret = new HashMap<>();
        ret.put(Semester.WS, winter);
        ret.put(Semester.SS, summer);
        return ret;
    }

    private CurriculumSubject createCurriculumSubjectEntity(CurriculumSemesterDTO curriculumSemesterDTO, Subject subject) {
        CurriculumSubject curriculumSubject = new CurriculumSubject();
        curriculumSubject.setCurriculumSemester(curriculumSemesterMapper.toEntity(curriculumSemesterDTO));
        curriculumSubject.setSubject(subject);
        return curriculumSubjectRepository.save(curriculumSubject);
    }
}
