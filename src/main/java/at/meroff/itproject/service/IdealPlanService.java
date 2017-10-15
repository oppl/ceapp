package at.meroff.itproject.service;

import at.meroff.itproject.domain.IdealPlan;
import at.meroff.itproject.domain.enumeration.Semester;
import at.meroff.itproject.repository.IdealPlanRepository;
import at.meroff.itproject.repository.search.IdealPlanSearchRepository;
import at.meroff.itproject.service.dto.IdealPlanDTO;
import at.meroff.itproject.service.mapper.IdealPlanMapper;
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
 * Service Implementation for managing IdealPlan.
 */
@Service
@Transactional
public class IdealPlanService {

    private final Logger log = LoggerFactory.getLogger(IdealPlanService.class);

    private final IdealPlanRepository idealPlanRepository;

    private final IdealPlanMapper idealPlanMapper;

    private final IdealPlanSearchRepository idealPlanSearchRepository;

    public IdealPlanService(IdealPlanRepository idealPlanRepository, IdealPlanMapper idealPlanMapper, IdealPlanSearchRepository idealPlanSearchRepository) {
        this.idealPlanRepository = idealPlanRepository;
        this.idealPlanMapper = idealPlanMapper;
        this.idealPlanSearchRepository = idealPlanSearchRepository;
    }

    /**
     * Save a idealPlan.
     *
     * @param idealPlanDTO the entity to save
     * @return the persisted entity
     */
    public IdealPlanDTO save(IdealPlanDTO idealPlanDTO) {
        log.debug("Request to save IdealPlan : {}", idealPlanDTO);
        IdealPlan idealPlan = idealPlanMapper.toEntity(idealPlanDTO);
        idealPlan = idealPlanRepository.save(idealPlan);
        IdealPlanDTO result = idealPlanMapper.toDto(idealPlan);
        idealPlanSearchRepository.save(idealPlan);
        return result;
    }

    /**
     *  Get all the idealPlans.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<IdealPlanDTO> findAll() {
        log.debug("Request to get all IdealPlans");
        return idealPlanRepository.findAll().stream()
            .map(idealPlanMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one idealPlan by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public IdealPlanDTO findOne(Long id) {
        log.debug("Request to get IdealPlan : {}", id);
        IdealPlan idealPlan = idealPlanRepository.findOne(id);
        return idealPlanMapper.toDto(idealPlan);
    }

    /**
     * Get an ideal plan by
     * @param curId curriculum db ID
     * @param year year of the ideal plan
     * @param semester semester of the ideal plan
     * @return the entity
     */
    @Transactional(readOnly = true)
    public IdealPlanDTO findByCurriculum_CurIdAndYearAndSemester(Integer curId, Integer year, Semester semester) {
        log.debug("Request to get IdealPlan : {}", curId);
        IdealPlan idealPlan = idealPlanRepository.findByCurriculum_CurIdAndYearAndSemester(curId, year, semester);
        return idealPlanMapper.toDto(idealPlan);
    }

    /**
     *  Delete the  idealPlan by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IdealPlan : {}", id);
        idealPlanRepository.delete(id);
        idealPlanSearchRepository.delete(id);
    }

    /**
     * Search for the idealPlan corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<IdealPlanDTO> search(String query) {
        log.debug("Request to search IdealPlans for query {}", query);
        return StreamSupport
            .stream(idealPlanSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(idealPlanMapper::toDto)
            .collect(Collectors.toList());
    }
}
