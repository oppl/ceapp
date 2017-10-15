package at.meroff.itproject.service.mapper;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.service.dto.CurriculumSubjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CurriculumSubject and its DTO CurriculumSubjectDTO.
 */
@Mapper(componentModel = "spring", uses = {LvaMapper.class, SubjectMapper.class, CurriculumSemesterMapper.class, })
public interface CurriculumSubjectMapper extends EntityMapper <CurriculumSubjectDTO, CurriculumSubject> {

    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "subject.subjectName", target = "subjectSubjectName")
    // added mapping
    @Mapping(source = "subject.subjectType", target = "subjectSubjectType")

    @Mapping(source = "curriculumSemester.id", target = "curriculumSemesterId")
    CurriculumSubjectDTO toDto(CurriculumSubject curriculumSubject);

    @Mapping(source = "subjectId", target = "subject")

    @Mapping(source = "curriculumSemesterId", target = "curriculumSemester")
    @Mapping(target = "collisionLevelOnes", ignore = true)
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
