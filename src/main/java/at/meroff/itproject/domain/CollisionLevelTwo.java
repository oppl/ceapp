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
            "}";
    }
}
