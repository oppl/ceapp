package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.AppointmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Appointment and its DTO AppointmentDTO.
 */
@Mapper(componentModel = "spring", uses = {LvaMapper.class, })
public interface AppointmentMapper extends EntityMapper <AppointmentDTO, Appointment> {

    @Mapping(source = "lva.id", target = "lvaId")
    @Mapping(source = "lva.lvaNr", target = "lvaLvaNr")
    AppointmentDTO toDto(Appointment appointment); 

    @Mapping(source = "lvaId", target = "lva")
    @Mapping(target = "sourceCollisionLevelFours", ignore = true)
    @Mapping(target = "targetCollisionLevelFours", ignore = true)
    Appointment toEntity(AppointmentDTO appointmentDTO); 
    default Appointment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Appointment appointment = new Appointment();
        appointment.setId(id);
        return appointment;
    }
}
