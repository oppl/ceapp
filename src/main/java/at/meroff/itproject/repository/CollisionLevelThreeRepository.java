package at.meroff.itproject.repository;

import at.meroff.itproject.domain.CollisionLevelThree;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the CollisionLevelThree entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollisionLevelThreeRepository extends JpaRepository<CollisionLevelThree,Long> {

    Set<CollisionLevelThree> findByCollisionLevelTwo_Id(@Param("cltId") Long cltId);

}
