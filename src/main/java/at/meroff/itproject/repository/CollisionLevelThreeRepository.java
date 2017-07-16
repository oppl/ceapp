package at.meroff.itproject.repository;

import at.meroff.itproject.domain.CollisionLevelThree;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CollisionLevelThree entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollisionLevelThreeRepository extends JpaRepository<CollisionLevelThree,Long> {
    
}
