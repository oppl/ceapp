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

    /**
     * internal database ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Curriculum ID within KUSSS
     */
    @NotNull
    @Column(name = "cur_id", nullable = false)
    private Integer curId;

    /**
     * Name for the curriculum
     */
    @NotNull
    @Column(name = "cur_name", nullable = false)
    private String curName;

    /**
     * Set of linked ideal plans
     */
    @OneToMany(mappedBy = "curriculum")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IdealPlan> idealPlans = new HashSet<>();

    /**
     * Set of institutes mainly responsible for the curriculum
     */
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "curriculum_institute",
               joinColumns = @JoinColumn(name="curricula_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="institutes_id", referencedColumnName="id"))
    private Set<Institute> institutes = new HashSet<>();

    /**
     * set of linked semesters (2017S, 2017W,...)
     */
    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CurriculumSemester> curriculumSemesters = new HashSet<>();

    /**
     * Method returns the internal database ID
     * @return internal database ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Method updates the internal database ID
     * @param id internal database ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Method returns the database ID which is used inside KUSSS
     * @return KUSSS curriculum ID
     */
    public Integer getCurId() {
        return curId;
    }

    /**
     * Method updates the KUSSS ID and returns the curriculum object
     * @param curId KUSSS curriculum ID
     * @return curriculum entity
     */
    public Curriculum curId(Integer curId) {
        this.curId = curId;
        return this;
    }

    /**
     * Method updates the KUSSS ID
     * @param curId KUSSS curriculum ID
     */
    public void setCurId(Integer curId) {
        this.curId = curId;
    }

    /**
     * Method returns the name for the curriculum
     * @return name of the curriculum
     */
    public String getCurName() {
        return curName;
    }

    /**
     * Method updates the curriculum name
     * @param curName name of the curriculum
     * @return curriculum entity
     */
    public Curriculum curName(String curName) {
        this.curName = curName;
        return this;
    }

    /**
     * Method updates the curriculum name
     * @param curName name of the curriculum
     */
    public void setCurName(String curName) {
        this.curName = curName;
    }

    /**
     * Method returns the linked ideal plans for a curriculum
     * @return Set of ideal plans
     */
    public Set<IdealPlan> getIdealPlans() {
        return idealPlans;
    }

    /**
     * Method links ideal plans with the curriculum
     * @param idealPlans Set of ideal plans
     */
    public void setIdealPlans(Set<IdealPlan> idealPlans) {
        this.idealPlans = idealPlans;
    }

    /**
     * Method returns the linked main institutes for the curriculum
     * @return Set of ideal plans
     */
    public Set<Institute> getInstitutes() {
        return institutes;
    }

    /**
     * Method updates the institutes linked with the curriculum and returns the curriculum
     * @param institutes Set of institutes
     * @return curriculum entity
     */
    public Curriculum institutes(Set<Institute> institutes) {
        this.institutes = institutes;
        return this;
    }

    /**
     * Method to add an institute to the curriculum
     * @param institute institute mainly responsible for the curriculum
     * @return curriculum entity
     */
    public Curriculum addInstitute(Institute institute) {
        this.institutes.add(institute);
        institute.getCurricula().add(this);
        return this;
    }

    /**
     * remove an institute for the set of mainly responsible institutes
     * @param institute institute which should be removed
     * @return curriculum entity
     */
    public Curriculum removeInstitute(Institute institute) {
        this.institutes.remove(institute);
        institute.getCurricula().remove(this);
        return this;
    }

    /**
     * Method updates the set of linked institutes
     * @param institutes set of institutes mainly responsible for the curriculum
     */
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

    /**
     * Method to add a semester to the curriculum
     * @param curriculumSemester
     * @return curriculum entity
     */
    public Curriculum addCurriculumSemester(CurriculumSemester curriculumSemester) {
        this.curriculumSemesters.add(curriculumSemester);
        curriculumSemester.setCurriculum(this);
        return this;
    }

    /**
     * Method to remove a semester from the curriculum
     * @param curriculumSemester
     * @return curriculum entity
     */
    public Curriculum removeCurriculumSemester(CurriculumSemester curriculumSemester) {
        this.curriculumSemesters.remove(curriculumSemester);
        curriculumSemester.setCurriculum(null);
        return this;
    }

    /**
     * Method updates the set of linked semesters
     * @param curriculumSemesters set of semesters linked with the curriculum
     */
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
