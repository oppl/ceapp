package at.meroff.itproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A IdealPlanEntries.
 */
@Entity
@Table(name = "ideal_plan_entries")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "idealplanentries")
public class IdealPlanEntries implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "winter_semester_default", nullable = false)
    private Integer winterSemesterDefault;

    @NotNull
    @Column(name = "summer_semester_default", nullable = false)
    private Integer summerSemesterDefault;

    @Column(name = "optional_subject")
    private Boolean optionalSubject;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private IdealPlan idealplan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWinterSemesterDefault() {
        return winterSemesterDefault;
    }

    public IdealPlanEntries winterSemesterDefault(Integer winterSemesterDefault) {
        this.winterSemesterDefault = winterSemesterDefault;
        return this;
    }

    public void setWinterSemesterDefault(Integer winterSemesterDefault) {
        this.winterSemesterDefault = winterSemesterDefault;
    }

    public Integer getSummerSemesterDefault() {
        return summerSemesterDefault;
    }

    public IdealPlanEntries summerSemesterDefault(Integer summerSemesterDefault) {
        this.summerSemesterDefault = summerSemesterDefault;
        return this;
    }

    public void setSummerSemesterDefault(Integer summerSemesterDefault) {
        this.summerSemesterDefault = summerSemesterDefault;
    }

    public Boolean isOptionalSubject() {
        return optionalSubject;
    }

    public IdealPlanEntries optionalSubject(Boolean optionalSubject) {
        this.optionalSubject = optionalSubject;
        return this;
    }

    public void setOptionalSubject(Boolean optionalSubject) {
        this.optionalSubject = optionalSubject;
    }

    public Subject getSubject() {
        return subject;
    }

    public IdealPlanEntries subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public IdealPlan getIdealplan() {
        return idealplan;
    }

    public IdealPlanEntries idealplan(IdealPlan idealPlan) {
        this.idealplan = idealPlan;
        return this;
    }

    public void setIdealplan(IdealPlan idealPlan) {
        this.idealplan = idealPlan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IdealPlanEntries idealPlanEntries = (IdealPlanEntries) o;
        if (idealPlanEntries.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), idealPlanEntries.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IdealPlanEntries{" +
            "id=" + getId() +
            ", winterSemesterDefault='" + getWinterSemesterDefault() + "'" +
            ", summerSemesterDefault='" + getSummerSemesterDefault() + "'" +
            ", optionalSubject='" + isOptionalSubject() + "'" +
            "}";
    }
}
