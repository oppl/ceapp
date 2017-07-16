package at.meroff.itproject.repository;

import at.meroff.itproject.domain.IdealPlanEntries;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the IdealPlanEntries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdealPlanEntriesRepository extends JpaRepository<IdealPlanEntries,Long> {

    @Query("select ipe from IdealPlanEntries ipe left join fetch ipe.subject left join fetch ipe.idealplan ip where ip.id = :id")
    List<IdealPlanEntries> findByIdealplan_Id(@Param("id") Long id);

}
