package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.IdealPlanEntriesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity IdealPlanEntries and its DTO IdealPlanEntriesDTO.
 */
@Mapper(componentModel = "spring", uses = {SubjectMapper.class, IdealPlanMapper.class, })
public interface IdealPlanEntriesMapper extends EntityMapper <IdealPlanEntriesDTO, IdealPlanEntries> {

    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "subject.subjectName", target = "subjectSubjectName")
    // added mapping
    @Mapping(source = "subject.subjectType", target = "subjectSubjectType")

    @Mapping(source = "idealplan.id", target = "idealplanId")
    IdealPlanEntriesDTO toDto(IdealPlanEntries idealPlanEntries);

    @Mapping(source = "subjectId", target = "subject")

    @Mapping(source = "idealplanId", target = "idealplan")
    IdealPlanEntries toEntity(IdealPlanEntriesDTO idealPlanEntriesDTO);
    default IdealPlanEntries fromId(Long id) {
        if (id == null) {
            return null;
        }
        IdealPlanEntries idealPlanEntries = new IdealPlanEntries();
        idealPlanEntries.setId(id);
        return idealPlanEntries;
    }
}
