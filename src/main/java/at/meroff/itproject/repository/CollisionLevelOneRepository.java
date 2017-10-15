package at.meroff.itproject.repository;

import at.meroff.itproject.domain.CollisionLevelOne;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Collection;


/**
 * Spring Data JPA repository for the CollisionLevelOne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollisionLevelOneRepository extends JpaRepository<CollisionLevelOne,Long> {

    // TODO Doku fehlt
    @Query(value = "select clo from CollisionLevelOne clo where clo.idealPlan.id =(:ip) and clo.curriculumSubject.curriculumSemester.id =(:cs)")
    Collection<CollisionLevelOne> findByCsIp(@Param("cs") Long cs, @Param("ip") Long ip);
}
