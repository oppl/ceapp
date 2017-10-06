package at.meroff.itproject.repository;

import at.meroff.itproject.domain.Curriculum;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Curriculum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum,Long> {

    @Query("select distinct curriculum from Curriculum curriculum left join fetch curriculum.institutes")
    List<Curriculum> findAllWithEagerRelationships();

    @Query("select curriculum from Curriculum curriculum left join fetch curriculum.institutes left join fetch curriculum.curriculumSemesters left join fetch curriculum.idealPlans where curriculum.id =:id")
    Curriculum findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select curriculum from Curriculum curriculum left join fetch curriculum.institutes where curriculum.curId =:curId")
    Curriculum findByCurId(@Param("curId") Integer curId);

}
