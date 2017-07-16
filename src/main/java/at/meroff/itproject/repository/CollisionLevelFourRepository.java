package at.meroff.itproject.repository;

import at.meroff.itproject.domain.CollisionLevelFour;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the CollisionLevelFour entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollisionLevelFourRepository extends JpaRepository<CollisionLevelFour,Long> {
    
    @Query("select distinct collision_level_four from CollisionLevelFour collision_level_four left join fetch collision_level_four.sourceAppointments left join fetch collision_level_four.targetAppointments")
    List<CollisionLevelFour> findAllWithEagerRelationships();

    @Query("select collision_level_four from CollisionLevelFour collision_level_four left join fetch collision_level_four.sourceAppointments left join fetch collision_level_four.targetAppointments where collision_level_four.id =:id")
    CollisionLevelFour findOneWithEagerRelationships(@Param("id") Long id);
    
}
