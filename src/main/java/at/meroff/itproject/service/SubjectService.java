package at.meroff.itproject.service;

import at.meroff.itproject.domain.Subject;
import at.meroff.itproject.domain.enumeration.SubjectType;
import at.meroff.itproject.repository.SubjectRepository;
import at.meroff.itproject.repository.search.SubjectSearchRepository;
import at.meroff.itproject.service.dto.SubjectDTO;
import at.meroff.itproject.service.mapper.SubjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Subject.
 */
@Service
@Transactional
public class SubjectService {

    private final Logger log = LoggerFactory.getLogger(SubjectService.class);

    private final SubjectRepository subjectRepository;

    private final SubjectMapper subjectMapper;

    private final SubjectSearchRepository subjectSearchRepository;

    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper, SubjectSearchRepository subjectSearchRepository) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
        this.subjectSearchRepository = subjectSearchRepository;
    }

    /**
     * Save a subject.
     *
     * @param subjectDTO the entity to save
     * @return the persisted entity
     */
    public SubjectDTO save(SubjectDTO subjectDTO) {
        log.debug("Request to save Subject : {}", subjectDTO);
        Subject subject = subjectMapper.toEntity(subjectDTO);
        subject = subjectRepository.save(subject);
        SubjectDTO result = subjectMapper.toDto(subject);
        subjectSearchRepository.save(subject);
        return result;
    }

    /**
     *  Get all the subjects.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SubjectDTO> findAll() {
        log.debug("Request to get all Subjects");
        return subjectRepository.findAll().stream()
            .map(subjectMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one subject by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public SubjectDTO findOne(Long id) {
        log.debug("Request to get Subject : {}", id);
        Subject subject = subjectRepository.findOne(id);
        return subjectMapper.toDto(subject);
    }

    /**
     *  Get one subject by name and type.
     *
     *  @param subjectName the name of the entity
     *  @param subjectType the subject type of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public SubjectDTO findBySubjectNameAndSubjectType(String subjectName, SubjectType subjectType) {
        log.debug("Request to get Subject ; {}", subjectName + " " + subjectType.name());
        Subject subject = subjectRepository.findBySubjectNameAndSubjectType(subjectName, subjectType);
        return subjectMapper.toDto(subject);
    }

    /**
     *  Delete the  subject by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Subject : {}", id);
        subjectRepository.delete(id);
        subjectSearchRepository.delete(id);
    }

    /**
     * Search for the subject corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SubjectDTO> search(String query) {
        log.debug("Request to search Subjects for query {}", query);
        return StreamSupport
            .stream(subjectSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(subjectMapper::toDto)
            .collect(Collectors.toList());
    }
}
