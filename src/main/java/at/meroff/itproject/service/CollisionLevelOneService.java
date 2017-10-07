package at.meroff.itproject.service;

import at.meroff.itproject.domain.CollisionLevelOne;
import at.meroff.itproject.repository.CollisionLevelOneRepository;
import at.meroff.itproject.repository.search.CollisionLevelOneSearchRepository;
import at.meroff.itproject.service.dto.CollisionLevelOneDTO;
import at.meroff.itproject.service.mapper.CollisionLevelOneMapper;
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
 * Service Implementation for managing CollisionLevelOne.
 */
@Service
@Transactional
public class CollisionLevelOneService {

    private final Logger log = LoggerFactory.getLogger(CollisionLevelOneService.class);

    private final CollisionLevelOneRepository collisionLevelOneRepository;

    private final CollisionLevelOneMapper collisionLevelOneMapper;

    private final CollisionLevelOneSearchRepository collisionLevelOneSearchRepository;

    public CollisionLevelOneService(CollisionLevelOneRepository collisionLevelOneRepository, CollisionLevelOneMapper collisionLevelOneMapper, CollisionLevelOneSearchRepository collisionLevelOneSearchRepository) {
        this.collisionLevelOneRepository = collisionLevelOneRepository;
        this.collisionLevelOneMapper = collisionLevelOneMapper;
        this.collisionLevelOneSearchRepository = collisionLevelOneSearchRepository;
    }

    /**
     * Save a collisionLevelOne.
     *
     * @param collisionLevelOneDTO the entity to save
     * @return the persisted entity
     */
    public CollisionLevelOneDTO save(CollisionLevelOneDTO collisionLevelOneDTO) {
        log.debug("Request to save CollisionLevelOne : {}", collisionLevelOneDTO);
        CollisionLevelOne collisionLevelOne = collisionLevelOneMapper.toEntity(collisionLevelOneDTO);
        collisionLevelOne = collisionLevelOneRepository.save(collisionLevelOne);
        CollisionLevelOneDTO result = collisionLevelOneMapper.toDto(collisionLevelOne);
        collisionLevelOneSearchRepository.save(collisionLevelOne);
        return result;
    }

    /**
     *  Get all the collisionLevelOnes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CollisionLevelOneDTO> findAll() {
        log.debug("Request to get all CollisionLevelOnes");
        return collisionLevelOneRepository.findAll().stream()
            .map(collisionLevelOneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one collisionLevelOne by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CollisionLevelOneDTO findOne(Long id) {
        log.debug("Request to get CollisionLevelOne : {}", id);
        CollisionLevelOne collisionLevelOne = collisionLevelOneRepository.findOne(id);
        return collisionLevelOneMapper.toDto(collisionLevelOne);
    }

    /**
     *  Delete the  collisionLevelOne by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CollisionLevelOne : {}", id);
        collisionLevelOneRepository.delete(id);
        collisionLevelOneSearchRepository.delete(id);
    }

    /**
     * Search for the collisionLevelOne corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CollisionLevelOneDTO> search(String query) {
        log.debug("Request to search CollisionLevelOnes for query {}", query);
        return StreamSupport
            .stream(collisionLevelOneSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(collisionLevelOneMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CollisionLevelOneDTO> findByCsIp(Long cs, Long ip) {
        log.debug("Request to get all CollisionLevelOnes");
        LinkedList<CollisionLevelOneDTO> collect = collisionLevelOneRepository.findByCsIp(cs, ip).stream()
            .map(collisionLevelOneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return collect;
    }
}
