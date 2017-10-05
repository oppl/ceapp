package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.CurriculumDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Curriculum and its DTO CurriculumDTO.
 */
@Mapper(componentModel = "spring", uses = {InstituteMapper.class, CurriculumSemesterBaseMapper.class, IdealPlanMapper.class})
public interface CurriculumMapper extends EntityMapper <CurriculumDTO, Curriculum> {

    @Mapping(source = "curriculumSemesters", target = "curriculumSemesters")
    @Mapping(source = "idealPlans", target = "idealPlans")
    CurriculumDTO toDto(Curriculum curriculum);

    @Mapping(target = "curriculumSemesters", ignore = true)
    @Mapping(target = "idealPlans", ignore = true)
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
