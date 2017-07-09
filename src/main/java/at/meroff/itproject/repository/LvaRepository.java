package at.meroff.itproject.repository;

import at.meroff.itproject.domain.Lva;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Lva entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LvaRepository extends JpaRepository<Lva,Long> {
    
}
