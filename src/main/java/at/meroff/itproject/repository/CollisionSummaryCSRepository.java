package at.meroff.itproject.repository;

import at.meroff.itproject.domain.CollisionSummaryCS;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CollisionSummaryCS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollisionSummaryCSRepository extends JpaRepository<CollisionSummaryCS,Long> {
    
}
