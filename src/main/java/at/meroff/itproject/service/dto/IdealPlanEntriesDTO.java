package at.meroff.itproject.service.dto;


import at.meroff.itproject.domain.enumeration.SubjectType;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the IdealPlanEntries entity.
 */
public class IdealPlanEntriesDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer winterSemesterDefault;

    @NotNull
    private Integer summerSemesterDefault;

    private Boolean optionalSubject;

    private Boolean exclusive;

    private Long subjectId;

    private String subjectSubjectName;

    private SubjectType subjectSubjectType;

    private Long idealplanId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWinterSemesterDefault() {
        return winterSemesterDefault;
    }

    public void setWinterSemesterDefault(Integer winterSemesterDefault) {
        this.winterSemesterDefault = winterSemesterDefault;
    }

    public Integer getSummerSemesterDefault() {
        return summerSemesterDefault;
    }

    public void setSummerSemesterDefault(Integer summerSemesterDefault) {
        this.summerSemesterDefault = summerSemesterDefault;
    }

    public Boolean isOptionalSubject() {
        return optionalSubject;
    }

    public void setOptionalSubject(Boolean optionalSubject) {
        this.optionalSubject = optionalSubject;
    }

    public Boolean isExclusive() {
        return exclusive;
    }

    public void setExclusive(Boolean exclusive) {
        this.exclusive = exclusive;
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

    public Long getIdealplanId() {
        return idealplanId;
    }

    public void setIdealplanId(Long idealPlanId) {
        this.idealplanId = idealPlanId;
    }

    public SubjectType getSubjectSubjectType() {
        return subjectSubjectType;
    }

    public void setSubjectSubjectType(SubjectType subjectSubjectType) {
        this.subjectSubjectType = subjectSubjectType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IdealPlanEntriesDTO idealPlanEntriesDTO = (IdealPlanEntriesDTO) o;
        if(idealPlanEntriesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), idealPlanEntriesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IdealPlanEntriesDTO{" +
            "id=" + getId() +
            ", winterSemesterDefault='" + getWinterSemesterDefault() + "'" +
            ", summerSemesterDefault='" + getSummerSemesterDefault() + "'" +
            ", optionalSubject='" + isOptionalSubject() + "'" +
            ", exclusive='" + isExclusive() + "'" +
            "}";
    }
}
