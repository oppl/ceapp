package at.meroff.itproject.repository;

import at.meroff.itproject.domain.CollisionSummaryCS;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CollisionSummaryCS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollisionSummaryCSRepository extends JpaRepository<CollisionSummaryCS,Long> {

    @Query(value = "select csss from CollisionSummaryCS csss left join fetch csss.csSource left join fetch csss.csTarget where csss.id = :id")
    CollisionSummaryCS findSomething(@Param("id") Long id);

}
