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
 * A CollisionLevelFour.
 */
@Entity
@Table(name = "collision_level_four")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "collisionlevelfour")
public class CollisionLevelFour implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "exam_collision")
    private Integer examCollision;

    @ManyToOne
    private CollisionLevelThree collisionLevelThree;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "collision_level_four_source_appointment",
               joinColumns = @JoinColumn(name="collision_level_fours_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="source_appointments_id", referencedColumnName="id"))
    private Set<Appointment> sourceAppointments = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "collision_level_four_target_appointment",
               joinColumns = @JoinColumn(name="collision_level_fours_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="target_appointments_id", referencedColumnName="id"))
    private Set<Appointment> targetAppointments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExamCollision() {
        return examCollision;
    }

    public CollisionLevelFour examCollision(Integer examCollision) {
        this.examCollision = examCollision;
        return this;
    }

    public void setExamCollision(Integer examCollision) {
        this.examCollision = examCollision;
    }

    public CollisionLevelThree getCollisionLevelThree() {
        return collisionLevelThree;
    }

    public CollisionLevelFour collisionLevelThree(CollisionLevelThree collisionLevelThree) {
        this.collisionLevelThree = collisionLevelThree;
        return this;
    }

    public void setCollisionLevelThree(CollisionLevelThree collisionLevelThree) {
        this.collisionLevelThree = collisionLevelThree;
    }

    public Set<Appointment> getSourceAppointments() {
        return sourceAppointments;
    }

    public CollisionLevelFour sourceAppointments(Set<Appointment> appointments) {
        this.sourceAppointments = appointments;
        return this;
    }

    public CollisionLevelFour addSourceAppointment(Appointment appointment) {
        this.sourceAppointments.add(appointment);
        appointment.getSourceCollisionLevelFours().add(this);
        return this;
    }

    public CollisionLevelFour removeSourceAppointment(Appointment appointment) {
        this.sourceAppointments.remove(appointment);
        appointment.getSourceCollisionLevelFours().remove(this);
        return this;
    }

    public void setSourceAppointments(Set<Appointment> appointments) {
        this.sourceAppointments = appointments;
    }

    public Set<Appointment> getTargetAppointments() {
        return targetAppointments;
    }

    public CollisionLevelFour targetAppointments(Set<Appointment> appointments) {
        this.targetAppointments = appointments;
        return this;
    }

    public CollisionLevelFour addTargetAppointment(Appointment appointment) {
        this.targetAppointments.add(appointment);
        appointment.getTargetCollisionLevelFours().add(this);
        return this;
    }

    public CollisionLevelFour removeTargetAppointment(Appointment appointment) {
        this.targetAppointments.remove(appointment);
        appointment.getTargetCollisionLevelFours().remove(this);
        return this;
    }

    public void setTargetAppointments(Set<Appointment> appointments) {
        this.targetAppointments = appointments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CollisionLevelFour collisionLevelFour = (CollisionLevelFour) o;
        if (collisionLevelFour.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collisionLevelFour.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollisionLevelFour{" +
            "id=" + getId() +
            ", examCollision='" + getExamCollision() + "'" +
            "}";
    }
}
