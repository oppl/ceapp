package at.meroff.itproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CollisionLevelThree.
 */
@Entity
@Table(name = "collision_level_three")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "collisionlevelthree")
public class CollisionLevelThree implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "exam_collision")
    private Integer examCollision;

    @Column(name = "institute_collision")
    private Integer instituteCollision;

    @Column(name = "curriculum_collision")
    private Integer curriculumCollision;

    @Column(name = "collision_value_avg")
    private Double collisionValueAvg;

    @Column(name = "collision_value_max")
    private Double collisionValueMax;

    @Column(name = "col_ws")
    private Boolean colWS;

    @Column(name = "col_ss")
    private Boolean colSS;

    @Column(name = "count_collision_lvas")
    private Integer countCollisionLvas;

    @ManyToOne
    private CollisionLevelTwo collisionLevelTwo;

    // changed the CascadeType
    @OneToMany(mappedBy = "collisionLevelThree", cascade = CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CollisionLevelFour> collisionLevelFours = new HashSet<>();

    @ManyToOne
    private CurriculumSubject curriculumSubject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExamCollision() {
        return examCollision;
    }

    public CollisionLevelThree examCollision(Integer examCollision) {
        this.examCollision = examCollision;
        return this;
    }

    public void setExamCollision(Integer examCollision) {
        this.examCollision = examCollision;
    }

    public Integer getInstituteCollision() {
        return instituteCollision;
    }

    public CollisionLevelThree instituteCollision(Integer instituteCollision) {
        this.instituteCollision = instituteCollision;
        return this;
    }

    public void setInstituteCollision(Integer instituteCollision) {
        this.instituteCollision = instituteCollision;
    }

    public Integer getCurriculumCollision() {
        return curriculumCollision;
    }

    public CollisionLevelThree curriculumCollision(Integer curriculumCollision) {
        this.curriculumCollision = curriculumCollision;
        return this;
    }

    public void setCurriculumCollision(Integer curriculumCollision) {
        this.curriculumCollision = curriculumCollision;
    }

    public Double getCollisionValueAvg() {
        return collisionValueAvg;
    }

    public CollisionLevelThree collisionValueAvg(Double collisionValueAvg) {
        this.collisionValueAvg = collisionValueAvg;
        return this;
    }

    public void setCollisionValueAvg(Double collisionValueAvg) {
        this.collisionValueAvg = collisionValueAvg;
    }

    public Double getCollisionValueMax() {
        return collisionValueMax;
    }

    public CollisionLevelThree collisionValueMax(Double collisionValueMax) {
        this.collisionValueMax = collisionValueMax;
        return this;
    }

    public void setCollisionValueMax(Double collisionValueMax) {
        this.collisionValueMax = collisionValueMax;
    }

    public Boolean isColWS() {
        return colWS;
    }

    public CollisionLevelThree colWS(Boolean colWS) {
        this.colWS = colWS;
        return this;
    }

    public void setColWS(Boolean colWS) {
        this.colWS = colWS;
    }

    public Boolean isColSS() {
        return colSS;
    }

    public CollisionLevelThree colSS(Boolean colSS) {
        this.colSS = colSS;
        return this;
    }

    public void setColSS(Boolean colSS) {
        this.colSS = colSS;
    }

    public Integer getCountCollisionLvas() {
        return countCollisionLvas;
    }

    public CollisionLevelThree countCollisionLvas(Integer countCollisionLvas) {
        this.countCollisionLvas = countCollisionLvas;
        return this;
    }

    public void setCountCollisionLvas(Integer countCollisionLvas) {
        this.countCollisionLvas = countCollisionLvas;
    }

    public CollisionLevelTwo getCollisionLevelTwo() {
        return collisionLevelTwo;
    }

    public CollisionLevelThree collisionLevelTwo(CollisionLevelTwo collisionLevelTwo) {
        this.collisionLevelTwo = collisionLevelTwo;
        return this;
    }

    public void setCollisionLevelTwo(CollisionLevelTwo collisionLevelTwo) {
        this.collisionLevelTwo = collisionLevelTwo;
    }

    public Set<CollisionLevelFour> getCollisionLevelFours() {
        return collisionLevelFours;
    }

    public CollisionLevelThree collisionLevelFours(Set<CollisionLevelFour> collisionLevelFours) {
        this.collisionLevelFours = collisionLevelFours;
        return this;
    }

    public CollisionLevelThree addCollisionLevelFour(CollisionLevelFour collisionLevelFour) {
        this.collisionLevelFours.add(collisionLevelFour);
        collisionLevelFour.setCollisionLevelThree(this);
        return this;
    }

    public CollisionLevelThree removeCollisionLevelFour(CollisionLevelFour collisionLevelFour) {
        this.collisionLevelFours.remove(collisionLevelFour);
        collisionLevelFour.setCollisionLevelThree(null);
        return this;
    }

    public void setCollisionLevelFours(Set<CollisionLevelFour> collisionLevelFours) {
        this.collisionLevelFours = collisionLevelFours;
    }

    public CurriculumSubject getCurriculumSubject() {
        return curriculumSubject;
    }

    public CollisionLevelThree curriculumSubject(CurriculumSubject curriculumSubject) {
        this.curriculumSubject = curriculumSubject;
        return this;
    }

    public void setCurriculumSubject(CurriculumSubject curriculumSubject) {
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
        CollisionLevelThree collisionLevelThree = (CollisionLevelThree) o;
        if (collisionLevelThree.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collisionLevelThree.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollisionLevelThree{" +
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
