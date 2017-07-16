package at.meroff.itproject.service;

import at.meroff.itproject.domain.CollisionLevelThree;
import at.meroff.itproject.repository.CollisionLevelThreeRepository;
import at.meroff.itproject.repository.search.CollisionLevelThreeSearchRepository;
import at.meroff.itproject.service.dto.CollisionLevelThreeDTO;
import at.meroff.itproject.service.mapper.CollisionLevelThreeMapper;
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
 * Service Implementation for managing CollisionLevelThree.
 */
@Service
@Transactional
public class CollisionLevelThreeService {

    private final Logger log = LoggerFactory.getLogger(CollisionLevelThreeService.class);

    private final CollisionLevelThreeRepository collisionLevelThreeRepository;

    private final CollisionLevelThreeMapper collisionLevelThreeMapper;

    private final CollisionLevelThreeSearchRepository collisionLevelThreeSearchRepository;

    public CollisionLevelThreeService(CollisionLevelThreeRepository collisionLevelThreeRepository, CollisionLevelThreeMapper collisionLevelThreeMapper, CollisionLevelThreeSearchRepository collisionLevelThreeSearchRepository) {
        this.collisionLevelThreeRepository = collisionLevelThreeRepository;
        this.collisionLevelThreeMapper = collisionLevelThreeMapper;
        this.collisionLevelThreeSearchRepository = collisionLevelThreeSearchRepository;
    }

    /**
     * Save a collisionLevelThree.
     *
     * @param collisionLevelThreeDTO the entity to save
     * @return the persisted entity
     */
    public CollisionLevelThreeDTO save(CollisionLevelThreeDTO collisionLevelThreeDTO) {
        log.debug("Request to save CollisionLevelThree : {}", collisionLevelThreeDTO);
        CollisionLevelThree collisionLevelThree = collisionLevelThreeMapper.toEntity(collisionLevelThreeDTO);
        collisionLevelThree = collisionLevelThreeRepository.save(collisionLevelThree);
        CollisionLevelThreeDTO result = collisionLevelThreeMapper.toDto(collisionLevelThree);
        collisionLevelThreeSearchRepository.save(collisionLevelThree);
        return result;
    }

    /**
     *  Get all the collisionLevelThrees.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CollisionLevelThreeDTO> findAll() {
        log.debug("Request to get all CollisionLevelThrees");
        return collisionLevelThreeRepository.findAll().stream()
            .map(collisionLevelThreeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one collisionLevelThree by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CollisionLevelThreeDTO findOne(Long id) {
        log.debug("Request to get CollisionLevelThree : {}", id);
        CollisionLevelThree collisionLevelThree = collisionLevelThreeRepository.findOne(id);
        return collisionLevelThreeMapper.toDto(collisionLevelThree);
    }

    /**
     *  Delete the  collisionLevelThree by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CollisionLevelThree : {}", id);
        collisionLevelThreeRepository.delete(id);
        collisionLevelThreeSearchRepository.delete(id);
    }

    /**
     * Search for the collisionLevelThree corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CollisionLevelThreeDTO> search(String query) {
        log.debug("Request to search CollisionLevelThrees for query {}", query);
        return StreamSupport
            .stream(collisionLevelThreeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(collisionLevelThreeMapper::toDto)
            .collect(Collectors.toList());
    }
}
