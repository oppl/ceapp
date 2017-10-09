package at.meroff.itproject.bootstrap;

import at.meroff.itproject.domain.enumeration.Semester;
import at.meroff.itproject.domain.enumeration.SubjectType;
import at.meroff.itproject.service.*;
import at.meroff.itproject.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The purpose of this class is to initialize an empty database. The following data will be created
 * <ul>
 *     <li>Institutes from a CSV</li>
 *     <li>Curriculum for Wirtschaftsinformatik Bachelor</li>
 *     <li>Ideal plan for Wirtschaftsinformatik Bachelor</li>
 *     <li>one semester for demo purpose</li>
 *     <li>collisions for the imported semester</li>
 * </ul>
 */
@Component
public class BootStrap implements ApplicationListener<ContextRefreshedEvent>{

    private final Logger log = LoggerFactory.getLogger(BootStrap.class);

    private CurriculumService curriculumService;
    private InstituteService instituteService;
    private SubjectService subjectService;
    private IdealPlanEntriesService idealPlanEntriesService;
    private ResourceLoader resourceLoader;
    private CurriculumSemesterService curriculumSemesterService;
    private ElasticsearchIndexService elasticsearchIndexService;
    private IdealPlanService idealPlanService;
    private CollisionService collisionService;

    public BootStrap(CurriculumService curriculumService,
                     InstituteService instituteService,
                     SubjectService subjectService,
                     IdealPlanService idealPlanService,
                     CurriculumSemesterService curriculumSemesterService,
                     CollisionService collisionService,
                     IdealPlanEntriesService idealPlanEntriesService,
                     ElasticsearchIndexService elasticsearchIndexService,
                     ResourceLoader resourceLoader) {
        this.curriculumService = curriculumService;
        this.instituteService = instituteService;
        this.subjectService = subjectService;
        this.resourceLoader = resourceLoader;
        this.curriculumSemesterService = curriculumSemesterService;
        this.elasticsearchIndexService = elasticsearchIndexService;
        this.idealPlanService = idealPlanService;
        this.collisionService = collisionService;
        this.idealPlanEntriesService = idealPlanEntriesService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        CurriculumDTO wirtschaftsinformatik;
        IdealPlanDTO idealPlanDTO;

        // check if the database contains data.
        if (instituteService.findAll().size() == 0) {
            log.warn("empty database - initialize database");

            // read institutes from CSV
            createInstitutes();

            // database entries for Wirtschaftsinformatik Bachelor
            wirtschaftsinformatik = createCurriculum(204, "Wirtschaftsinformatik");

            // define core insitutes for Wirtschaftsinformatik
            Set<InstituteDTO> institutes = new HashSet<>();

            institutes.add(instituteService.findByInstituteId(256));
            institutes.add(instituteService.findByInstituteId(257));
            institutes.add(instituteService.findByInstituteId(258));
            institutes.add(instituteService.findByInstituteId(259));

            wirtschaftsinformatik.setInstitutes(institutes);

            curriculumService.save(wirtschaftsinformatik);

            // create new ideal study plan
            idealPlanDTO = new IdealPlanDTO();
            idealPlanDTO.setYear(2015);
            idealPlanDTO.setSemester(Semester.WS);
            idealPlanDTO.setActive(true);
            idealPlanDTO.setCurriculumId(wirtschaftsinformatik.getId());

            idealPlanDTO = idealPlanService.save(idealPlanDTO);

            createIdealPlan(idealPlanDTO);

            // create new ideal study plan
            /*idealPlanDTO = new IdealPlanDTO();
            idealPlanDTO.setYear(2016);
            idealPlanDTO.setSemester(Semester.WS);
            idealPlanDTO.setActive(true);
            idealPlanDTO.setCurriculumId(wirtschaftsinformatik.getId());

            idealPlanDTO = idealPlanService.save(idealPlanDTO);

            createIdealPlan(idealPlanDTO);*/

            // create new ideal study plan
            idealPlanDTO = new IdealPlanDTO();
            idealPlanDTO.setYear(2017);
            idealPlanDTO.setSemester(Semester.WS);
            idealPlanDTO.setActive(true);
            idealPlanDTO.setCurriculumId(wirtschaftsinformatik.getId());

            idealPlanDTO = idealPlanService.save(idealPlanDTO);

            createIdealPlan(idealPlanDTO);

            // create one semester for Wirtschaftsinformatik
            CurriculumSemesterDTO curriculumSemester = new CurriculumSemesterDTO();
            curriculumSemester.setCurriculumId(wirtschaftsinformatik.getId());
            curriculumSemester.setYear(2017);
            curriculumSemester.setSemester(Semester.WS);

            curriculumSemester = curriculumSemesterService.save(curriculumSemester);

            // calculate collisions
            collisionService.calculateCollisions(204, 2015, Semester.WS, 2017, Semester.WS);
            //collisionService.calculateCollisions(204, 2016, Semester.WS, 2017, Semester.WS);
            collisionService.calculateCollisions(204, 2017, Semester.WS, 2017, Semester.WS);


        }

        // Update Elastic Search Index
        elasticsearchIndexService.reindexAll();

    }

