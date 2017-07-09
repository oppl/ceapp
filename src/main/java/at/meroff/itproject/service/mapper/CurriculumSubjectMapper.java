package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.CurriculumSubjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CurriculumSubject and its DTO CurriculumSubjectDTO.
 */
@Mapper(componentModel = "spring", uses = {LvaMapper.class, CurriculumMapper.class, SubjectMapper.class, })
public interface CurriculumSubjectMapper extends EntityMapper <CurriculumSubjectDTO, CurriculumSubject> {

    @Mapping(source = "curriculum.id", target = "curriculumId")
    @Mapping(source = "curriculum.curName", target = "curriculumCurName")

    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "subject.subjectName", target = "subjectSubjectName")
    CurriculumSubjectDTO toDto(CurriculumSubject curriculumSubject); 
    @Mapping(target = "collCSSources", ignore = true)
    @Mapping(target = "collCSTargets", ignore = true)

    @Mapping(source = "curriculumId", target = "curriculum")

    @Mapping(source = "subjectId", target = "subject")
    CurriculumSubject toEntity(CurriculumSubjectDTO curriculumSubjectDTO); 
    default CurriculumSubject fromId(Long id) {
        if (id == null) {
            return null;
        }
        CurriculumSubject curriculumSubject = new CurriculumSubject();
        curriculumSubject.setId(id);
        return curriculumSubject;
    }
}
