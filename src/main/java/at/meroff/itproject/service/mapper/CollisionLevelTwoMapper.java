package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.CollisionLevelTwoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CollisionLevelTwo and its DTO CollisionLevelTwoDTO.
 */
@Mapper(componentModel = "spring", uses = {CollisionLevelOneMapper.class, LvaMapper.class, })
public interface CollisionLevelTwoMapper extends EntityMapper <CollisionLevelTwoDTO, CollisionLevelTwo> {

    @Mapping(source = "collisionLevelOne.id", target = "collisionLevelOneId")

    @Mapping(source = "lva.id", target = "lvaId")
    @Mapping(source = "lva.lvaNr", target = "lvaLvaNr")
    // added mapping
    @Mapping(source = "lva", target = "lva")
    CollisionLevelTwoDTO toDto(CollisionLevelTwo collisionLevelTwo);

    @Mapping(source = "collisionLevelOneId", target = "collisionLevelOne")
    @Mapping(target = "collisionLevelThrees", ignore = true)

    @Mapping(source = "lvaId", target = "lva")
    CollisionLevelTwo toEntity(CollisionLevelTwoDTO collisionLevelTwoDTO);
    default CollisionLevelTwo fromId(Long id) {
        if (id == null) {
            return null;
        }
        CollisionLevelTwo collisionLevelTwo = new CollisionLevelTwo();
        collisionLevelTwo.setId(id);
        return collisionLevelTwo;
    }
}
