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

    private Integer instituteCollision;

    private Integer curriculumCollision;

    private Double collisionValueAvg;

    private Double collisionValueMax;

    private Boolean colWS;

    private Boolean colSS;

    private Integer countCollisionLvas;

    private Long collisionLevelTwoId;

    private Long curriculumSubjectId;

    private CurriculumSubjectDTO curriculumSubject;

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

    public Double getCollisionValueAvg() {
        return collisionValueAvg;
    }

    public void setCollisionValueAvg(Double collisionValueAvg) {
        this.collisionValueAvg = collisionValueAvg;
    }

    public Double getCollisionValueMax() {
        return collisionValueMax;
    }

    public void setCollisionValueMax(Double collisionValueMax) {
        this.collisionValueMax = collisionValueMax;
    }

    public Boolean isColWS() {
        return colWS;
    }

    public void setColWS(Boolean colWS) {
        this.colWS = colWS;
    }

    public Boolean isColSS() {
        return colSS;
    }

    public void setColSS(Boolean colSS) {
        this.colSS = colSS;
    }

    public Integer getCountCollisionLvas() {
        return countCollisionLvas;
    }

    public void setCountCollisionLvas(Integer countCollisionLvas) {
        this.countCollisionLvas = countCollisionLvas;
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

    public CurriculumSubjectDTO getCurriculumSubject() {
        return curriculumSubject;
    }

    public void setCurriculumSubject(CurriculumSubjectDTO curriculumSubject) {
        this.curriculumSubject = curriculumSubject;
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
            ", instituteCollision='" + getInstituteCollision() + "'" +
            ", curriculumCollision='" + getCurriculumCollision() + "'" +
            ", collisionValueAvg='" + getCollisionValueAvg() + "'" +
            ", collisionValueMax='" + getCollisionValueMax() + "'" +
            ", colWS='" + isColWS() + "'" +
            ", colSS='" + isColSS() + "'" +
            ", countCollisionLvas='" + getCountCollisionLvas() + "'" +
            "}";
    }
}
