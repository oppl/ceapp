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

import at.meroff.itproject.domain.enumeration.SubjectType;

/**
 * A Subject.
 */
@Entity
@Table(name = "subject")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "subject")
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "subject_type", nullable = false)
    private SubjectType subjectType;

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CurriculumSubject> curriculumsubjects = new HashSet<>();

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IdealPlanEntries> idealplanentries = new HashSet<>();

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lva> lvas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Subject subjectName(String subjectName) {
        this.subjectName = subjectName;
        return this;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public Subject subjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
        return this;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }

    public Set<CurriculumSubject> getCurriculumsubjects() {
        return curriculumsubjects;
    }

    public Subject curriculumsubjects(Set<CurriculumSubject> curriculumSubjects) {
        this.curriculumsubjects = curriculumSubjects;
        return this;
    }

    public Subject addCurriculumsubject(CurriculumSubject curriculumSubject) {
        this.curriculumsubjects.add(curriculumSubject);
        curriculumSubject.setSubject(this);
        return this;
    }

    public Subject removeCurriculumsubject(CurriculumSubject curriculumSubject) {
        this.curriculumsubjects.remove(curriculumSubject);
        curriculumSubject.setSubject(null);
        return this;
    }

    public void setCurriculumsubjects(Set<CurriculumSubject> curriculumSubjects) {
        this.curriculumsubjects = curriculumSubjects;
    }

    public Set<IdealPlanEntries> getIdealplanentries() {
        return idealplanentries;
    }

    public Subject idealplanentries(Set<IdealPlanEntries> idealPlanEntries) {
        this.idealplanentries = idealPlanEntries;
        return this;
    }

    public Subject addIdealplanentries(IdealPlanEntries idealPlanEntries) {
        this.idealplanentries.add(idealPlanEntries);
        idealPlanEntries.setSubject(this);
        return this;
    }

    public Subject removeIdealplanentries(IdealPlanEntries idealPlanEntries) {
        this.idealplanentries.remove(idealPlanEntries);
        idealPlanEntries.setSubject(null);
        return this;
    }

    public void setIdealplanentries(Set<IdealPlanEntries> idealPlanEntries) {
        this.idealplanentries = idealPlanEntries;
    }

    public Set<Lva> getLvas() {
        return lvas;
    }

    public Subject lvas(Set<Lva> lvas) {
        this.lvas = lvas;
        return this;
    }

    public Subject addLva(Lva lva) {
        this.lvas.add(lva);
        lva.setSubject(this);
        return this;
    }

    public Subject removeLva(Lva lva) {
        this.lvas.remove(lva);
        lva.setSubject(null);
        return this;
    }

    public void setLvas(Set<Lva> lvas) {
        this.lvas = lvas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Subject subject = (Subject) o;
        if (subject.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Subject{" +
            "id=" + getId() +
            ", subjectName='" + getSubjectName() + "'" +
            ", subjectType='" + getSubjectType() + "'" +
            "}";
    }
}
