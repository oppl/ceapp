package at.meroff.itproject.service;

import at.meroff.itproject.domain.Appointment;
import at.meroff.itproject.domain.IdealPlanEntries;
import at.meroff.itproject.domain.enumeration.Semester;
import at.meroff.itproject.repository.AppointmentRepository;
import at.meroff.itproject.repository.CurriculumSubjectRepository;
import at.meroff.itproject.repository.IdealPlanEntriesRepository;
import at.meroff.itproject.repository.search.AppointmentSearchRepository;
import at.meroff.itproject.service.dto.AppointmentDTO;
import at.meroff.itproject.service.dto.CurriculumSemesterDTO;
import at.meroff.itproject.service.mapper.AppointmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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

    private final IdealPlanEntriesRepository idealPlanEntriesRepository;

    private final CurriculumSubjectRepository curriculumSubjectRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              AppointmentMapper appointmentMapper,
                              AppointmentSearchRepository appointmentSearchRepository,
                              IdealPlanEntriesRepository idealPlanEntriesRepository,
                              CurriculumSubjectRepository curriculumSubjectRepository) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.appointmentSearchRepository = appointmentSearchRepository;
        this.idealPlanEntriesRepository = idealPlanEntriesRepository;
        this.curriculumSubjectRepository = curriculumSubjectRepository;
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
     *  Get all the appointments.
     *
     *  @return the list of entities
     * @param id
     * @param semester
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findBySomething(Long id, Integer semester) {
        log.debug("Request to get all Appointments");

        // bei ungerader Semesternummer und Wintersemester --> Start Wintersemester
        // bei gerader Semesternummer und Wintersemester --> Start Sommersemester
        return idealPlanEntriesRepository.findByIdealplan_Id(id)
            .stream()
            .filter(idealPlanEntries -> idealPlanEntries.getWinterSemesterDefault() == semester)
            .map(IdealPlanEntries::getSubject)
            .map(curriculumSubjectRepository::findBySubject)
            .flatMap(curriculumSubject -> curriculumSubject.getLvas().stream())
            .flatMap(lva -> lva.getAppointments().stream())
            .map(appointmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  Get all the appointments.
     *
     *  @return the list of entities
     * @param id
     * @param semester
     */
    @Transactional(readOnly = true)
    public List<AppointmentDTO> findIdealPlanIdSemesterCurriculumSemsterId(Long id, Integer semester, String curriculumSemesterType,  Long curriculumSemesterId) {
        log.debug("Request to get all Appointments");

        boolean searchWinter;

        if (curriculumSemesterType.equals("WS") && semester % 2 != 0) {
            searchWinter = true;
        } else {
            searchWinter = false;
        }

        // bei ungerader Semesternummer und Wintersemester --> Start Wintersemester
        // bei gerader Semesternummer und Wintersemester --> Start Sommersemester
        return idealPlanEntriesRepository.findByIdealplan_Id(id)
            .stream()
            //.filter(idealPlanEntries -> idealPlanEntries.getWinterSemesterDefault() == semester)
            .filter(idealPlanEntries -> {
                if (searchWinter) {
                    return idealPlanEntries.getWinterSemesterDefault() == semester;
                } else {
                    return idealPlanEntries.getSummerSemesterDefault() == semester;
                }
            })
            .map(IdealPlanEntries::getSubject)
            .peek(subject -> log.warn(subject.getSubjectName() + " " + subject.getSubjectType()))
            .map(subject -> curriculumSubjectRepository.findBySubjectAndCurriculumSemester_Id(subject, curriculumSemesterId))
            .filter(Objects::nonNull)
            .peek(curriculumSubject -> log.error(curriculumSubject.toString()))
            .flatMap(curriculumSubject -> curriculumSubject.getLvas().stream())
            .flatMap(lva -> lva.getAppointments().stream())
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
