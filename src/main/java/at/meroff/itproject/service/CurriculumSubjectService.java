package at.meroff.itproject.service;

import at.meroff.itproject.domain.CurriculumSubject;
import at.meroff.itproject.repository.CurriculumSubjectRepository;
import at.meroff.itproject.service.dto.CurriculumSubjectDTO;
import at.meroff.itproject.service.mapper.CurriculumSubjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CurriculumSubject.
 */
@Service
@Transactional
public class CurriculumSubjectService {

    private final Logger log = LoggerFactory.getLogger(CurriculumSubjectService.class);

    private final CurriculumSubjectRepository curriculumSubjectRepository;

    private final CurriculumSubjectMapper curriculumSubjectMapper;

    public CurriculumSubjectService(CurriculumSubjectRepository curriculumSubjectRepository, CurriculumSubjectMapper curriculumSubjectMapper) {
        this.curriculumSubjectRepository = curriculumSubjectRepository;
        this.curriculumSubjectMapper = curriculumSubjectMapper;
    }

    /**
     * Save a curriculumSubject.
     *
     * @param curriculumSubjectDTO the entity to save
     * @return the persisted entity
     */
    public CurriculumSubjectDTO save(CurriculumSubjectDTO curriculumSubjectDTO) {
        log.debug("Request to save CurriculumSubject : {}", curriculumSubjectDTO);
        CurriculumSubject curriculumSubject = curriculumSubjectMapper.toEntity(curriculumSubjectDTO);
        curriculumSubject = curriculumSubjectRepository.save(curriculumSubject);
        return curriculumSubjectMapper.toDto(curriculumSubject);
    }

    /**
     *  Get all the curriculumSubjects.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CurriculumSubjectDTO> findAll() {
        log.debug("Request to get all CurriculumSubjects");
        return curriculumSubjectRepository.findAllWithEagerRelationships().stream()
            .map(curriculumSubjectMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one curriculumSubject by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CurriculumSubjectDTO findOne(Long id) {
        log.debug("Request to get CurriculumSubject : {}", id);
        CurriculumSubject curriculumSubject = curriculumSubjectRepository.findOneWithEagerRelationships(id);
        return curriculumSubjectMapper.toDto(curriculumSubject);
    }

    /**
     *  Delete the  curriculumSubject by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CurriculumSubject : {}", id);
        curriculumSubjectRepository.delete(id);
    }
}
