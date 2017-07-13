package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.CurriculumDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Curriculum and its DTO CurriculumDTO.
 */
@Mapper(componentModel = "spring", uses = {InstituteMapper.class, })
public interface CurriculumMapper extends EntityMapper <CurriculumDTO, Curriculum> {
    
    @Mapping(target = "idealplanentries", ignore = true)
    @Mapping(target = "curriculumSemesters", ignore = true)
    Curriculum toEntity(CurriculumDTO curriculumDTO); 
    default Curriculum fromId(Long id) {
        if (id == null) {
            return null;
        }
        Curriculum curriculum = new Curriculum();
        curriculum.setId(id);
        return curriculum;
    }
}
