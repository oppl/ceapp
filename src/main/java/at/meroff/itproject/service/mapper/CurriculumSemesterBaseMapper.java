package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.CurriculumSemester;
import at.meroff.itproject.service.dto.CurriculumSemesterBaseDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity CurriculumSemester and its DTO CurriculumSemesterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CurriculumSemesterBaseMapper extends EntityMapper <CurriculumSemesterBaseDTO, CurriculumSemester> {

    CurriculumSemesterBaseDTO toDto(CurriculumSemester curriculumSemester);
}
