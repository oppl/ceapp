package at.meroff.itproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "curriculum_subject_lva",
               joinColumns = @JoinColumn(name="curriculum_subjects_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="lvas_id", referencedColumnName="id"))
    private Set<Lva> lvas = new HashSet<>();

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private CurriculumSemester curriculumSemester;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CurriculumSemester getCurriculumSemester() {
        return curriculumSemester;
    }

    public CurriculumSubject curriculumSemester(CurriculumSemester curriculumSemester) {
        this.curriculumSemester = curriculumSemester;
        return this;
    }

    public void setCurriculumSemester(CurriculumSemester curriculumSemester) {
        this.curriculumSemester = curriculumSemester;
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
            "}";
    }
}
