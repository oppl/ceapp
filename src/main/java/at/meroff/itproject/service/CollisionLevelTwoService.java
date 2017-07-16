package at.meroff.itproject.service;

import at.meroff.itproject.domain.CollisionLevelTwo;
import at.meroff.itproject.repository.CollisionLevelTwoRepository;
import at.meroff.itproject.repository.search.CollisionLevelTwoSearchRepository;
import at.meroff.itproject.service.dto.CollisionLevelTwoDTO;
import at.meroff.itproject.service.mapper.CollisionLevelTwoMapper;
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
 * Service Implementation for managing CollisionLevelTwo.
 */
@Service
@Transactional
public class CollisionLevelTwoService {

    private final Logger log = LoggerFactory.getLogger(CollisionLevelTwoService.class);

    private final CollisionLevelTwoRepository collisionLevelTwoRepository;

    private final CollisionLevelTwoMapper collisionLevelTwoMapper;

    private final CollisionLevelTwoSearchRepository collisionLevelTwoSearchRepository;

    public CollisionLevelTwoService(CollisionLevelTwoRepository collisionLevelTwoRepository, CollisionLevelTwoMapper collisionLevelTwoMapper, CollisionLevelTwoSearchRepository collisionLevelTwoSearchRepository) {
        this.collisionLevelTwoRepository = collisionLevelTwoRepository;
        this.collisionLevelTwoMapper = collisionLevelTwoMapper;
        this.collisionLevelTwoSearchRepository = collisionLevelTwoSearchRepository;
    }

    /**
     * Save a collisionLevelTwo.
     *
     * @param collisionLevelTwoDTO the entity to save
     * @return the persisted entity
     */
    public CollisionLevelTwoDTO save(CollisionLevelTwoDTO collisionLevelTwoDTO) {
        log.debug("Request to save CollisionLevelTwo : {}", collisionLevelTwoDTO);
        CollisionLevelTwo collisionLevelTwo = collisionLevelTwoMapper.toEntity(collisionLevelTwoDTO);
        collisionLevelTwo = collisionLevelTwoRepository.save(collisionLevelTwo);
        CollisionLevelTwoDTO result = collisionLevelTwoMapper.toDto(collisionLevelTwo);
        collisionLevelTwoSearchRepository.save(collisionLevelTwo);
        return result;
    }

    /**
     *  Get all the collisionLevelTwos.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CollisionLevelTwoDTO> findAll() {
        log.debug("Request to get all CollisionLevelTwos");
        return collisionLevelTwoRepository.findAll().stream()
            .map(collisionLevelTwoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one collisionLevelTwo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CollisionLevelTwoDTO findOne(Long id) {
        log.debug("Request to get CollisionLevelTwo : {}", id);
        CollisionLevelTwo collisionLevelTwo = collisionLevelTwoRepository.findOne(id);
        return collisionLevelTwoMapper.toDto(collisionLevelTwo);
    }

    /**
     *  Delete the  collisionLevelTwo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CollisionLevelTwo : {}", id);
        collisionLevelTwoRepository.delete(id);
        collisionLevelTwoSearchRepository.delete(id);
    }

    /**
     * Search for the collisionLevelTwo corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CollisionLevelTwoDTO> search(String query) {
        log.debug("Request to search CollisionLevelTwos for query {}", query);
        return StreamSupport
            .stream(collisionLevelTwoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(collisionLevelTwoMapper::toDto)
            .collect(Collectors.toList());
    }
}
