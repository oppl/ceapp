package at.meroff.itproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CollisionLevelFive.
 */
@Entity
@Table(name = "collision_level_five")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "collisionlevelfive")
public class CollisionLevelFive implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "exam_collision")
    private Integer examCollision;

    @Column(name = "collision_value")
    private Double collisionValue;

    @ManyToOne
    private CollisionLevelFour collisionLevelFour;

    @ManyToOne
    private Appointment sourceAppointment;

    @ManyToOne
    private Appointment targetAppointment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExamCollision() {
        return examCollision;
    }

    public CollisionLevelFive examCollision(Integer examCollision) {
        this.examCollision = examCollision;
        return this;
    }

    public void setExamCollision(Integer examCollision) {
        this.examCollision = examCollision;
    }

    public Double getCollisionValue() {
        return collisionValue;
    }

    public CollisionLevelFive collisionValue(Double collisionValue) {
        this.collisionValue = collisionValue;
        return this;
    }

    public void setCollisionValue(Double collisionValue) {
        this.collisionValue = collisionValue;
    }

    public CollisionLevelFour getCollisionLevelFour() {
        return collisionLevelFour;
    }

    public CollisionLevelFive collisionLevelFour(CollisionLevelFour collisionLevelFour) {
        this.collisionLevelFour = collisionLevelFour;
        return this;
    }

    public void setCollisionLevelFour(CollisionLevelFour collisionLevelFour) {
        this.collisionLevelFour = collisionLevelFour;
    }

    public Appointment getSourceAppointment() {
        return sourceAppointment;
    }

    public CollisionLevelFive sourceAppointment(Appointment appointment) {
        this.sourceAppointment = appointment;
        return this;
    }

    public void setSourceAppointment(Appointment appointment) {
        this.sourceAppointment = appointment;
    }

    public Appointment getTargetAppointment() {
        return targetAppointment;
    }

    public CollisionLevelFive targetAppointment(Appointment appointment) {
        this.targetAppointment = appointment;
        return this;
    }

    public void setTargetAppointment(Appointment appointment) {
        this.targetAppointment = appointment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CollisionLevelFive collisionLevelFive = (CollisionLevelFive) o;
        if (collisionLevelFive.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collisionLevelFive.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollisionLevelFive{" +
            "id=" + getId() +
            ", examCollision='" + getExamCollision() + "'" +
            ", collisionValue='" + getCollisionValue() + "'" +
            "}";
    }
}
