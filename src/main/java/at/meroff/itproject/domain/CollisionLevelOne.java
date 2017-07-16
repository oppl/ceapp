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

    @ManyToOne
    private CurriculumSubject curriculumSubject;

    @OneToMany(mappedBy = "collisionLevelOne")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CollisionLevelTwo> collisionLevelTwos = new HashSet<>();

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
