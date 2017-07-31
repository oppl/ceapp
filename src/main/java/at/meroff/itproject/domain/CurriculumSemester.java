package at.meroff.itproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import at.meroff.itproject.domain.enumeration.Semester;

/**
 * A CurriculumSemester.
 */
@Entity
@Table(name = "curriculum_semester")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "curriculumsemester")
public class CurriculumSemester implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_year")
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    private Semester semester;

    @OneToMany(mappedBy = "curriculumSemester", cascade = CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CurriculumSubject> curriculumSubjects = new HashSet<>();

    @ManyToOne
    private Curriculum curriculum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public CurriculumSemester year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Semester getSemester() {
        return semester;
    }

    public CurriculumSemester semester(Semester semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Set<CurriculumSubject> getCurriculumSubjects() {
        return curriculumSubjects;
    }

    public CurriculumSemester curriculumSubjects(Set<CurriculumSubject> curriculumSubjects) {
        this.curriculumSubjects = curriculumSubjects;
        return this;
    }

    public CurriculumSemester addCurriculumSubject(CurriculumSubject curriculumSubject) {
        this.curriculumSubjects.add(curriculumSubject);
        curriculumSubject.setCurriculumSemester(this);
        return this;
    }

    public CurriculumSemester removeCurriculumSubject(CurriculumSubject curriculumSubject) {
        this.curriculumSubjects.remove(curriculumSubject);
        curriculumSubject.setCurriculumSemester(null);
        return this;
    }

    public void setCurriculumSubjects(Set<CurriculumSubject> curriculumSubjects) {
        this.curriculumSubjects = curriculumSubjects;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public CurriculumSemester curriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
        return this;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CurriculumSemester curriculumSemester = (CurriculumSemester) o;
        if (curriculumSemester.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), curriculumSemester.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurriculumSemester{" +
            "id=" + getId() +
            ", year='" + getYear() + "'" +
            ", semester='" + getSemester() + "'" +
            "}";
    }
}
