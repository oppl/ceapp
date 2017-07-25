package at.meroff.itproject.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import at.meroff.itproject.domain.enumeration.CollisionType;

/**
 * A DTO for the CollisionLevelFour entity.
 */
public class CollisionLevelFourDTO implements Serializable {

    private Long id;

    private Integer examCollision;

    private Integer instituteCollision;

    private Integer curriculumCollision;

    private CollisionType collisionType;

    private Double collisionValue;

    private Long collisionLevelThreeId;

    private Long lvaId;

    private String lvaLvaNr;

    private LvaDTO lva;

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

    public Integer getInstituteCollision() {
        return instituteCollision;
    }

    public void setInstituteCollision(Integer instituteCollision) {
        this.instituteCollision = instituteCollision;
    }

    public Integer getCurriculumCollision() {
        return curriculumCollision;
    }

    public void setCurriculumCollision(Integer curriculumCollision) {
        this.curriculumCollision = curriculumCollision;
    }

    public CollisionType getCollisionType() {
        return collisionType;
    }

    public void setCollisionType(CollisionType collisionType) {
        this.collisionType = collisionType;
    }

    public Double getCollisionValue() {
        return collisionValue;
    }

    public void setCollisionValue(Double collisionValue) {
        this.collisionValue = collisionValue;
    }

    public Long getCollisionLevelThreeId() {
        return collisionLevelThreeId;
    }

    public void setCollisionLevelThreeId(Long collisionLevelThreeId) {
        this.collisionLevelThreeId = collisionLevelThreeId;
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

    public LvaDTO getLva() {
        return lva;
    }

    public void setLva(LvaDTO lva) {
        this.lva = lva;
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
            ", instituteCollision='" + getInstituteCollision() + "'" +
            ", curriculumCollision='" + getCurriculumCollision() + "'" +
            ", collisionType='" + getCollisionType() + "'" +
            ", collisionValue='" + getCollisionValue() + "'" +
            "}";
    }
}
