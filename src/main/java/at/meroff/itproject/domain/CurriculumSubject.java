package at.meroff.itproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import at.meroff.itproject.domain.enumeration.Semester;

/**
 * A CurriculumSubject.
 */
@Entity
@Table(name = "curriculum_subject")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "curriculumsubject")
public class CurriculumSubject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_year", nullable = false)
    private Integer year;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "semester", nullable = false)
    private Semester semester;

    @OneToMany(mappedBy = "csSource")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CollisionSummaryCS> collCSSources = new HashSet<>();

    @OneToMany(mappedBy = "csTarget")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CollisionSummaryCS> collCSTargets = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "curriculum_subject_lva",
               joinColumns = @JoinColumn(name="curriculum_subjects_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="lvas_id", referencedColumnName="id"))
    private Set<Lva> lvas = new HashSet<>();

    @ManyToOne
    private Curriculum curriculum;

    @ManyToOne
    private Subject subject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public CurriculumSubject year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Semester getSemester() {
        return semester;
    }

    public CurriculumSubject semester(Semester semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Set<CollisionSummaryCS> getCollCSSources() {
        return collCSSources;
    }

    public CurriculumSubject collCSSources(Set<CollisionSummaryCS> collisionSummaryCS) {
        this.collCSSources = collisionSummaryCS;
        return this;
    }

    public CurriculumSubject addCollCSSource(CollisionSummaryCS collisionSummaryCS) {
        this.collCSSources.add(collisionSummaryCS);
        collisionSummaryCS.setCsSource(this);
        return this;
    }

    public CurriculumSubject removeCollCSSource(CollisionSummaryCS collisionSummaryCS) {
        this.collCSSources.remove(collisionSummaryCS);
        collisionSummaryCS.setCsSource(null);
        return this;
    }

    public void setCollCSSources(Set<CollisionSummaryCS> collisionSummaryCS) {
        this.collCSSources = collisionSummaryCS;
    }

    public Set<CollisionSummaryCS> getCollCSTargets() {
        return collCSTargets;
    }

    public CurriculumSubject collCSTargets(Set<CollisionSummaryCS> collisionSummaryCS) {
        this.collCSTargets = collisionSummaryCS;
        return this;
    }

    public CurriculumSubject addCollCSTarget(CollisionSummaryCS collisionSummaryCS) {
        this.collCSTargets.add(collisionSummaryCS);
        collisionSummaryCS.setCsTarget(this);
        return this;
    }

    public CurriculumSubject removeCollCSTarget(CollisionSummaryCS collisionSummaryCS) {
        this.collCSTargets.remove(collisionSummaryCS);
        collisionSummaryCS.setCsTarget(null);
        return this;
    }

    public void setCollCSTargets(Set<CollisionSummaryCS> collisionSummaryCS) {
        this.collCSTargets = collisionSummaryCS;
    }

    public Set<Lva> getLvas() {
        return lvas;
    }

    public CurriculumSubject lvas(Set<Lva> lvas) {
        this.lvas = lvas;
        return this;
    }

    public CurriculumSubject addLva(Lva lva) {
        this.lvas.add(lva);
        lva.getCurriculumsubjects().add(this);
        return this;
    }

    public CurriculumSubject removeLva(Lva lva) {
        this.lvas.remove(lva);
        lva.getCurriculumsubjects().remove(this);
        return this;
    }

    public void setLvas(Set<Lva> lvas) {
        this.lvas = lvas;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public CurriculumSubject curriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
        return this;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public Subject getSubject() {
        return subject;
    }

    public CurriculumSubject subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CurriculumSubject curriculumSubject = (CurriculumSubject) o;
        if (curriculumSubject.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), curriculumSubject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurriculumSubject{" +
            "id=" + getId() +
            ", year='" + getYear() + "'" +
            ", semester='" + getSemester() + "'" +
            "}";
    }
}
