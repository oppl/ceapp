package at.meroff.itproject.repository;

import at.meroff.itproject.domain.CurriculumSubject;
import at.meroff.itproject.domain.Subject;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the CurriculumSubject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurriculumSubjectRepository extends JpaRepository<CurriculumSubject,Long> {

    @Query("select distinct curriculum_subject from CurriculumSubject curriculum_subject left join fetch curriculum_subject.lvas")
    List<CurriculumSubject> findAllWithEagerRelationships();

    @Query("select curriculum_subject from CurriculumSubject curriculum_subject left join fetch curriculum_subject.lvas where curriculum_subject.id =:id")
    CurriculumSubject findOneWithEagerRelationships(@Param("id") Long id);

    List<CurriculumSubject> findByCurriculumSemester_Id(@Param("id") Long id);

    @Query("select cs from CurriculumSubject cs left join fetch cs.lvas l left join fetch l.appointments where cs.subject = (:subject)")
    CurriculumSubject findBySubject(@Param("subject") Subject subject);

}
