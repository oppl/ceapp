package at.meroff.itproject.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CollisionLevelThree entity.
 */
public class CollisionLevelThreeDTO implements Serializable {

    private Long id;

    private Integer examCollision;

    private Long collisionLevelTwoId;

    private Long curriculumSubjectId;

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

    public Long getCollisionLevelTwoId() {
        return collisionLevelTwoId;
    }

    public void setCollisionLevelTwoId(Long collisionLevelTwoId) {
        this.collisionLevelTwoId = collisionLevelTwoId;
    }

    public Long getCurriculumSubjectId() {
        return curriculumSubjectId;
    }

    public void setCurriculumSubjectId(Long curriculumSubjectId) {
        this.curriculumSubjectId = curriculumSubjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CollisionLevelThreeDTO collisionLevelThreeDTO = (CollisionLevelThreeDTO) o;
        if(collisionLevelThreeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collisionLevelThreeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollisionLevelThreeDTO{" +
            "id=" + getId() +
            ", examCollision='" + getExamCollision() + "'" +
            "}";
    }
}
