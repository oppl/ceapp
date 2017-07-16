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

    @ManyToOne
    private CollisionLevelTwo collisionLevelTwo;

    @OneToMany(mappedBy = "collisionLevelThree")
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
            "}";
    }
}
