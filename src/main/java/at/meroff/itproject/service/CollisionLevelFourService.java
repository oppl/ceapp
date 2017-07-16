package at.meroff.itproject.service;

import at.meroff.itproject.domain.CollisionLevelFour;
import at.meroff.itproject.repository.CollisionLevelFourRepository;
import at.meroff.itproject.repository.search.CollisionLevelFourSearchRepository;
import at.meroff.itproject.service.dto.CollisionLevelFourDTO;
import at.meroff.itproject.service.mapper.CollisionLevelFourMapper;
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
 * Service Implementation for managing CollisionLevelFour.
 */
@Service
@Transactional
public class CollisionLevelFourService {

    private final Logger log = LoggerFactory.getLogger(CollisionLevelFourService.class);

    private final CollisionLevelFourRepository collisionLevelFourRepository;

    private final CollisionLevelFourMapper collisionLevelFourMapper;

    private final CollisionLevelFourSearchRepository collisionLevelFourSearchRepository;

    public CollisionLevelFourService(CollisionLevelFourRepository collisionLevelFourRepository, CollisionLevelFourMapper collisionLevelFourMapper, CollisionLevelFourSearchRepository collisionLevelFourSearchRepository) {
        this.collisionLevelFourRepository = collisionLevelFourRepository;
        this.collisionLevelFourMapper = collisionLevelFourMapper;
        this.collisionLevelFourSearchRepository = collisionLevelFourSearchRepository;
    }

    /**
     * Save a collisionLevelFour.
     *
     * @param collisionLevelFourDTO the entity to save
     * @return the persisted entity
     */
    public CollisionLevelFourDTO save(CollisionLevelFourDTO collisionLevelFourDTO) {
        log.debug("Request to save CollisionLevelFour : {}", collisionLevelFourDTO);
        CollisionLevelFour collisionLevelFour = collisionLevelFourMapper.toEntity(collisionLevelFourDTO);
        collisionLevelFour = collisionLevelFourRepository.save(collisionLevelFour);
        CollisionLevelFourDTO result = collisionLevelFourMapper.toDto(collisionLevelFour);
        collisionLevelFourSearchRepository.save(collisionLevelFour);
        return result;
    }

    /**
     *  Get all the collisionLevelFours.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CollisionLevelFourDTO> findAll() {
        log.debug("Request to get all CollisionLevelFours");
        return collisionLevelFourRepository.findAllWithEagerRelationships().stream()
            .map(collisionLevelFourMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one collisionLevelFour by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CollisionLevelFourDTO findOne(Long id) {
        log.debug("Request to get CollisionLevelFour : {}", id);
        CollisionLevelFour collisionLevelFour = collisionLevelFourRepository.findOneWithEagerRelationships(id);
        return collisionLevelFourMapper.toDto(collisionLevelFour);
    }

    /**
     *  Delete the  collisionLevelFour by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CollisionLevelFour : {}", id);
        collisionLevelFourRepository.delete(id);
        collisionLevelFourSearchRepository.delete(id);
    }

    /**
     * Search for the collisionLevelFour corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CollisionLevelFourDTO> search(String query) {
        log.debug("Request to search CollisionLevelFours for query {}", query);
        return StreamSupport
            .stream(collisionLevelFourSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(collisionLevelFourMapper::toDto)
            .collect(Collectors.toList());
    }
}
