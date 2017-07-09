package at.meroff.itproject.service;

import at.meroff.itproject.domain.IdealPlanEntries;
import at.meroff.itproject.repository.IdealPlanEntriesRepository;
import at.meroff.itproject.service.dto.IdealPlanEntriesDTO;
import at.meroff.itproject.service.mapper.IdealPlanEntriesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing IdealPlanEntries.
 */
@Service
@Transactional
public class IdealPlanEntriesService {

    private final Logger log = LoggerFactory.getLogger(IdealPlanEntriesService.class);

    private final IdealPlanEntriesRepository idealPlanEntriesRepository;

    private final IdealPlanEntriesMapper idealPlanEntriesMapper;

    public IdealPlanEntriesService(IdealPlanEntriesRepository idealPlanEntriesRepository, IdealPlanEntriesMapper idealPlanEntriesMapper) {
        this.idealPlanEntriesRepository = idealPlanEntriesRepository;
        this.idealPlanEntriesMapper = idealPlanEntriesMapper;
    }

    /**
     * Save a idealPlanEntries.
     *
     * @param idealPlanEntriesDTO the entity to save
     * @return the persisted entity
     */
    public IdealPlanEntriesDTO save(IdealPlanEntriesDTO idealPlanEntriesDTO) {
        log.debug("Request to save IdealPlanEntries : {}", idealPlanEntriesDTO);
        IdealPlanEntries idealPlanEntries = idealPlanEntriesMapper.toEntity(idealPlanEntriesDTO);
        idealPlanEntries = idealPlanEntriesRepository.save(idealPlanEntries);
        return idealPlanEntriesMapper.toDto(idealPlanEntries);
    }

    /**
     *  Get all the idealPlanEntries.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<IdealPlanEntriesDTO> findAll() {
        log.debug("Request to get all IdealPlanEntries");
        return idealPlanEntriesRepository.findAll().stream()
            .map(idealPlanEntriesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one idealPlanEntries by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public IdealPlanEntriesDTO findOne(Long id) {
        log.debug("Request to get IdealPlanEntries : {}", id);
        IdealPlanEntries idealPlanEntries = idealPlanEntriesRepository.findOne(id);
        return idealPlanEntriesMapper.toDto(idealPlanEntries);
    }

    /**
     *  Delete the  idealPlanEntries by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IdealPlanEntries : {}", id);
        idealPlanEntriesRepository.delete(id);
    }
}
