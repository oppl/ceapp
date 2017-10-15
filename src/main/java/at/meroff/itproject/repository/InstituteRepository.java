package at.meroff.itproject.repository;

import at.meroff.itproject.domain.Institute;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Institute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstituteRepository extends JpaRepository<Institute,Long> {

    /**
     * Find an institute by the KUSSS internal ID
     * @param instituteId KUSSS internal ID
     * @return institute
     */
    Institute findByInstituteId(@Param("instituteId") Integer instituteId);
}
