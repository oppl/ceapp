package at.meroff.itproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Appointment.
 */
@Entity
@Table(name = "appointment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "appointment")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "start_date_time", nullable = false)
    private ZonedDateTime startDateTime;

    @NotNull
    @Column(name = "end_date_time", nullable = false)
    private ZonedDateTime endDateTime;

    @NotNull
    @Column(name = "is_exam", nullable = false)
    private Boolean isExam;

    @Column(name = "room")
    private String room;

    @Column(name = "theme")
    private String theme;

    @ManyToOne
    private Lva lva;

    @ManyToMany(mappedBy = "sourceAppointments")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CollisionLevelFour> sourceCollisionLevelFours = new HashSet<>();

    @ManyToMany(mappedBy = "targetAppointments")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CollisionLevelFour> targetCollisionLevelFours = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public Appointment startDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public Appointment endDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Boolean isIsExam() {
        return isExam;
    }

    public Appointment isExam(Boolean isExam) {
        this.isExam = isExam;
        return this;
    }

    public void setIsExam(Boolean isExam) {
        this.isExam = isExam;
    }

    public String getRoom() {
        return room;
    }

    public Appointment room(String room) {
        this.room = room;
        return this;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTheme() {
        return theme;
    }

    public Appointment theme(String theme) {
        this.theme = theme;
        return this;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Lva getLva() {
        return lva;
    }

    public Appointment lva(Lva lva) {
        this.lva = lva;
        return this;
    }

    public void setLva(Lva lva) {
        this.lva = lva;
    }

    public Set<CollisionLevelFour> getSourceCollisionLevelFours() {
        return sourceCollisionLevelFours;
    }

    public Appointment sourceCollisionLevelFours(Set<CollisionLevelFour> collisionLevelFours) {
        this.sourceCollisionLevelFours = collisionLevelFours;
        return this;
    }

    public Appointment addSourceCollisionLevelFour(CollisionLevelFour collisionLevelFour) {
        this.sourceCollisionLevelFours.add(collisionLevelFour);
        collisionLevelFour.getSourceAppointments().add(this);
        return this;
    }

    public Appointment removeSourceCollisionLevelFour(CollisionLevelFour collisionLevelFour) {
        this.sourceCollisionLevelFours.remove(collisionLevelFour);
        collisionLevelFour.getSourceAppointments().remove(this);
        return this;
    }

    public void setSourceCollisionLevelFours(Set<CollisionLevelFour> collisionLevelFours) {
        this.sourceCollisionLevelFours = collisionLevelFours;
    }

    public Set<CollisionLevelFour> getTargetCollisionLevelFours() {
        return targetCollisionLevelFours;
    }

    public Appointment targetCollisionLevelFours(Set<CollisionLevelFour> collisionLevelFours) {
        this.targetCollisionLevelFours = collisionLevelFours;
        return this;
    }

    public Appointment addTargetCollisionLevelFour(CollisionLevelFour collisionLevelFour) {
        this.targetCollisionLevelFours.add(collisionLevelFour);
        collisionLevelFour.getTargetAppointments().add(this);
        return this;
    }

    public Appointment removeTargetCollisionLevelFour(CollisionLevelFour collisionLevelFour) {
        this.targetCollisionLevelFours.remove(collisionLevelFour);
        collisionLevelFour.getTargetAppointments().remove(this);
        return this;
    }

    public void setTargetCollisionLevelFours(Set<CollisionLevelFour> collisionLevelFours) {
        this.targetCollisionLevelFours = collisionLevelFours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Appointment appointment = (Appointment) o;
        if (appointment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appointment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Appointment{" +
            "id=" + getId() +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", isExam='" + isIsExam() + "'" +
            ", room='" + getRoom() + "'" +
            ", theme='" + getTheme() + "'" +
            "}";
    }
}
