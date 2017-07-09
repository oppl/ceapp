package at.meroff.itproject.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import at.meroff.itproject.domain.enumeration.Semester;

/**
 * A DTO for the CurriculumSubject entity.
 */
public class CurriculumSubjectDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer year;

    @NotNull
    private Semester semester;

    private Set<LvaDTO> lvas = new HashSet<>();

    private Long curriculumId;

    private String curriculumCurName;

    private Long subjectId;

    private String subjectSubjectName;

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

    public Set<LvaDTO> getLvas() {
        return lvas;
    }

    public void setLvas(Set<LvaDTO> lvas) {
        this.lvas = lvas;
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

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectSubjectName() {
        return subjectSubjectName;
    }

    public void setSubjectSubjectName(String subjectSubjectName) {
        this.subjectSubjectName = subjectSubjectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurriculumSubjectDTO curriculumSubjectDTO = (CurriculumSubjectDTO) o;
        if(curriculumSubjectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), curriculumSubjectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurriculumSubjectDTO{" +
            "id=" + getId() +
            ", year='" + getYear() + "'" +
            ", semester='" + getSemester() + "'" +
            "}";
    }
}
