package at.meroff.itproject.repository;

import at.meroff.itproject.domain.CollisionLevelOne;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CollisionLevelOne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollisionLevelOneRepository extends JpaRepository<CollisionLevelOne,Long> {
    
}
