package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.IdealPlanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity IdealPlan and its DTO IdealPlanDTO.
 */
@Mapper(componentModel = "spring", uses = {CurriculumMapper.class, })
public interface IdealPlanMapper extends EntityMapper <IdealPlanDTO, IdealPlan> {

    @Mapping(source = "curriculum.id", target = "curriculumId")
    @Mapping(source = "curriculum.curName", target = "curriculumCurName")
    IdealPlanDTO toDto(IdealPlan idealPlan); 
    @Mapping(target = "idealplanentries", ignore = true)
    @Mapping(target = "collisionSummaryCS", ignore = true)

    @Mapping(source = "curriculumId", target = "curriculum")
    IdealPlan toEntity(IdealPlanDTO idealPlanDTO); 
    default IdealPlan fromId(Long id) {
        if (id == null) {
            return null;
        }
        IdealPlan idealPlan = new IdealPlan();
        idealPlan.setId(id);
        return idealPlan;
    }
}
