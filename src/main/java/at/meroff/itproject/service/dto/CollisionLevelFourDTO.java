package at.meroff.itproject.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CollisionLevelFour entity.
 */
public class CollisionLevelFourDTO implements Serializable {

    private Long id;

    private Integer examCollision;

    private Long collisionLevelThreeId;

    private Set<AppointmentDTO> sourceAppointments = new HashSet<>();

    private Set<AppointmentDTO> targetAppointments = new HashSet<>();

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

    public Long getCollisionLevelThreeId() {
        return collisionLevelThreeId;
    }

    public void setCollisionLevelThreeId(Long collisionLevelThreeId) {
        this.collisionLevelThreeId = collisionLevelThreeId;
    }

    public Set<AppointmentDTO> getSourceAppointments() {
        return sourceAppointments;
    }

    public void setSourceAppointments(Set<AppointmentDTO> appointments) {
        this.sourceAppointments = appointments;
    }

    public Set<AppointmentDTO> getTargetAppointments() {
        return targetAppointments;
    }

    public void setTargetAppointments(Set<AppointmentDTO> appointments) {
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

        CollisionLevelFourDTO collisionLevelFourDTO = (CollisionLevelFourDTO) o;
        if(collisionLevelFourDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collisionLevelFourDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollisionLevelFourDTO{" +
            "id=" + getId() +
            ", examCollision='" + getExamCollision() + "'" +
            "}";
    }
}
