package at.meroff.itproject.service;

import at.meroff.itproject.domain.Lva;
import at.meroff.itproject.repository.LvaRepository;
import at.meroff.itproject.repository.search.LvaSearchRepository;
import at.meroff.itproject.service.dto.LvaDTO;
import at.meroff.itproject.service.mapper.LvaMapper;
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
 * Service Implementation for managing Lva.
 */
@Service
@Transactional
public class LvaService {

    private final Logger log = LoggerFactory.getLogger(LvaService.class);

    private final LvaRepository lvaRepository;

    private final LvaMapper lvaMapper;

    private final LvaSearchRepository lvaSearchRepository;

    public LvaService(LvaRepository lvaRepository, LvaMapper lvaMapper, LvaSearchRepository lvaSearchRepository) {
        this.lvaRepository = lvaRepository;
        this.lvaMapper = lvaMapper;
        this.lvaSearchRepository = lvaSearchRepository;
    }

    /**
     * Save a lva.
     *
     * @param lvaDTO the entity to save
     * @return the persisted entity
     */
    public LvaDTO save(LvaDTO lvaDTO) {
        log.debug("Request to save Lva : {}", lvaDTO);
        Lva lva = lvaMapper.toEntity(lvaDTO);
        lva = lvaRepository.save(lva);
        LvaDTO result = lvaMapper.toDto(lva);
        lvaSearchRepository.save(lva);
        return result;
    }

    /**
     *  Get all the lvas.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<LvaDTO> findAll() {
        log.debug("Request to get all Lvas");
        return lvaRepository.findAll().stream()
            .map(lvaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one lva by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public LvaDTO findOne(Long id) {
        log.debug("Request to get Lva : {}", id);
        Lva lva = lvaRepository.findOne(id);
        return lvaMapper.toDto(lva);
    }

    /**
     *  Delete the  lva by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Lva : {}", id);
        lvaRepository.delete(id);
        lvaSearchRepository.delete(id);
    }

    /**
     * Search for the lva corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<LvaDTO> search(String query) {
        log.debug("Request to search Lvas for query {}", query);
        return StreamSupport
            .stream(lvaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(lvaMapper::toDto)
            .collect(Collectors.toList());
    }
}
