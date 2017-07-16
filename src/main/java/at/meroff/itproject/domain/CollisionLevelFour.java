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
 * A CollisionLevelFour.
 */
@Entity
@Table(name = "collision_level_four")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "collisionlevelfour")
public class CollisionLevelFour implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "exam_collision")
    private Integer examCollision;

    @ManyToOne
    private CollisionLevelThree collisionLevelThree;

    @OneToMany(mappedBy = "collisionLevelFour")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CollisionLevelFive> collisionLevelFives = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExamCollision() {
        return examCollision;
    }

    public CollisionLevelFour examCollision(Integer examCollision) {
        this.examCollision = examCollision;
        return this;
    }

    public void setExamCollision(Integer examCollision) {
        this.examCollision = examCollision;
    }

    public CollisionLevelThree getCollisionLevelThree() {
        return collisionLevelThree;
    }

    public CollisionLevelFour collisionLevelThree(CollisionLevelThree collisionLevelThree) {
        this.collisionLevelThree = collisionLevelThree;
        return this;
    }

    public void setCollisionLevelThree(CollisionLevelThree collisionLevelThree) {
        this.collisionLevelThree = collisionLevelThree;
    }

    public Set<CollisionLevelFive> getCollisionLevelFives() {
        return collisionLevelFives;
    }

    public CollisionLevelFour collisionLevelFives(Set<CollisionLevelFive> collisionLevelFives) {
        this.collisionLevelFives = collisionLevelFives;
        return this;
    }

    public CollisionLevelFour addCollisionLevelFive(CollisionLevelFive collisionLevelFive) {
        this.collisionLevelFives.add(collisionLevelFive);
        collisionLevelFive.setCollisionLevelFour(this);
        return this;
    }

    public CollisionLevelFour removeCollisionLevelFive(CollisionLevelFive collisionLevelFive) {
        this.collisionLevelFives.remove(collisionLevelFive);
        collisionLevelFive.setCollisionLevelFour(null);
        return this;
    }

    public void setCollisionLevelFives(Set<CollisionLevelFive> collisionLevelFives) {
        this.collisionLevelFives = collisionLevelFives;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CollisionLevelFour collisionLevelFour = (CollisionLevelFour) o;
        if (collisionLevelFour.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collisionLevelFour.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollisionLevelFour{" +
            "id=" + getId() +
            ", examCollision='" + getExamCollision() + "'" +
            "}";
    }
}
