package at.meroff.itproject.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CollisionSummaryCS entity.
 */
public class CollisionSummaryCSDTO implements Serializable {

    private Long id;

    private Integer instituteCollision;

    private Long csSourceId;

    private Long csTargetId;

    private Long idealPlanId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInstituteCollision() {
        return instituteCollision;
    }

    public void setInstituteCollision(Integer instituteCollision) {
        this.instituteCollision = instituteCollision;
    }

    public Long getCsSourceId() {
        return csSourceId;
    }

    public void setCsSourceId(Long curriculumSubjectId) {
        this.csSourceId = curriculumSubjectId;
    }

    public Long getCsTargetId() {
        return csTargetId;
    }

    public void setCsTargetId(Long curriculumSubjectId) {
        this.csTargetId = curriculumSubjectId;
    }

    public Long getIdealPlanId() {
        return idealPlanId;
    }

    public void setIdealPlanId(Long idealPlanId) {
        this.idealPlanId = idealPlanId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CollisionSummaryCSDTO collisionSummaryCSDTO = (CollisionSummaryCSDTO) o;
        if(collisionSummaryCSDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collisionSummaryCSDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollisionSummaryCSDTO{" +
            "id=" + getId() +
            ", instituteCollision='" + getInstituteCollision() + "'" +
            "}";
    }
}
