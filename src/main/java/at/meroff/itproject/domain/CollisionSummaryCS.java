package at.meroff.itproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CollisionSummaryCS.
 */
@Entity
@Table(name = "collision_summary_cs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CollisionSummaryCS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "institute_collision")
    private Integer instituteCollision;

    @OneToMany(mappedBy = "collision")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CollisionSummaryLva> collisionsummarylvas = new HashSet<>();

    @ManyToOne
    private CurriculumSubject csSource;

    @ManyToOne
    private CurriculumSubject csTarget;

    @ManyToOne
    private IdealPlan idealPlan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInstituteCollision() {
        return instituteCollision;
    }

    public CollisionSummaryCS instituteCollision(Integer instituteCollision) {
        this.instituteCollision = instituteCollision;
        return this;
    }

    public void setInstituteCollision(Integer instituteCollision) {
        this.instituteCollision = instituteCollision;
    }

    public Set<CollisionSummaryLva> getCollisionsummarylvas() {
        return collisionsummarylvas;
    }

    public CollisionSummaryCS collisionsummarylvas(Set<CollisionSummaryLva> collisionSummaryLvas) {
        this.collisionsummarylvas = collisionSummaryLvas;
        return this;
    }

    public CollisionSummaryCS addCollisionsummarylva(CollisionSummaryLva collisionSummaryLva) {
        this.collisionsummarylvas.add(collisionSummaryLva);
        collisionSummaryLva.setCollision(this);
        return this;
    }

    public CollisionSummaryCS removeCollisionsummarylva(CollisionSummaryLva collisionSummaryLva) {
        this.collisionsummarylvas.remove(collisionSummaryLva);
        collisionSummaryLva.setCollision(null);
        return this;
    }

    public void setCollisionsummarylvas(Set<CollisionSummaryLva> collisionSummaryLvas) {
        this.collisionsummarylvas = collisionSummaryLvas;
    }

    public CurriculumSubject getCsSource() {
        return csSource;
    }

    public CollisionSummaryCS csSource(CurriculumSubject curriculumSubject) {
        this.csSource = curriculumSubject;
        return this;
    }

    public void setCsSource(CurriculumSubject curriculumSubject) {
        this.csSource = curriculumSubject;
    }

    public CurriculumSubject getCsTarget() {
        return csTarget;
    }

    public CollisionSummaryCS csTarget(CurriculumSubject curriculumSubject) {
        this.csTarget = curriculumSubject;
        return this;
    }

    public void setCsTarget(CurriculumSubject curriculumSubject) {
        this.csTarget = curriculumSubject;
    }

    public IdealPlan getIdealPlan() {
        return idealPlan;
    }

    public CollisionSummaryCS idealPlan(IdealPlan idealPlan) {
        this.idealPlan = idealPlan;
        return this;
    }

    public void setIdealPlan(IdealPlan idealPlan) {
        this.idealPlan = idealPlan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CollisionSummaryCS collisionSummaryCS = (CollisionSummaryCS) o;
        if (collisionSummaryCS.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collisionSummaryCS.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollisionSummaryCS{" +
            "id=" + getId() +
            ", instituteCollision='" + getInstituteCollision() + "'" +
            "}";
    }
}
