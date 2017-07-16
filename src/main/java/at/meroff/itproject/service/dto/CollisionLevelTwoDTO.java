package at.meroff.itproject.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CollisionLevelTwo entity.
 */
public class CollisionLevelTwoDTO implements Serializable {

    private Long id;

    private Integer examCollision;

    private Long collisionLevelOneId;

    private Long lvaId;

    private String lvaLvaNr;

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

    public Long getCollisionLevelOneId() {
        return collisionLevelOneId;
    }

    public void setCollisionLevelOneId(Long collisionLevelOneId) {
        this.collisionLevelOneId = collisionLevelOneId;
    }

    public Long getLvaId() {
        return lvaId;
    }

    public void setLvaId(Long lvaId) {
        this.lvaId = lvaId;
    }

    public String getLvaLvaNr() {
        return lvaLvaNr;
    }

    public void setLvaLvaNr(String lvaLvaNr) {
        this.lvaLvaNr = lvaLvaNr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CollisionLevelTwoDTO collisionLevelTwoDTO = (CollisionLevelTwoDTO) o;
        if(collisionLevelTwoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collisionLevelTwoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollisionLevelTwoDTO{" +
            "id=" + getId() +
            ", examCollision='" + getExamCollision() + "'" +
            "}";
    }
}
