package at.meroff.itproject.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CollisionSummaryLva entity.
 */
public class CollisionSummaryLvaDTO implements Serializable {

    private Long id;

    private Integer instituteCollision;

    private Long collisionId;

    private Long l1Id;

    private Long l2Id;

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

    public Long getCollisionId() {
        return collisionId;
    }

    public void setCollisionId(Long collisionSummaryCSId) {
        this.collisionId = collisionSummaryCSId;
    }

    public Long getL1Id() {
        return l1Id;
    }

    public void setL1Id(Long lvaId) {
        this.l1Id = lvaId;
    }

    public Long getL2Id() {
        return l2Id;
    }

    public void setL2Id(Long lvaId) {
        this.l2Id = lvaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CollisionSummaryLvaDTO collisionSummaryLvaDTO = (CollisionSummaryLvaDTO) o;
        if(collisionSummaryLvaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collisionSummaryLvaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollisionSummaryLvaDTO{" +
            "id=" + getId() +
            ", instituteCollision='" + getInstituteCollision() + "'" +
            "}";
    }
}
