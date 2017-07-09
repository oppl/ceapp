package at.meroff.itproject.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Curriculum entity.
 */
public class CurriculumDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer curId;

    @NotNull
    private String curName;

    private Set<InstituteDTO> institutes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCurId() {
        return curId;
    }

    public void setCurId(Integer curId) {
        this.curId = curId;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public Set<InstituteDTO> getInstitutes() {
        return institutes;
    }

    public void setInstitutes(Set<InstituteDTO> institutes) {
        this.institutes = institutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurriculumDTO curriculumDTO = (CurriculumDTO) o;
        if(curriculumDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), curriculumDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurriculumDTO{" +
            "id=" + getId() +
            ", curId='" + getCurId() + "'" +
            ", curName='" + getCurName() + "'" +
            "}";
    }
}
