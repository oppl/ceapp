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
 * A CollisionLevelTwo.
 */
@Entity
@Table(name = "collision_level_two")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "collisionleveltwo")
public class CollisionLevelTwo implements Serializable {

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

    @ManyToOne
    private CollisionLevelOne collisionLevelOne;

    @OneToMany(mappedBy = "collisionLevelTwo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CollisionLevelThree> collisionLevelThrees = new HashSet<>();

    @ManyToOne
    private Lva lva;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExamCollision() {
        return examCollision;
    }

    public CollisionLevelTwo examCollision(Integer examCollision) {
        this.examCollision = examCollision;
        return this;
    }

    public void setExamCollision(Integer examCollision) {
        this.examCollision = examCollision;
    }

    public Integer getInstituteCollision() {
        return instituteCollision;
    }

    public CollisionLevelTwo instituteCollision(Integer instituteCollision) {
        this.instituteCollision = instituteCollision;
        return this;
    }

    public void setInstituteCollision(Integer instituteCollision) {
        this.instituteCollision = instituteCollision;
    }

    public Integer getCurriculumCollision() {
        return curriculumCollision;
    }

    public CollisionLevelTwo curriculumCollision(Integer curriculumCollision) {
        this.curriculumCollision = curriculumCollision;
        return this;
    }

    public void setCurriculumCollision(Integer curriculumCollision) {
        this.curriculumCollision = curriculumCollision;
    }

    public Double getCollisionValueAvg() {
        return collisionValueAvg;
    }

    public CollisionLevelTwo collisionValueAvg(Double collisionValueAvg) {
        this.collisionValueAvg = collisionValueAvg;
        return this;
    }

    public void setCollisionValueAvg(Double collisionValueAvg) {
        this.collisionValueAvg = collisionValueAvg;
    }

    public Double getCollisionValueMax() {
        return collisionValueMax;
    }

    public CollisionLevelTwo collisionValueMax(Double collisionValueMax) {
        this.collisionValueMax = collisionValueMax;
        return this;
    }

    public void setCollisionValueMax(Double collisionValueMax) {
        this.collisionValueMax = collisionValueMax;
    }

    public Boolean isColWS() {
        return colWS;
    }

    public CollisionLevelTwo colWS(Boolean colWS) {
        this.colWS = colWS;
        return this;
    }

    public void setColWS(Boolean colWS) {
        this.colWS = colWS;
    }

    public Boolean isColSS() {
        return colSS;
    }

    public CollisionLevelTwo colSS(Boolean colSS) {
        this.colSS = colSS;
        return this;
    }

    public void setColSS(Boolean colSS) {
        this.colSS = colSS;
    }

    public CollisionLevelOne getCollisionLevelOne() {
        return collisionLevelOne;
    }

    public CollisionLevelTwo collisionLevelOne(CollisionLevelOne collisionLevelOne) {
        this.collisionLevelOne = collisionLevelOne;
        return this;
    }

    public void setCollisionLevelOne(CollisionLevelOne collisionLevelOne) {
        this.collisionLevelOne = collisionLevelOne;
    }

    public Set<CollisionLevelThree> getCollisionLevelThrees() {
        return collisionLevelThrees;
    }

    public CollisionLevelTwo collisionLevelThrees(Set<CollisionLevelThree> collisionLevelThrees) {
        this.collisionLevelThrees = collisionLevelThrees;
        return this;
    }

    public CollisionLevelTwo addCollisionLevelThree(CollisionLevelThree collisionLevelThree) {
        this.collisionLevelThrees.add(collisionLevelThree);
        collisionLevelThree.setCollisionLevelTwo(this);
        return this;
    }

    public CollisionLevelTwo removeCollisionLevelThree(CollisionLevelThree collisionLevelThree) {
        this.collisionLevelThrees.remove(collisionLevelThree);
        collisionLevelThree.setCollisionLevelTwo(null);
        return this;
    }

    public void setCollisionLevelThrees(Set<CollisionLevelThree> collisionLevelThrees) {
        this.collisionLevelThrees = collisionLevelThrees;
    }

    public Lva getLva() {
        return lva;
    }

    public CollisionLevelTwo lva(Lva lva) {
        this.lva = lva;
        return this;
    }

    public void setLva(Lva lva) {
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
        CollisionLevelTwo collisionLevelTwo = (CollisionLevelTwo) o;
        if (collisionLevelTwo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collisionLevelTwo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollisionLevelTwo{" +
            "id=" + getId() +
            ", examCollision='" + getExamCollision() + "'" +
            ", instituteCollision='" + getInstituteCollision() + "'" +
            ", curriculumCollision='" + getCurriculumCollision() + "'" +
            ", collisionValueAvg='" + getCollisionValueAvg() + "'" +
            ", collisionValueMax='" + getCollisionValueMax() + "'" +
            ", colWS='" + isColWS() + "'" +
            ", colSS='" + isColSS() + "'" +
            "}";
    }
}
