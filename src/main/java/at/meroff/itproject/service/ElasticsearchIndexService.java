package at.meroff.itproject.service;

import com.codahale.metrics.annotation.Timed;
import at.meroff.itproject.domain.*;
import at.meroff.itproject.repository.*;
import at.meroff.itproject.repository.search.*;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class ElasticsearchIndexService {

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final AppointmentRepository appointmentRepository;

    private final AppointmentSearchRepository appointmentSearchRepository;

    private final CollisionLevelOneRepository collisionLevelOneRepository;

    private final CollisionLevelOneSearchRepository collisionLevelOneSearchRepository;

    private final CollisionSummaryCSRepository collisionSummaryCSRepository;

    private final CollisionSummaryCSSearchRepository collisionSummaryCSSearchRepository;

    private final CollisionSummaryLvaRepository collisionSummaryLvaRepository;

    private final CollisionSummaryLvaSearchRepository collisionSummaryLvaSearchRepository;

    private final CurriculumRepository curriculumRepository;

    private final CurriculumSearchRepository curriculumSearchRepository;

    private final CurriculumSemesterRepository curriculumSemesterRepository;

    private final CurriculumSemesterSearchRepository curriculumSemesterSearchRepository;

    private final CurriculumSubjectRepository curriculumSubjectRepository;

    private final CurriculumSubjectSearchRepository curriculumSubjectSearchRepository;

    private final IdealPlanRepository idealPlanRepository;

    private final IdealPlanSearchRepository idealPlanSearchRepository;

    private final IdealPlanEntriesRepository idealPlanEntriesRepository;

    private final IdealPlanEntriesSearchRepository idealPlanEntriesSearchRepository;

    private final InstituteRepository instituteRepository;

    private final InstituteSearchRepository instituteSearchRepository;

    private final LvaRepository lvaRepository;

    private final LvaSearchRepository lvaSearchRepository;

    private final SubjectRepository subjectRepository;

    private final SubjectSearchRepository subjectSearchRepository;

    private final UserRepository userRepository;

    private final UserSearchRepository userSearchRepository;

    private final ElasticsearchTemplate elasticsearchTemplate;

    public ElasticsearchIndexService(
        UserRepository userRepository,
        UserSearchRepository userSearchRepository,
        AppointmentRepository appointmentRepository,
        AppointmentSearchRepository appointmentSearchRepository,
        CollisionLevelOneRepository collisionLevelOneRepository,
        CollisionLevelOneSearchRepository collisionLevelOneSearchRepository,
        CollisionSummaryCSRepository collisionSummaryCSRepository,
        CollisionSummaryCSSearchRepository collisionSummaryCSSearchRepository,
        CollisionSummaryLvaRepository collisionSummaryLvaRepository,
        CollisionSummaryLvaSearchRepository collisionSummaryLvaSearchRepository,
        CurriculumRepository curriculumRepository,
        CurriculumSearchRepository curriculumSearchRepository,
        CurriculumSemesterRepository curriculumSemesterRepository,
        CurriculumSemesterSearchRepository curriculumSemesterSearchRepository,
        CurriculumSubjectRepository curriculumSubjectRepository,
        CurriculumSubjectSearchRepository curriculumSubjectSearchRepository,
        IdealPlanRepository idealPlanRepository,
        IdealPlanSearchRepository idealPlanSearchRepository,
        IdealPlanEntriesRepository idealPlanEntriesRepository,
        IdealPlanEntriesSearchRepository idealPlanEntriesSearchRepository,
        InstituteRepository instituteRepository,
        InstituteSearchRepository instituteSearchRepository,
        LvaRepository lvaRepository,
        LvaSearchRepository lvaSearchRepository,
        SubjectRepository subjectRepository,
        SubjectSearchRepository subjectSearchRepository,
        ElasticsearchTemplate elasticsearchTemplate) {
        this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
        this.appointmentRepository = appointmentRepository;
        this.appointmentSearchRepository = appointmentSearchRepository;
        this.collisionLevelOneRepository = collisionLevelOneRepository;
        this.collisionLevelOneSearchRepository = collisionLevelOneSearchRepository;
        this.collisionSummaryCSRepository = collisionSummaryCSRepository;
        this.collisionSummaryCSSearchRepository = collisionSummaryCSSearchRepository;
        this.collisionSummaryLvaRepository = collisionSummaryLvaRepository;
        this.collisionSummaryLvaSearchRepository = collisionSummaryLvaSearchRepository;
        this.curriculumRepository = curriculumRepository;
        this.curriculumSearchRepository = curriculumSearchRepository;
        this.curriculumSemesterRepository = curriculumSemesterRepository;
        this.curriculumSemesterSearchRepository = curriculumSemesterSearchRepository;
        this.curriculumSubjectRepository = curriculumSubjectRepository;
        this.curriculumSubjectSearchRepository = curriculumSubjectSearchRepository;
        this.idealPlanRepository = idealPlanRepository;
        this.idealPlanSearchRepository = idealPlanSearchRepository;
        this.idealPlanEntriesRepository = idealPlanEntriesRepository;
        this.idealPlanEntriesSearchRepository = idealPlanEntriesSearchRepository;
        this.instituteRepository = instituteRepository;
        this.instituteSearchRepository = instituteSearchRepository;
        this.lvaRepository = lvaRepository;
        this.lvaSearchRepository = lvaSearchRepository;
        this.subjectRepository = subjectRepository;
        this.subjectSearchRepository = subjectSearchRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Async
    @Timed
    public void reindexAll() {
        reindexForClass(Appointment.class, appointmentRepository, appointmentSearchRepository);
        reindexForClass(CollisionLevelOne.class, collisionLevelOneRepository, collisionLevelOneSearchRepository);
        reindexForClass(CollisionSummaryCS.class, collisionSummaryCSRepository, collisionSummaryCSSearchRepository);
        reindexForClass(CollisionSummaryLva.class, collisionSummaryLvaRepository, collisionSummaryLvaSearchRepository);
        reindexForClass(Curriculum.class, curriculumRepository, curriculumSearchRepository);
        reindexForClass(CurriculumSemester.class, curriculumSemesterRepository, curriculumSemesterSearchRepository);
        reindexForClass(CurriculumSubject.class, curriculumSubjectRepository, curriculumSubjectSearchRepository);
        reindexForClass(IdealPlan.class, idealPlanRepository, idealPlanSearchRepository);
        reindexForClass(IdealPlanEntries.class, idealPlanEntriesRepository, idealPlanEntriesSearchRepository);
        reindexForClass(Institute.class, instituteRepository, instituteSearchRepository);
        reindexForClass(Lva.class, lvaRepository, lvaSearchRepository);
        reindexForClass(Subject.class, subjectRepository, subjectSearchRepository);
        reindexForClass(User.class, userRepository, userSearchRepository);

        log.info("Elasticsearch: Successfully performed reindexing");
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    private <T, ID extends Serializable> void reindexForClass(Class<T> entityClass, JpaRepository<T, ID> jpaRepository,
                                                              ElasticsearchRepository<T, ID> elasticsearchRepository) {
        elasticsearchTemplate.deleteIndex(entityClass);
        try {
            elasticsearchTemplate.createIndex(entityClass);
        } catch (IndexAlreadyExistsException e) {
            // Do nothing. Index was already concurrently recreated by some other service.
        }
        elasticsearchTemplate.putMapping(entityClass);
        if (jpaRepository.count() > 0) {
            try {
                Method m = jpaRepository.getClass().getMethod("findAllWithEagerRelationships");
                elasticsearchRepository.save((List<T>) m.invoke(jpaRepository));
            } catch (Exception e) {
                elasticsearchRepository.save(jpaRepository.findAll());
            }
        }
        log.info("Elasticsearch: Indexed all rows for " + entityClass.getSimpleName());
    }
}
