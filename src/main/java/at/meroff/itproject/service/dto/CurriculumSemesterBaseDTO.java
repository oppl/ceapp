package at.meroff.itproject.service.dto;


import at.meroff.itproject.domain.enumeration.Semester;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the CurriculumSemester entity.
 */
public class CurriculumSemesterBaseDTO implements Serializable {

    private Long id;

    private Integer year;

    private Semester semester;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurriculumSemesterBaseDTO curriculumSemesterDTO = (CurriculumSemesterBaseDTO) o;
        if(curriculumSemesterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), curriculumSemesterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurriculumSemesterDTO{" +
            "id=" + getId() +
            ", year='" + getYear() + "'" +
            ", semester='" + getSemester() + "'" +
            "}";
    }
}
