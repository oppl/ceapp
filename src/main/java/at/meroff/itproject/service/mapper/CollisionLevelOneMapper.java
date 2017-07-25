package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.CollisionLevelOneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CollisionLevelOne and its DTO CollisionLevelOneDTO.
 */
@Mapper(componentModel = "spring", uses = {CurriculumSubjectMapper.class, IdealPlanMapper.class, })
public interface CollisionLevelOneMapper extends EntityMapper <CollisionLevelOneDTO, CollisionLevelOne> {

    @Mapping(source = "curriculumSubject.id", target = "curriculumSubjectId")

    @Mapping(source = "idealPlan.id", target = "idealPlanId")
    @Mapping(source = "curriculumSubject", target = "curriculumSubject")
    CollisionLevelOneDTO toDto(CollisionLevelOne collisionLevelOne);

    @Mapping(source = "curriculumSubjectId", target = "curriculumSubject")
    @Mapping(target = "collisionLevelTwos", ignore = true)

    @Mapping(source = "idealPlanId", target = "idealPlan")
    CollisionLevelOne toEntity(CollisionLevelOneDTO collisionLevelOneDTO);
    default CollisionLevelOne fromId(Long id) {
        if (id == null) {
            return null;
        }
        CollisionLevelOne collisionLevelOne = new CollisionLevelOne();
        collisionLevelOne.setId(id);
        return collisionLevelOne;
    }
}