    /**
     * Method creates the entries for an ideal plan.
     * @param idealPlan ideal plan for which entries should be created
     */
    // TODO ev. in den Service verschieben
    private void createIdealPlan(IdealPlanDTO idealPlan) {
        try {
            Resource resource = resourceLoader.getResource("classpath:imports/idealplan.csv");
            InputStream inputStream = resource.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            List<IdealPlanEntriesDTO> idealPlanEntriesDTOList = br.lines()
                .map(s -> s.split(";"))
                .map(strings -> {

                    // find the corresponding subject in the database
                    SubjectDTO subject = subjectService.findBySubjectNameAndSubjectType(strings[0], SubjectType.valueOf(strings[1]));
                    if (subject == null) {
                        // create a new subject if it does not exist.
                        System.out.println(strings[0] + " " + strings[1]);
                        SubjectDTO newSubject = new SubjectDTO();
                        newSubject.setSubjectName(strings[0]);
                        newSubject.setSubjectType(SubjectType.valueOf(strings[1]));
                        subject = subjectService.save(newSubject);
                    }

                    // create new ideal plan entry
                    IdealPlanEntriesDTO entry = new IdealPlanEntriesDTO();
                    entry.setSubjectId(subject.getId());
                    entry.setWinterSemesterDefault(Integer.parseInt(strings[2]));
                    entry.setSummerSemesterDefault(Integer.parseInt(strings[3]));
                    entry.setExclusive(false);
                    entry.setIdealplanId(idealPlan.getId());
                    if (strings[4].equals("true")) {
                        entry.setOptionalSubject(true);
                    } else {
                        entry.setOptionalSubject(false);
                    }
                    return entry;
                }).collect(Collectors.toList());

            // save all new ideal plan entries
            idealPlanEntriesDTOList.forEach(idealPlanEntriesDTO -> idealPlanEntriesService.save(idealPlanEntriesDTO));

            br.close();
        } catch (IOException e) {
            log.error("Error during import (ideal plan entries");
            e.printStackTrace();
        }
    }

    /**
     * Method creates predefined institutes. The institutes are defined in a CSV file.
     */
    private void createInstitutes() {
        try {
            log.debug("create institutes");
            Resource resource = resourceLoader.getResource("classpath:imports/institute.csv");
            InputStream inputStream = resource.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            List<InstituteDTO> institutes = br.lines()
                .map(s -> s.split(";"))
                .filter(strings -> Integer.parseInt(strings[0].substring(3,4)) == 0)
                .map(strings -> {
                    InstituteDTO institute = new InstituteDTO();
                    institute.setInstituteId(Integer.parseInt(strings[0].substring(0, 3)));
                    institute.setInstituteName(strings[1].trim());
                    return institute;
                }).collect(Collectors.toList());
            institutes.forEach(institute -> {
                instituteService.save(institute);
            });
            br.close();
        } catch (IOException e) {
            log.error("Could not create predefined institutes");
            e.printStackTrace();
        }
    }

    /**
     * create a curriculum during initializing the database
     * @param curId KUSSS ID for the curriculum
     * @param curName name for the curriculum
     * @return curriculum instance
     */
    private CurriculumDTO createCurriculum(int curId, String curName) {
        CurriculumDTO curriculum = new CurriculumDTO();
        curriculum.setCurId(curId);
        curriculum.setCurName(curName);
        return curriculumService.save(curriculum);
    }


}
