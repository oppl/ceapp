package at.meroff.itproject.repository;

import at.meroff.itproject.domain.CollisionLevelFive;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the CollisionLevelFive entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollisionLevelFiveRepository extends JpaRepository<CollisionLevelFive,Long> {

    Set<CollisionLevelFive> findByCollisionLevelFour_Id(@Param("clfId") Long clfId);

}
