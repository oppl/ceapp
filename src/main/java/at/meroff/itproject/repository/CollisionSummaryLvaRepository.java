package at.meroff.itproject.repository;

import at.meroff.itproject.domain.CollisionSummaryLva;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CollisionSummaryLva entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollisionSummaryLvaRepository extends JpaRepository<CollisionSummaryLva,Long> {
    
}
