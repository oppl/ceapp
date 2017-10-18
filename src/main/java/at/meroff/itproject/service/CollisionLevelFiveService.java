package at.meroff.itproject.service;

import at.meroff.itproject.domain.CollisionLevelFive;
import at.meroff.itproject.repository.CollisionLevelFiveRepository;
import at.meroff.itproject.repository.search.CollisionLevelFiveSearchRepository;
import at.meroff.itproject.service.dto.CollisionLevelFiveDTO;
import at.meroff.itproject.service.mapper.CollisionLevelFiveMapper;
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
 * Service Implementation for managing CollisionLevelFive.
 */
@Service
@Transactional
public class CollisionLevelFiveService {

    private final Logger log = LoggerFactory.getLogger(CollisionLevelFiveService.class);

    private final CollisionLevelFiveRepository collisionLevelFiveRepository;

    private final CollisionLevelFiveMapper collisionLevelFiveMapper;

    private final CollisionLevelFiveSearchRepository collisionLevelFiveSearchRepository;

    public CollisionLevelFiveService(CollisionLevelFiveRepository collisionLevelFiveRepository, CollisionLevelFiveMapper collisionLevelFiveMapper, CollisionLevelFiveSearchRepository collisionLevelFiveSearchRepository) {
        this.collisionLevelFiveRepository = collisionLevelFiveRepository;
        this.collisionLevelFiveMapper = collisionLevelFiveMapper;
        this.collisionLevelFiveSearchRepository = collisionLevelFiveSearchRepository;
    }

    /**
     * Save a collisionLevelFive.
     *
     * @param collisionLevelFiveDTO the entity to save
     * @return the persisted entity
     */
    public CollisionLevelFiveDTO save(CollisionLevelFiveDTO collisionLevelFiveDTO) {
        log.debug("Request to save CollisionLevelFive : {}", collisionLevelFiveDTO);
        CollisionLevelFive collisionLevelFive = collisionLevelFiveMapper.toEntity(collisionLevelFiveDTO);
        collisionLevelFive = collisionLevelFiveRepository.save(collisionLevelFive);
        CollisionLevelFiveDTO result = collisionLevelFiveMapper.toDto(collisionLevelFive);
        collisionLevelFiveSearchRepository.save(collisionLevelFive);
        return result;
    }

    /**
     *  Get all the collisionLevelFives.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CollisionLevelFiveDTO> findAll() {
        log.debug("Request to get all CollisionLevelFives");
        return collisionLevelFiveRepository.findAll().stream()
            .map(collisionLevelFiveMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the collisionLevelFives.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CollisionLevelFiveDTO> findByCollisionLevelFour_Id(Long id) {
        log.debug("Request to get all CollisionLevelFours");
        LinkedList<CollisionLevelFiveDTO> collect = collisionLevelFiveRepository.findByCollisionLevelFour_Id(id).stream()
            .map(collisionLevelFiveMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return collect;
    }

    /**
     *  Get one collisionLevelFive by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CollisionLevelFiveDTO findOne(Long id) {
        log.debug("Request to get CollisionLevelFive : {}", id);
        CollisionLevelFive collisionLevelFive = collisionLevelFiveRepository.findOne(id);
        return collisionLevelFiveMapper.toDto(collisionLevelFive);
    }

    /**
     *  Delete the  collisionLevelFive by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CollisionLevelFive : {}", id);
        collisionLevelFiveRepository.delete(id);
        collisionLevelFiveSearchRepository.delete(id);
    }

    /**
     * Search for the collisionLevelFive corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CollisionLevelFiveDTO> search(String query) {
        log.debug("Request to search CollisionLevelFives for query {}", query);
        return StreamSupport
            .stream(collisionLevelFiveSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(collisionLevelFiveMapper::toDto)
            .collect(Collectors.toList());
    }
}
