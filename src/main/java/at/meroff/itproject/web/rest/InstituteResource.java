package at.meroff.itproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.itproject.service.InstituteService;
import at.meroff.itproject.web.rest.util.HeaderUtil;
import at.meroff.itproject.service.dto.InstituteDTO;
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
 * REST controller for managing Institute.
 */
@RestController
@RequestMapping("/api")
public class InstituteResource {

    private final Logger log = LoggerFactory.getLogger(InstituteResource.class);

    private static final String ENTITY_NAME = "institute";

    private final InstituteService instituteService;

    public InstituteResource(InstituteService instituteService) {
        this.instituteService = instituteService;
    }

    /**
     * POST  /institutes : Create a new institute.
     *
     * @param instituteDTO the instituteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new instituteDTO, or with status 400 (Bad Request) if the institute has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/institutes")
    @Timed
    public ResponseEntity<InstituteDTO> createInstitute(@Valid @RequestBody InstituteDTO instituteDTO) throws URISyntaxException {
        log.debug("REST request to save Institute : {}", instituteDTO);
        if (instituteDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new institute cannot already have an ID")).body(null);
        }
        InstituteDTO result = instituteService.save(instituteDTO);
        return ResponseEntity.created(new URI("/api/institutes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /institutes : Updates an existing institute.
     *
     * @param instituteDTO the instituteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated instituteDTO,
     * or with status 400 (Bad Request) if the instituteDTO is not valid,
     * or with status 500 (Internal Server Error) if the instituteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/institutes")
    @Timed
    public ResponseEntity<InstituteDTO> updateInstitute(@Valid @RequestBody InstituteDTO instituteDTO) throws URISyntaxException {
        log.debug("REST request to update Institute : {}", instituteDTO);
        if (instituteDTO.getId() == null) {
            return createInstitute(instituteDTO);
        }
        InstituteDTO result = instituteService.save(instituteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, instituteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /institutes : get all the institutes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of institutes in body
     */
    @GetMapping("/institutes")
    @Timed
    public List<InstituteDTO> getAllInstitutes() {
        log.debug("REST request to get all Institutes");
        return instituteService.findAll();
    }

    /**
     * GET  /institutes/:id : get the "id" institute.
     *
     * @param id the id of the instituteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the instituteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/institutes/{id}")
    @Timed
    public ResponseEntity<InstituteDTO> getInstitute(@PathVariable Long id) {
        log.debug("REST request to get Institute : {}", id);
        InstituteDTO instituteDTO = instituteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(instituteDTO));
    }

    /**
     * DELETE  /institutes/:id : delete the "id" institute.
     *
     * @param id the id of the instituteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/institutes/{id}")
    @Timed
    public ResponseEntity<Void> deleteInstitute(@PathVariable Long id) {
        log.debug("REST request to delete Institute : {}", id);
        instituteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
