package at.meroff.itproject.repository;

import at.meroff.itproject.domain.IdealPlanEntries;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IdealPlanEntries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdealPlanEntriesRepository extends JpaRepository<IdealPlanEntries,Long> {
    
}
