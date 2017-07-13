package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.CollisionSummaryCSDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CollisionSummaryCS and its DTO CollisionSummaryCSDTO.
 */
@Mapper(componentModel = "spring", uses = {CurriculumSubjectMapper.class, IdealPlanMapper.class, })
public interface CollisionSummaryCSMapper extends EntityMapper <CollisionSummaryCSDTO, CollisionSummaryCS> {

    @Mapping(source = "csSource.id", target = "csSourceId")

    @Mapping(source = "csTarget.id", target = "csTargetId")

    @Mapping(source = "idealPlan.id", target = "idealPlanId")
    CollisionSummaryCSDTO toDto(CollisionSummaryCS collisionSummaryCS); 
    @Mapping(target = "collisionsummarylvas", ignore = true)

    @Mapping(source = "csSourceId", target = "csSource")

    @Mapping(source = "csTargetId", target = "csTarget")

    @Mapping(source = "idealPlanId", target = "idealPlan")
    CollisionSummaryCS toEntity(CollisionSummaryCSDTO collisionSummaryCSDTO); 
    default CollisionSummaryCS fromId(Long id) {
        if (id == null) {
            return null;
        }
        CollisionSummaryCS collisionSummaryCS = new CollisionSummaryCS();
        collisionSummaryCS.setId(id);
        return collisionSummaryCS;
    }
}
