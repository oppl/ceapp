package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.CollisionLevelFiveDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CollisionLevelFive and its DTO CollisionLevelFiveDTO.
 */
@Mapper(componentModel = "spring", uses = {CollisionLevelFourMapper.class, AppointmentMapper.class, })
public interface CollisionLevelFiveMapper extends EntityMapper <CollisionLevelFiveDTO, CollisionLevelFive> {

    @Mapping(source = "collisionLevelFour.id", target = "collisionLevelFourId")

    @Mapping(source = "sourceAppointment.id", target = "sourceAppointmentId")

    @Mapping(source = "targetAppointment.id", target = "targetAppointmentId")
    CollisionLevelFiveDTO toDto(CollisionLevelFive collisionLevelFive); 

    @Mapping(source = "collisionLevelFourId", target = "collisionLevelFour")

    @Mapping(source = "sourceAppointmentId", target = "sourceAppointment")

    @Mapping(source = "targetAppointmentId", target = "targetAppointment")
    CollisionLevelFive toEntity(CollisionLevelFiveDTO collisionLevelFiveDTO); 
    default CollisionLevelFive fromId(Long id) {
        if (id == null) {
            return null;
        }
        CollisionLevelFive collisionLevelFive = new CollisionLevelFive();
        collisionLevelFive.setId(id);
        return collisionLevelFive;
    }
}
