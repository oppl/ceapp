package at.meroff.itproject.service;

import at.meroff.itproject.domain.CollisionSummaryLva;
import at.meroff.itproject.repository.CollisionSummaryLvaRepository;
import at.meroff.itproject.repository.search.CollisionSummaryLvaSearchRepository;
import at.meroff.itproject.service.dto.CollisionSummaryLvaDTO;
import at.meroff.itproject.service.mapper.CollisionSummaryLvaMapper;
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
 * Service Implementation for managing CollisionSummaryLva.
 */
@Service
@Transactional
public class CollisionSummaryLvaService {

    private final Logger log = LoggerFactory.getLogger(CollisionSummaryLvaService.class);

    private final CollisionSummaryLvaRepository collisionSummaryLvaRepository;

    private final CollisionSummaryLvaMapper collisionSummaryLvaMapper;

    private final CollisionSummaryLvaSearchRepository collisionSummaryLvaSearchRepository;

    public CollisionSummaryLvaService(CollisionSummaryLvaRepository collisionSummaryLvaRepository, CollisionSummaryLvaMapper collisionSummaryLvaMapper, CollisionSummaryLvaSearchRepository collisionSummaryLvaSearchRepository) {
        this.collisionSummaryLvaRepository = collisionSummaryLvaRepository;
        this.collisionSummaryLvaMapper = collisionSummaryLvaMapper;
        this.collisionSummaryLvaSearchRepository = collisionSummaryLvaSearchRepository;
    }

    /**
     * Save a collisionSummaryLva.
     *
     * @param collisionSummaryLvaDTO the entity to save
     * @return the persisted entity
     */
    public CollisionSummaryLvaDTO save(CollisionSummaryLvaDTO collisionSummaryLvaDTO) {
        log.debug("Request to save CollisionSummaryLva : {}", collisionSummaryLvaDTO);
        CollisionSummaryLva collisionSummaryLva = collisionSummaryLvaMapper.toEntity(collisionSummaryLvaDTO);
        collisionSummaryLva = collisionSummaryLvaRepository.save(collisionSummaryLva);
        CollisionSummaryLvaDTO result = collisionSummaryLvaMapper.toDto(collisionSummaryLva);
        collisionSummaryLvaSearchRepository.save(collisionSummaryLva);
        return result;
    }

    /**
     *  Get all the collisionSummaryLvas.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CollisionSummaryLvaDTO> findAll() {
        log.debug("Request to get all CollisionSummaryLvas");
        return collisionSummaryLvaRepository.findAll().stream()
            .map(collisionSummaryLvaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one collisionSummaryLva by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CollisionSummaryLvaDTO findOne(Long id) {
        log.debug("Request to get CollisionSummaryLva : {}", id);
        CollisionSummaryLva collisionSummaryLva = collisionSummaryLvaRepository.findOne(id);
        return collisionSummaryLvaMapper.toDto(collisionSummaryLva);
    }

    /**
     *  Delete the  collisionSummaryLva by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CollisionSummaryLva : {}", id);
        collisionSummaryLvaRepository.delete(id);
        collisionSummaryLvaSearchRepository.delete(id);
    }

    /**
     * Search for the collisionSummaryLva corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CollisionSummaryLvaDTO> search(String query) {
        log.debug("Request to search CollisionSummaryLvas for query {}", query);
        return StreamSupport
            .stream(collisionSummaryLvaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(collisionSummaryLvaMapper::toDto)
            .collect(Collectors.toList());
    }
}
