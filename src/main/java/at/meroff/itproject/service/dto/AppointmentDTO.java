package at.meroff.itproject.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Appointment entity.
 */
public class AppointmentDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime startDateTime;

    @NotNull
    private ZonedDateTime endDateTime;

    @NotNull
    private Boolean isExam;

    private String room;

    private String theme;

    private Long lvaId;

    private String lvaLvaNr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Boolean isIsExam() {
        return isExam;
    }

    public void setIsExam(Boolean isExam) {
        this.isExam = isExam;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Long getLvaId() {
        return lvaId;
    }

    public void setLvaId(Long lvaId) {
        this.lvaId = lvaId;
    }

    public String getLvaLvaNr() {
        return lvaLvaNr;
    }

    public void setLvaLvaNr(String lvaLvaNr) {
        this.lvaLvaNr = lvaLvaNr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppointmentDTO appointmentDTO = (AppointmentDTO) o;
        if(appointmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appointmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppointmentDTO{" +
            "id=" + getId() +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", isExam='" + isIsExam() + "'" +
            ", room='" + getRoom() + "'" +
            ", theme='" + getTheme() + "'" +
            "}";
    }
}
