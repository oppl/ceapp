package at.meroff.itproject.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import at.meroff.itproject.domain.Curriculum;
import at.meroff.itproject.domain.enumeration.Semester;

/**
 * A DTO for the CurriculumSemester entity.
 */
public class CurriculumSemesterDTO implements Serializable {

    private Long id;

    private Integer year;

    private Semester semester;

    private Curriculum curriculum;

    private Long curriculumId;

    private Integer curriculumCurId;

    private String curriculumCurName;

    private Set<CurriculumSubjectDTO> curriculumSubjects = new HashSet<>();

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

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
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

    public Set<CurriculumSubjectDTO> getCurriculumSubjects() {
        return curriculumSubjects;
    }

    public void setCurriculumSubjects(Set<CurriculumSubjectDTO> curriculumSubjects) {
        this.curriculumSubjects = curriculumSubjects;
    }

    public Integer getCurriculumCurId() {
        return curriculumCurId;
    }

    public void setCurriculumCurId(Integer curriculumCurId) {
        this.curriculumCurId = curriculumCurId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurriculumSemesterDTO curriculumSemesterDTO = (CurriculumSemesterDTO) o;
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
