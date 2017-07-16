package at.meroff.itproject.repository;

import at.meroff.itproject.domain.CollisionLevelTwo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CollisionLevelTwo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollisionLevelTwoRepository extends JpaRepository<CollisionLevelTwo,Long> {
    
}
