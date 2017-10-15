package at.meroff.itproject.service.dto;


import at.meroff.itproject.domain.enumeration.SubjectType;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CurriculumSubject entity.
 */
public class CurriculumSubjectDTO implements Serializable {

    private Long id;

    private Integer countLvas;

    private Set<LvaDTO> lvas = new HashSet<>();

    private Long subjectId;

    private String subjectSubjectName;

    /**
     * Subject type of the linked subject
     */
    private SubjectType subjectSubjectType;

    private Long curriculumSemesterId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCountLvas() {
        return countLvas;
    }

    public void setCountLvas(Integer countLvas) {
        this.countLvas = countLvas;
    }

    public Set<LvaDTO> getLvas() {
        return lvas;
    }

    public void setLvas(Set<LvaDTO> lvas) {
        this.lvas = lvas;
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

    public Long getCurriculumSemesterId() {
        return curriculumSemesterId;
    }

    public void setCurriculumSemesterId(Long curriculumSemesterId) {
        this.curriculumSemesterId = curriculumSemesterId;
    }

    /**
     * Method returns the SubjectType of the linked SubjectType
     * @return SubjectType of the linked subject
     */
    public SubjectType getSubjectSubjectType() {
        return subjectSubjectType;
    }

    /**
     * Method updates the SubjectType of the linked Subject
     * @param subjectSubjectType
     */
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
            ", countLvas='" + getCountLvas() + "'" +
            "}";
    }
}
