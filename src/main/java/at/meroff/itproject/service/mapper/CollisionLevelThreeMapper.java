package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.CollisionLevelThreeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CollisionLevelThree and its DTO CollisionLevelThreeDTO.
 */
@Mapper(componentModel = "spring", uses = {CollisionLevelTwoMapper.class, })
public interface CollisionLevelThreeMapper extends EntityMapper <CollisionLevelThreeDTO, CollisionLevelThree> {

    @Mapping(source = "collisionLevelTwo.id", target = "collisionLevelTwoId")
    CollisionLevelThreeDTO toDto(CollisionLevelThree collisionLevelThree); 

    @Mapping(source = "collisionLevelTwoId", target = "collisionLevelTwo")
    CollisionLevelThree toEntity(CollisionLevelThreeDTO collisionLevelThreeDTO); 
    default CollisionLevelThree fromId(Long id) {
        if (id == null) {
            return null;
        }
        CollisionLevelThree collisionLevelThree = new CollisionLevelThree();
        collisionLevelThree.setId(id);
        return collisionLevelThree;
    }
}
