package at.meroff.itproject.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import at.meroff.itproject.domain.enumeration.Semester;

/**
 * A DTO for the IdealPlan entity.
 */
public class IdealPlanDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer year;

    @NotNull
    private Semester semester;

    private Long curriculumId;

    private String curriculumCurName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Long getCurriculumId() {
        return curriculumId;
    }

    public void setCurriculumId(Long curriculumId) {
        this.curriculumId = curriculumId;
    }

    public String getCurriculumCurName() {
        return curriculumCurName;
    }

    public void setCurriculumCurName(String curriculumCurName) {
        this.curriculumCurName = curriculumCurName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IdealPlanDTO idealPlanDTO = (IdealPlanDTO) o;
        if(idealPlanDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), idealPlanDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IdealPlanDTO{" +
            "id=" + getId() +
            ", year='" + getYear() + "'" +
            ", semester='" + getSemester() + "'" +
            "}";
    }
}
