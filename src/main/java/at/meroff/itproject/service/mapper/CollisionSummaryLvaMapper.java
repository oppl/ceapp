package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.CollisionSummaryLvaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CollisionSummaryLva and its DTO CollisionSummaryLvaDTO.
 */
@Mapper(componentModel = "spring", uses = {CollisionSummaryCSMapper.class, LvaMapper.class, })
public interface CollisionSummaryLvaMapper extends EntityMapper <CollisionSummaryLvaDTO, CollisionSummaryLva> {

    @Mapping(source = "collision.id", target = "collisionId")

    @Mapping(source = "l1.id", target = "l1Id")

    @Mapping(source = "l2.id", target = "l2Id")
    CollisionSummaryLvaDTO toDto(CollisionSummaryLva collisionSummaryLva); 

    @Mapping(source = "collisionId", target = "collision")

    @Mapping(source = "l1Id", target = "l1")

    @Mapping(source = "l2Id", target = "l2")
    CollisionSummaryLva toEntity(CollisionSummaryLvaDTO collisionSummaryLvaDTO); 
    default CollisionSummaryLva fromId(Long id) {
        if (id == null) {
            return null;
        }
        CollisionSummaryLva collisionSummaryLva = new CollisionSummaryLva();
        collisionSummaryLva.setId(id);
        return collisionSummaryLva;
    }
}
