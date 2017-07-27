package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.CollisionLevelThreeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CollisionLevelThree and its DTO CollisionLevelThreeDTO.
 */
@Mapper(componentModel = "spring", uses = {CollisionLevelTwoMapper.class, CurriculumSubjectMapper.class, })
public interface CollisionLevelThreeMapper extends EntityMapper <CollisionLevelThreeDTO, CollisionLevelThree> {

    @Mapping(source = "collisionLevelTwo.id", target = "collisionLevelTwoId")

    @Mapping(source = "curriculumSubject.id", target = "curriculumSubjectId")
    @Mapping(source = "curriculumSubject", target = "curriculumSubject")
    CollisionLevelThreeDTO toDto(CollisionLevelThree collisionLevelThree);

    @Mapping(source = "collisionLevelTwoId", target = "collisionLevelTwo")
    @Mapping(target = "collisionLevelFours", ignore = true)

    @Mapping(source = "curriculumSubjectId", target = "curriculumSubject")
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
