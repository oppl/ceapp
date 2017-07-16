package at.meroff.itproject.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CollisionLevelFive entity.
 */
public class CollisionLevelFiveDTO implements Serializable {

    private Long id;

    private Integer examCollision;

    private Double collisionValue;

    private Long collisionLevelFourId;

    private Long sourceAppointmentId;

    private Long targetAppointmentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExamCollision() {
        return examCollision;
    }

    public void setExamCollision(Integer examCollision) {
        this.examCollision = examCollision;
    }

    public Double getCollisionValue() {
        return collisionValue;
    }

    public void setCollisionValue(Double collisionValue) {
        this.collisionValue = collisionValue;
    }

    public Long getCollisionLevelFourId() {
        return collisionLevelFourId;
    }

    public void setCollisionLevelFourId(Long collisionLevelFourId) {
        this.collisionLevelFourId = collisionLevelFourId;
    }

    public Long getSourceAppointmentId() {
        return sourceAppointmentId;
    }

    public void setSourceAppointmentId(Long appointmentId) {
        this.sourceAppointmentId = appointmentId;
    }

    public Long getTargetAppointmentId() {
        return targetAppointmentId;
    }

    public void setTargetAppointmentId(Long appointmentId) {
        this.targetAppointmentId = appointmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CollisionLevelFiveDTO collisionLevelFiveDTO = (CollisionLevelFiveDTO) o;
        if(collisionLevelFiveDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collisionLevelFiveDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollisionLevelFiveDTO{" +
            "id=" + getId() +
            ", examCollision='" + getExamCollision() + "'" +
            ", collisionValue='" + getCollisionValue() + "'" +
            "}";
    }
}
