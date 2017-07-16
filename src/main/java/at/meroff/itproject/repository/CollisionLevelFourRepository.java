package at.meroff.itproject.repository;

import at.meroff.itproject.domain.CollisionLevelFour;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CollisionLevelFour entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollisionLevelFourRepository extends JpaRepository<CollisionLevelFour,Long> {
    
}
