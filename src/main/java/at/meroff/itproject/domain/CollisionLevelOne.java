package at.meroff.itproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
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

    public CollisionLevelOne examCollision(Integer examCollision) {
        this.examCollision = examCollision;
        return this;
    }

    public void setExamCollision(Integer examCollision) {
        this.examCollision = examCollision;
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
            "}";
    }
}
