package at.meroff.itproject.repository;

import at.meroff.itproject.domain.CurriculumSemester;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CurriculumSemester entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurriculumSemesterRepository extends JpaRepository<CurriculumSemester,Long> {

    @Query("select cs from CurriculumSemester cs left join fetch cs.curriculumSubjects where cs.id = :id")
    CurriculumSemester findOne(@Param("id") Long id);

}
