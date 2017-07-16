package at.meroff.itproject.service;

import at.meroff.itproject.domain.CurriculumSemester;
import at.meroff.itproject.domain.enumeration.Semester;
import at.meroff.itproject.repository.CurriculumSemesterRepository;
import at.meroff.itproject.repository.search.CurriculumSemesterSearchRepository;
import at.meroff.itproject.service.dto.CurriculumSemesterDTO;
import at.meroff.itproject.service.mapper.CurriculumSemesterMapper;
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
 * Service Implementation for managing CurriculumSemester.
 */
@Service
@Transactional
public class CurriculumSemesterService {

    private final Logger log = LoggerFactory.getLogger(CurriculumSemesterService.class);

    private final CurriculumSemesterRepository curriculumSemesterRepository;

    private final CurriculumSemesterMapper curriculumSemesterMapper;

    private final CurriculumSemesterSearchRepository curriculumSemesterSearchRepository;

    public CurriculumSemesterService(CurriculumSemesterRepository curriculumSemesterRepository, CurriculumSemesterMapper curriculumSemesterMapper, CurriculumSemesterSearchRepository curriculumSemesterSearchRepository) {
        this.curriculumSemesterRepository = curriculumSemesterRepository;
        this.curriculumSemesterMapper = curriculumSemesterMapper;
        this.curriculumSemesterSearchRepository = curriculumSemesterSearchRepository;
    }

    /**
     * Save a curriculumSemester.
     *
     * @param curriculumSemesterDTO the entity to save
     * @return the persisted entity
     */
    public CurriculumSemesterDTO save(CurriculumSemesterDTO curriculumSemesterDTO) {
        log.debug("Request to save CurriculumSemester : {}", curriculumSemesterDTO);
        CurriculumSemester curriculumSemester = curriculumSemesterMapper.toEntity(curriculumSemesterDTO);
        curriculumSemester = curriculumSemesterRepository.save(curriculumSemester);
        CurriculumSemesterDTO result = curriculumSemesterMapper.toDto(curriculumSemester);
        curriculumSemesterSearchRepository.save(curriculumSemester);
        return result;
    }

    /**
     *  Get all the curriculumSemesters.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CurriculumSemesterDTO> findAll() {
        log.debug("Request to get all CurriculumSemesters");
        return curriculumSemesterRepository.findAll().stream()
            .map(curriculumSemesterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one curriculumSemester by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CurriculumSemesterDTO findOne(Long id) {
        log.debug("Request to get CurriculumSemester : {}", id);
        CurriculumSemester curriculumSemester = curriculumSemesterRepository.findOne(id);
        return curriculumSemesterMapper.toDto(curriculumSemester);
    }

    /**
     *  Get one curriculumSemester by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CurriculumSemesterDTO findOne(Integer curId, Integer year, Semester semester) {
        log.debug("Request to get CurriculumSemester : {}", curId);
        CurriculumSemester curriculumSemester = curriculumSemesterRepository.findByCurriculum_CurIdAndYearAndSemester(curId, year, semester);
        return curriculumSemesterMapper.toDto(curriculumSemester);
    }

    /**
     *  Delete the  curriculumSemester by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CurriculumSemester : {}", id);
        curriculumSemesterRepository.delete(id);
        curriculumSemesterSearchRepository.delete(id);
    }

    /**
     * Search for the curriculumSemester corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CurriculumSemesterDTO> search(String query) {
        log.debug("Request to search CurriculumSemesters for query {}", query);
        return StreamSupport
            .stream(curriculumSemesterSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(curriculumSemesterMapper::toDto)
            .collect(Collectors.toList());
    }
}
