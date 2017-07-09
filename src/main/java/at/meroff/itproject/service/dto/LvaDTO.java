package at.meroff.itproject.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import at.meroff.itproject.domain.enumeration.LvaType;
import at.meroff.itproject.domain.enumeration.Semester;

/**
 * A DTO for the Lva entity.
 */
public class LvaDTO implements Serializable {

    private Long id;

    @NotNull
    private String lvaNr;

    @NotNull
    private LvaType lvaType;

    @NotNull
    private Integer year;

    @NotNull
    private Semester semester;

    private Long subjectId;

    private String subjectSubjectName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLvaNr() {
        return lvaNr;
    }

    public void setLvaNr(String lvaNr) {
        this.lvaNr = lvaNr;
    }

    public LvaType getLvaType() {
        return lvaType;
    }

    public void setLvaType(LvaType lvaType) {
        this.lvaType = lvaType;
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

        LvaDTO lvaDTO = (LvaDTO) o;
        if(lvaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lvaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LvaDTO{" +
            "id=" + getId() +
            ", lvaNr='" + getLvaNr() + "'" +
            ", lvaType='" + getLvaType() + "'" +
            ", year='" + getYear() + "'" +
            ", semester='" + getSemester() + "'" +
            "}";
    }
}
