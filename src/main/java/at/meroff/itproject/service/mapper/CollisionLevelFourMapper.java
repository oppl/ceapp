package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.CollisionLevelFourDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CollisionLevelFour and its DTO CollisionLevelFourDTO.
 */
@Mapper(componentModel = "spring", uses = {CollisionLevelThreeMapper.class, AppointmentMapper.class, })
public interface CollisionLevelFourMapper extends EntityMapper <CollisionLevelFourDTO, CollisionLevelFour> {

    @Mapping(source = "collisionLevelThree.id", target = "collisionLevelThreeId")
    CollisionLevelFourDTO toDto(CollisionLevelFour collisionLevelFour); 

    @Mapping(source = "collisionLevelThreeId", target = "collisionLevelThree")
    CollisionLevelFour toEntity(CollisionLevelFourDTO collisionLevelFourDTO); 
    default CollisionLevelFour fromId(Long id) {
        if (id == null) {
            return null;
        }
        CollisionLevelFour collisionLevelFour = new CollisionLevelFour();
        collisionLevelFour.setId(id);
        return collisionLevelFour;
    }
}
