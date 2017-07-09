package at.meroff.itproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Institute.
 */
@Entity
@Table(name = "institute")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Institute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "institute_id", nullable = false)
    private Integer instituteId;

    @NotNull
    @Column(name = "institute_name", nullable = false)
    private String instituteName;

    @ManyToMany(mappedBy = "institutes")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Curriculum> curricula = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInstituteId() {
        return instituteId;
    }

    public Institute instituteId(Integer instituteId) {
        this.instituteId = instituteId;
        return this;
    }

    public void setInstituteId(Integer instituteId) {
        this.instituteId = instituteId;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public Institute instituteName(String instituteName) {
        this.instituteName = instituteName;
        return this;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public Set<Curriculum> getCurricula() {
        return curricula;
    }

    public Institute curricula(Set<Curriculum> curricula) {
        this.curricula = curricula;
        return this;
    }

    public Institute addCurriculum(Curriculum curriculum) {
        this.curricula.add(curriculum);
        curriculum.getInstitutes().add(this);
        return this;
    }

    public Institute removeCurriculum(Curriculum curriculum) {
        this.curricula.remove(curriculum);
        curriculum.getInstitutes().remove(this);
        return this;
    }

    public void setCurricula(Set<Curriculum> curricula) {
        this.curricula = curricula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Institute institute = (Institute) o;
        if (institute.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), institute.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Institute{" +
            "id=" + getId() +
            ", instituteId='" + getInstituteId() + "'" +
            ", instituteName='" + getInstituteName() + "'" +
            "}";
    }
}
