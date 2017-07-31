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

/**
 * A Curriculum.
 */
@Entity
@Table(name = "curriculum")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "curriculum")
public class Curriculum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cur_id", nullable = false)
    private Integer curId;

    @NotNull
    @Column(name = "cur_name", nullable = false)
    private String curName;

    @OneToMany(mappedBy = "curriculum")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IdealPlan> idealplanentries = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "curriculum_institute",
               joinColumns = @JoinColumn(name="curricula_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="institutes_id", referencedColumnName="id"))
    private Set<Institute> institutes = new HashSet<>();

    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CurriculumSemester> curriculumSemesters = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCurId() {
        return curId;
    }

    public Curriculum curId(Integer curId) {
        this.curId = curId;
        return this;
    }

    public void setCurId(Integer curId) {
        this.curId = curId;
    }

    public String getCurName() {
        return curName;
    }

    public Curriculum curName(String curName) {
        this.curName = curName;
        return this;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public Set<IdealPlan> getIdealplanentries() {
        return idealplanentries;
    }

    public Curriculum idealplanentries(Set<IdealPlan> idealPlans) {
        this.idealplanentries = idealPlans;
        return this;
    }

    public Curriculum addIdealplanentries(IdealPlan idealPlan) {
        this.idealplanentries.add(idealPlan);
        idealPlan.setCurriculum(this);
        return this;
    }

    public Curriculum removeIdealplanentries(IdealPlan idealPlan) {
        this.idealplanentries.remove(idealPlan);
        idealPlan.setCurriculum(null);
        return this;
    }

    public void setIdealplanentries(Set<IdealPlan> idealPlans) {
        this.idealplanentries = idealPlans;
    }

    public Set<Institute> getInstitutes() {
        return institutes;
    }

    public Curriculum institutes(Set<Institute> institutes) {
        this.institutes = institutes;
        return this;
    }

    public Curriculum addInstitute(Institute institute) {
        this.institutes.add(institute);
        institute.getCurricula().add(this);
        return this;
    }

    public Curriculum removeInstitute(Institute institute) {
        this.institutes.remove(institute);
        institute.getCurricula().remove(this);
        return this;
    }

    public void setInstitutes(Set<Institute> institutes) {
        this.institutes = institutes;
    }

    public Set<CurriculumSemester> getCurriculumSemesters() {
        return curriculumSemesters;
    }

    public Curriculum curriculumSemesters(Set<CurriculumSemester> curriculumSemesters) {
        this.curriculumSemesters = curriculumSemesters;
        return this;
    }

    public Curriculum addCurriculumSemester(CurriculumSemester curriculumSemester) {
        this.curriculumSemesters.add(curriculumSemester);
        curriculumSemester.setCurriculum(this);
        return this;
    }

    public Curriculum removeCurriculumSemester(CurriculumSemester curriculumSemester) {
        this.curriculumSemesters.remove(curriculumSemester);
        curriculumSemester.setCurriculum(null);
        return this;
    }

    public void setCurriculumSemesters(Set<CurriculumSemester> curriculumSemesters) {
        this.curriculumSemesters = curriculumSemesters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Curriculum curriculum = (Curriculum) o;
        if (curriculum.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), curriculum.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Curriculum{" +
            "id=" + getId() +
            ", curId='" + getCurId() + "'" +
            ", curName='" + getCurName() + "'" +
            "}";
    }
}
