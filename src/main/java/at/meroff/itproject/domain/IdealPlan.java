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
 * A IdealPlan.
 */
@Entity
@Table(name = "ideal_plan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "idealplan")
public class IdealPlan implements Serializable {

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

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "idealplan")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IdealPlanEntries> idealplanentries = new HashSet<>();

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

    public IdealPlan year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Semester getSemester() {
        return semester;
    }

    public IdealPlan semester(Semester semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Boolean isActive() {
        return active;
    }

    public IdealPlan active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<IdealPlanEntries> getIdealplanentries() {
        return idealplanentries;
    }

    public IdealPlan idealplanentries(Set<IdealPlanEntries> idealPlanEntries) {
        this.idealplanentries = idealPlanEntries;
        return this;
    }

    public IdealPlan addIdealplanentries(IdealPlanEntries idealPlanEntries) {
        this.idealplanentries.add(idealPlanEntries);
        idealPlanEntries.setIdealplan(this);
        return this;
    }

    public IdealPlan removeIdealplanentries(IdealPlanEntries idealPlanEntries) {
        this.idealplanentries.remove(idealPlanEntries);
        idealPlanEntries.setIdealplan(null);
        return this;
    }

    public void setIdealplanentries(Set<IdealPlanEntries> idealPlanEntries) {
        this.idealplanentries = idealPlanEntries;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public IdealPlan curriculum(Curriculum curriculum) {
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
        IdealPlan idealPlan = (IdealPlan) o;
        if (idealPlan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), idealPlan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IdealPlan{" +
            "id=" + getId() +
            ", year='" + getYear() + "'" +
            ", semester='" + getSemester() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
