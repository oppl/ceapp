package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.LvaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lva and its DTO LvaDTO.
 */
@Mapper(componentModel = "spring", uses = {SubjectMapper.class, })
public interface LvaMapper extends EntityMapper <LvaDTO, Lva> {

    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "subject.subjectName", target = "subjectSubjectName")
    LvaDTO toDto(Lva lva); 
    @Mapping(target = "appointments", ignore = true)
    @Mapping(target = "csl1S", ignore = true)
    @Mapping(target = "csl2S", ignore = true)

    @Mapping(source = "subjectId", target = "subject")
    @Mapping(target = "curriculumsubjects", ignore = true)
    Lva toEntity(LvaDTO lvaDTO); 
    default Lva fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lva lva = new Lva();
        lva.setId(id);
        return lva;
    }
}
