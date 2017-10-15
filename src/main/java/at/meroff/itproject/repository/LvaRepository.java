package at.meroff.itproject.repository;

import at.meroff.itproject.domain.Lva;
import at.meroff.itproject.domain.enumeration.Semester;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Lva entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LvaRepository extends JpaRepository<Lva,Long> {

    @Query("select lva from Lva lva left join fetch lva.appointments a where lva.lvaNr = (:lvanr) and lva.year = (:year) and lva.semester = (:semester)")
    Lva findByLvaNrAndYearAndSemester(@Param("lvanr") String lvanr, @Param("year") Integer year, @Param("semester") Semester semester);

}
