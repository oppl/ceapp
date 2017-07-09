package at.meroff.itproject.repository;

import at.meroff.itproject.domain.IdealPlan;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IdealPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdealPlanRepository extends JpaRepository<IdealPlan,Long> {
    
}
