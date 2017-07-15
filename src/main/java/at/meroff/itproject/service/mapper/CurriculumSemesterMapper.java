package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.CurriculumSemesterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CurriculumSemester and its DTO CurriculumSemesterDTO.
 */
@Mapper(componentModel = "spring", uses = {CurriculumMapper.class, CurriculumSubjectMapper.class})
public interface CurriculumSemesterMapper extends EntityMapper <CurriculumSemesterDTO, CurriculumSemester> {

    @Mapping(source = "curriculum.id", target = "curriculumId")
    @Mapping(source = "curriculum.curName", target = "curriculumCurName")
    @Mapping(source = "curriculum.curId", target = "curriculumCurId")
    @Mapping(target = "curriculumSubjects")
    CurriculumSemesterDTO toDto(CurriculumSemester curriculumSemester);
    @Mapping(target = "curriculumSubjects", ignore = true)

    @Mapping(source = "curriculumId", target = "curriculum")
    CurriculumSemester toEntity(CurriculumSemesterDTO curriculumSemesterDTO);
    default CurriculumSemester fromId(Long id) {
        if (id == null) {
            return null;
        }
        CurriculumSemester curriculumSemester = new CurriculumSemester();
        curriculumSemester.setId(id);
        return curriculumSemester;
    }
}
