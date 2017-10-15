package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.CollisionLevelFourDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CollisionLevelFour and its DTO CollisionLevelFourDTO.
 */
@Mapper(componentModel = "spring", uses = {CollisionLevelThreeMapper.class, LvaMapper.class, })
public interface CollisionLevelFourMapper extends EntityMapper <CollisionLevelFourDTO, CollisionLevelFour> {

    @Mapping(source = "collisionLevelThree.id", target = "collisionLevelThreeId")

    @Mapping(source = "lva.id", target = "lvaId")
    @Mapping(source = "lva.lvaNr", target = "lvaLvaNr")
    // added mapping
    @Mapping(source = "lva", target = "lva")
    CollisionLevelFourDTO toDto(CollisionLevelFour collisionLevelFour);

    @Mapping(source = "collisionLevelThreeId", target = "collisionLevelThree")
    @Mapping(target = "collisionLevelFives", ignore = true)

    @Mapping(source = "lvaId", target = "lva")
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
