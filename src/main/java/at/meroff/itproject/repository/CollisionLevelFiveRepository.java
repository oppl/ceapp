package at.meroff.itproject.repository;

import at.meroff.itproject.domain.CollisionLevelFive;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CollisionLevelFive entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollisionLevelFiveRepository extends JpaRepository<CollisionLevelFive,Long> {
    
}
