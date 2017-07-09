package at.meroff.itproject.service;

import at.meroff.itproject.domain.CollisionSummaryCS;
import at.meroff.itproject.repository.CollisionSummaryCSRepository;
import at.meroff.itproject.service.dto.CollisionSummaryCSDTO;
import at.meroff.itproject.service.mapper.CollisionSummaryCSMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CollisionSummaryCS.
 */
@Service
@Transactional
public class CollisionSummaryCSService {

    private final Logger log = LoggerFactory.getLogger(CollisionSummaryCSService.class);

    private final CollisionSummaryCSRepository collisionSummaryCSRepository;

    private final CollisionSummaryCSMapper collisionSummaryCSMapper;

    public CollisionSummaryCSService(CollisionSummaryCSRepository collisionSummaryCSRepository, CollisionSummaryCSMapper collisionSummaryCSMapper) {
        this.collisionSummaryCSRepository = collisionSummaryCSRepository;
        this.collisionSummaryCSMapper = collisionSummaryCSMapper;
    }

    /**
     * Save a collisionSummaryCS.
     *
     * @param collisionSummaryCSDTO the entity to save
     * @return the persisted entity
     */
    public CollisionSummaryCSDTO save(CollisionSummaryCSDTO collisionSummaryCSDTO) {
        log.debug("Request to save CollisionSummaryCS : {}", collisionSummaryCSDTO);
        CollisionSummaryCS collisionSummaryCS = collisionSummaryCSMapper.toEntity(collisionSummaryCSDTO);
        collisionSummaryCS = collisionSummaryCSRepository.save(collisionSummaryCS);
        return collisionSummaryCSMapper.toDto(collisionSummaryCS);
    }

    /**
     *  Get all the collisionSummaryCS.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CollisionSummaryCSDTO> findAll() {
        log.debug("Request to get all CollisionSummaryCS");
        return collisionSummaryCSRepository.findAll().stream()
            .map(collisionSummaryCSMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one collisionSummaryCS by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CollisionSummaryCSDTO findOne(Long id) {
        log.debug("Request to get CollisionSummaryCS : {}", id);
        CollisionSummaryCS collisionSummaryCS = collisionSummaryCSRepository.findOne(id);
        return collisionSummaryCSMapper.toDto(collisionSummaryCS);
    }

    /**
     *  Delete the  collisionSummaryCS by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CollisionSummaryCS : {}", id);
        collisionSummaryCSRepository.delete(id);
    }
}
