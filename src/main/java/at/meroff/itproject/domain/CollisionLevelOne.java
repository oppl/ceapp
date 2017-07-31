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
 * A CollisionLevelOne.
 */
@Entity
@Table(name = "collision_level_one")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "collisionlevelone")
public class CollisionLevelOne implements Serializable {

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
    private CurriculumSubject curriculumSubject;

    @OneToMany(mappedBy = "collisionLevelOne", cascade = CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CollisionLevelTwo> collisionLevelTwos = new HashSet<>();

    @ManyToOne
    private IdealPlan idealPlan;

    @ManyToOne
    private Institute institute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExamCollision() {
        return examCollision;
    }

    public CollisionLevelOne examCollision(Integer examCollision) {
        this.examCollision = examCollision;
        return this;
    }

    public void setExamCollision(Integer examCollision) {
        this.examCollision = examCollision;
    }

    public Integer getInstituteCollision() {
        return instituteCollision;
    }

    public CollisionLevelOne instituteCollision(Integer instituteCollision) {
        this.instituteCollision = instituteCollision;
        return this;
    }

    public void setInstituteCollision(Integer instituteCollision) {
        this.instituteCollision = instituteCollision;
    }

    public Integer getCurriculumCollision() {
        return curriculumCollision;
    }

    public CollisionLevelOne curriculumCollision(Integer curriculumCollision) {
        this.curriculumCollision = curriculumCollision;
        return this;
    }

    public void setCurriculumCollision(Integer curriculumCollision) {
        this.curriculumCollision = curriculumCollision;
    }

    public Double getCollisionValueAvg() {
        return collisionValueAvg;
    }

    public CollisionLevelOne collisionValueAvg(Double collisionValueAvg) {
        this.collisionValueAvg = collisionValueAvg;
        return this;
    }

    public void setCollisionValueAvg(Double collisionValueAvg) {
        this.collisionValueAvg = collisionValueAvg;
    }

    public Double getCollisionValueMax() {
        return collisionValueMax;
    }

    public CollisionLevelOne collisionValueMax(Double collisionValueMax) {
        this.collisionValueMax = collisionValueMax;
        return this;
    }

    public void setCollisionValueMax(Double collisionValueMax) {
        this.collisionValueMax = collisionValueMax;
    }

    public Boolean isColWS() {
        return colWS;
    }

    public CollisionLevelOne colWS(Boolean colWS) {
        this.colWS = colWS;
        return this;
    }

    public void setColWS(Boolean colWS) {
        this.colWS = colWS;
    }

    public Boolean isColSS() {
        return colSS;
    }

    public CollisionLevelOne colSS(Boolean colSS) {
        this.colSS = colSS;
        return this;
    }

    public void setColSS(Boolean colSS) {
        this.colSS = colSS;
    }

    public CurriculumSubject getCurriculumSubject() {
        return curriculumSubject;
    }

    public CollisionLevelOne curriculumSubject(CurriculumSubject curriculumSubject) {
        this.curriculumSubject = curriculumSubject;
        return this;
    }

    public void setCurriculumSubject(CurriculumSubject curriculumSubject) {
        this.curriculumSubject = curriculumSubject;
    }

    public Set<CollisionLevelTwo> getCollisionLevelTwos() {
        return collisionLevelTwos;
    }

    public CollisionLevelOne collisionLevelTwos(Set<CollisionLevelTwo> collisionLevelTwos) {
        this.collisionLevelTwos = collisionLevelTwos;
        return this;
    }

    public CollisionLevelOne addCollisionLevelTwo(CollisionLevelTwo collisionLevelTwo) {
        this.collisionLevelTwos.add(collisionLevelTwo);
        collisionLevelTwo.setCollisionLevelOne(this);
        return this;
    }

    public CollisionLevelOne removeCollisionLevelTwo(CollisionLevelTwo collisionLevelTwo) {
        this.collisionLevelTwos.remove(collisionLevelTwo);
        collisionLevelTwo.setCollisionLevelOne(null);
        return this;
    }

    public void setCollisionLevelTwos(Set<CollisionLevelTwo> collisionLevelTwos) {
        this.collisionLevelTwos = collisionLevelTwos;
    }

    public IdealPlan getIdealPlan() {
        return idealPlan;
    }

    public CollisionLevelOne idealPlan(IdealPlan idealPlan) {
        this.idealPlan = idealPlan;
        return this;
    }

    public void setIdealPlan(IdealPlan idealPlan) {
        this.idealPlan = idealPlan;
    }

    public Institute getInstitute() {
        return institute;
    }

    public CollisionLevelOne institute(Institute institute) {
        this.institute = institute;
        return this;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CollisionLevelOne collisionLevelOne = (CollisionLevelOne) o;
        if (collisionLevelOne.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collisionLevelOne.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollisionLevelOne{" +
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
