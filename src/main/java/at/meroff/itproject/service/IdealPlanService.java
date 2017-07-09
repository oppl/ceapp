package at.meroff.itproject.service;

import at.meroff.itproject.domain.IdealPlan;
import at.meroff.itproject.repository.IdealPlanRepository;
import at.meroff.itproject.service.dto.IdealPlanDTO;
import at.meroff.itproject.service.mapper.IdealPlanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing IdealPlan.
 */
@Service
@Transactional
public class IdealPlanService {

    private final Logger log = LoggerFactory.getLogger(IdealPlanService.class);

    private final IdealPlanRepository idealPlanRepository;

    private final IdealPlanMapper idealPlanMapper;

    public IdealPlanService(IdealPlanRepository idealPlanRepository, IdealPlanMapper idealPlanMapper) {
        this.idealPlanRepository = idealPlanRepository;
        this.idealPlanMapper = idealPlanMapper;
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
        return idealPlanMapper.toDto(idealPlan);
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
     *  Delete the  idealPlan by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IdealPlan : {}", id);
        idealPlanRepository.delete(id);
    }
}
