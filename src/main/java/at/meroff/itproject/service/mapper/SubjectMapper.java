package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.SubjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Subject and its DTO SubjectDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SubjectMapper extends EntityMapper <SubjectDTO, Subject> {
    
    @Mapping(target = "curriculumsubjects", ignore = true)
    @Mapping(target = "idealplanentries", ignore = true)
    @Mapping(target = "lvas", ignore = true)
    Subject toEntity(SubjectDTO subjectDTO); 
    default Subject fromId(Long id) {
        if (id == null) {
            return null;
        }
        Subject subject = new Subject();
        subject.setId(id);
        return subject;
    }
}
