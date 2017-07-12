package at.meroff.itproject.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CollisionSummaryLva.
 */
@Entity
@Table(name = "collision_summary_lva")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "collisionsummarylva")
public class CollisionSummaryLva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "institute_collision")
    private Integer instituteCollision;

    @ManyToOne
    private CollisionSummaryCS collision;

    @ManyToOne
    private Lva l1;

    @ManyToOne
    private Lva l2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInstituteCollision() {
        return instituteCollision;
    }

    public CollisionSummaryLva instituteCollision(Integer instituteCollision) {
        this.instituteCollision = instituteCollision;
        return this;
    }

    public void setInstituteCollision(Integer instituteCollision) {
        this.instituteCollision = instituteCollision;
    }

    public CollisionSummaryCS getCollision() {
        return collision;
    }

    public CollisionSummaryLva collision(CollisionSummaryCS collisionSummaryCS) {
        this.collision = collisionSummaryCS;
        return this;
    }

    public void setCollision(CollisionSummaryCS collisionSummaryCS) {
        this.collision = collisionSummaryCS;
    }

    public Lva getL1() {
        return l1;
    }

    public CollisionSummaryLva l1(Lva lva) {
        this.l1 = lva;
        return this;
    }

    public void setL1(Lva lva) {
        this.l1 = lva;
    }

    public Lva getL2() {
        return l2;
    }

    public CollisionSummaryLva l2(Lva lva) {
        this.l2 = lva;
        return this;
    }

    public void setL2(Lva lva) {
        this.l2 = lva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CollisionSummaryLva collisionSummaryLva = (CollisionSummaryLva) o;
        if (collisionSummaryLva.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collisionSummaryLva.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollisionSummaryLva{" +
            "id=" + getId() +
            ", instituteCollision='" + getInstituteCollision() + "'" +
            "}";
    }
}
