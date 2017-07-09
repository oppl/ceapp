package at.meroff.itproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.itproject.service.IdealPlanEntriesService;
import at.meroff.itproject.web.rest.util.HeaderUtil;
import at.meroff.itproject.service.dto.IdealPlanEntriesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing IdealPlanEntries.
 */
@RestController
@RequestMapping("/api")
public class IdealPlanEntriesResource {

    private final Logger log = LoggerFactory.getLogger(IdealPlanEntriesResource.class);

    private static final String ENTITY_NAME = "idealPlanEntries";

    private final IdealPlanEntriesService idealPlanEntriesService;

    public IdealPlanEntriesResource(IdealPlanEntriesService idealPlanEntriesService) {
        this.idealPlanEntriesService = idealPlanEntriesService;
    }

    /**
     * POST  /ideal-plan-entries : Create a new idealPlanEntries.
     *
     * @param idealPlanEntriesDTO the idealPlanEntriesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new idealPlanEntriesDTO, or with status 400 (Bad Request) if the idealPlanEntries has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ideal-plan-entries")
    @Timed
    public ResponseEntity<IdealPlanEntriesDTO> createIdealPlanEntries(@Valid @RequestBody IdealPlanEntriesDTO idealPlanEntriesDTO) throws URISyntaxException {
        log.debug("REST request to save IdealPlanEntries : {}", idealPlanEntriesDTO);
        if (idealPlanEntriesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new idealPlanEntries cannot already have an ID")).body(null);
        }
        IdealPlanEntriesDTO result = idealPlanEntriesService.save(idealPlanEntriesDTO);
        return ResponseEntity.created(new URI("/api/ideal-plan-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ideal-plan-entries : Updates an existing idealPlanEntries.
     *
     * @param idealPlanEntriesDTO the idealPlanEntriesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated idealPlanEntriesDTO,
     * or with status 400 (Bad Request) if the idealPlanEntriesDTO is not valid,
     * or with status 500 (Internal Server Error) if the idealPlanEntriesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ideal-plan-entries")
    @Timed
    public ResponseEntity<IdealPlanEntriesDTO> updateIdealPlanEntries(@Valid @RequestBody IdealPlanEntriesDTO idealPlanEntriesDTO) throws URISyntaxException {
        log.debug("REST request to update IdealPlanEntries : {}", idealPlanEntriesDTO);
        if (idealPlanEntriesDTO.getId() == null) {
            return createIdealPlanEntries(idealPlanEntriesDTO);
        }
        IdealPlanEntriesDTO result = idealPlanEntriesService.save(idealPlanEntriesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, idealPlanEntriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ideal-plan-entries : get all the idealPlanEntries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of idealPlanEntries in body
     */
    @GetMapping("/ideal-plan-entries")
    @Timed
    public List<IdealPlanEntriesDTO> getAllIdealPlanEntries() {
        log.debug("REST request to get all IdealPlanEntries");
        return idealPlanEntriesService.findAll();
    }

    /**
     * GET  /ideal-plan-entries/:id : get the "id" idealPlanEntries.
     *
     * @param id the id of the idealPlanEntriesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the idealPlanEntriesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ideal-plan-entries/{id}")
    @Timed
    public ResponseEntity<IdealPlanEntriesDTO> getIdealPlanEntries(@PathVariable Long id) {
        log.debug("REST request to get IdealPlanEntries : {}", id);
        IdealPlanEntriesDTO idealPlanEntriesDTO = idealPlanEntriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(idealPlanEntriesDTO));
    }

    /**
     * DELETE  /ideal-plan-entries/:id : delete the "id" idealPlanEntries.
     *
     * @param id the id of the idealPlanEntriesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ideal-plan-entries/{id}")
    @Timed
    public ResponseEntity<Void> deleteIdealPlanEntries(@PathVariable Long id) {
        log.debug("REST request to delete IdealPlanEntries : {}", id);
        idealPlanEntriesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
