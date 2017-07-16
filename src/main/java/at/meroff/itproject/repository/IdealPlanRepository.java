package at.meroff.itproject.repository;

import at.meroff.itproject.domain.IdealPlan;
import at.meroff.itproject.domain.enumeration.Semester;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IdealPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdealPlanRepository extends JpaRepository<IdealPlan,Long> {

    IdealPlan findByCurriculum_CurIdAndYearAndSemester(@Param("curId") Integer curId, @Param("year") Integer year, @Param("semester") Semester semester);

}
