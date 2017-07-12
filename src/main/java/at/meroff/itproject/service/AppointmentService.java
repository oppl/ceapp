package at.meroff.itproject.service;

import at.meroff.itproject.domain.Appointment;
import at.meroff.itproject.repository.AppointmentRepository;
import at.meroff.itproject.repository.search.AppointmentSearchRepository;
import at.meroff.itproject.service.dto.AppointmentDTO;
import at.meroff.itproject.service.mapper.AppointmentMapper;
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
 * Service Implementation for managing Appointment.
 */
@Service
@Transactional
public class AppointmentService {

    private final Logger log = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;

    private final AppointmentMapper appointmentMapper;

    private final AppointmentSearchRepository appointmentSearchRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper, AppointmentSearchRepository appointmentSearchRepository) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.appointmentSearchRepository = appointmentSearchRepository;
    }

    /**
     * Save a appointment.
     *
     * @param appointmentDTO the entity to save
     * @return the persisted entity
     */
    public AppointmentDTO save(AppointmentDTO appointmentDTO) {
        log.debug("Request to save Appointment : {}", appointmentDTO);
        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);
        appointment = appointmentRepository.save(appointment);
        AppointmentDTO result = appointmentMapper.toDto(appointment);
        appointmentSearchRepository.save(appointment);
        return result;
    }

    /**
     *  Get all the appointments.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findAll() {
        log.debug("Request to get all Appointments");
        return appointmentRepository.findAll().stream()
            .map(appointmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one appointment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AppointmentDTO findOne(Long id) {
        log.debug("Request to get Appointment : {}", id);
        Appointment appointment = appointmentRepository.findOne(id);
        return appointmentMapper.toDto(appointment);
    }

    /**
     *  Delete the  appointment by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Appointment : {}", id);
        appointmentRepository.delete(id);
        appointmentSearchRepository.delete(id);
    }

    /**
     * Search for the appointment corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> search(String query) {
        log.debug("Request to search Appointments for query {}", query);
        return StreamSupport
            .stream(appointmentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(appointmentMapper::toDto)
            .collect(Collectors.toList());
    }
}
