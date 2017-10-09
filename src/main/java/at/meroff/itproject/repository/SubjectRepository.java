package at.meroff.itproject.repository;

import at.meroff.itproject.domain.Subject;
import at.meroff.itproject.domain.enumeration.SubjectType;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Subject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {

    // TODO Doku fehlt
    Subject findBySubjectNameAndSubjectType(@Param("subjectName") String subjectName, @Param("subjectType") SubjectType subjectType);

}
