package at.meroff.itproject.repository;

import at.meroff.itproject.domain.CollisionLevelFour;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the CollisionLevelFour entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollisionLevelFourRepository extends JpaRepository<CollisionLevelFour,Long> {

    Set<CollisionLevelFour> findByCollisionLevelThree_Id(@Param("cltId") Long cltId);

}
