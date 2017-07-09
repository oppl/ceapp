package at.meroff.itproject.service;

import at.meroff.itproject.domain.Institute;
import at.meroff.itproject.repository.InstituteRepository;
import at.meroff.itproject.service.dto.InstituteDTO;
import at.meroff.itproject.service.mapper.InstituteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Institute.
 */
@Service
@Transactional
public class InstituteService {

    private final Logger log = LoggerFactory.getLogger(InstituteService.class);

    private final InstituteRepository instituteRepository;

    private final InstituteMapper instituteMapper;

    public InstituteService(InstituteRepository instituteRepository, InstituteMapper instituteMapper) {
        this.instituteRepository = instituteRepository;
        this.instituteMapper = instituteMapper;
    }

    /**
     * Save a institute.
     *
     * @param instituteDTO the entity to save
     * @return the persisted entity
     */
    public InstituteDTO save(InstituteDTO instituteDTO) {
        log.debug("Request to save Institute : {}", instituteDTO);
        Institute institute = instituteMapper.toEntity(instituteDTO);
        institute = instituteRepository.save(institute);
        return instituteMapper.toDto(institute);
    }

    /**
     *  Get all the institutes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<InstituteDTO> findAll() {
        log.debug("Request to get all Institutes");
        return instituteRepository.findAll().stream()
            .map(instituteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one institute by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public InstituteDTO findOne(Long id) {
        log.debug("Request to get Institute : {}", id);
        Institute institute = instituteRepository.findOne(id);
        return instituteMapper.toDto(institute);
    }

    @Transactional(readOnly = true)
    public InstituteDTO findByInstituteId(Integer instituteId) {
        log.debug("Request to get Institute by instituteId : {}", instituteId);
        Institute institute = instituteRepository.findByInstituteId(instituteId);
        return instituteMapper.toDto(institute);
    }

    /**
     *  Delete the  institute by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Institute : {}", id);
        instituteRepository.delete(id);
    }
}
