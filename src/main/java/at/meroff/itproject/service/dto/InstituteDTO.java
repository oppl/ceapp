package at.meroff.itproject.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Institute entity.
 */
public class InstituteDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer instituteId;

    @NotNull
    private String instituteName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(Integer instituteId) {
        this.instituteId = instituteId;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstituteDTO instituteDTO = (InstituteDTO) o;
        if(instituteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), instituteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InstituteDTO{" +
            "id=" + getId() +
            ", instituteId='" + getInstituteId() + "'" +
            ", instituteName='" + getInstituteName() + "'" +
            "}";
    }
}
