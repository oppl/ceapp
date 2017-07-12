package at.meroff.itproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.itproject.service.IdealPlanService;
import at.meroff.itproject.web.rest.util.HeaderUtil;
import at.meroff.itproject.service.dto.IdealPlanDTO;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing IdealPlan.
 */
@RestController
@RequestMapping("/api")
public class IdealPlanResource {

    private final Logger log = LoggerFactory.getLogger(IdealPlanResource.class);

    private static final String ENTITY_NAME = "idealPlan";

    private final IdealPlanService idealPlanService;

    public IdealPlanResource(IdealPlanService idealPlanService) {
        this.idealPlanService = idealPlanService;
    }

    /**
     * POST  /ideal-plans : Create a new idealPlan.
     *
     * @param idealPlanDTO the idealPlanDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new idealPlanDTO, or with status 400 (Bad Request) if the idealPlan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ideal-plans")
    @Timed
    public ResponseEntity<IdealPlanDTO> createIdealPlan(@Valid @RequestBody IdealPlanDTO idealPlanDTO) throws URISyntaxException {
        log.debug("REST request to save IdealPlan : {}", idealPlanDTO);
        if (idealPlanDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new idealPlan cannot already have an ID")).body(null);
        }
        IdealPlanDTO result = idealPlanService.save(idealPlanDTO);
        return ResponseEntity.created(new URI("/api/ideal-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ideal-plans : Updates an existing idealPlan.
     *
     * @param idealPlanDTO the idealPlanDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated idealPlanDTO,
     * or with status 400 (Bad Request) if the idealPlanDTO is not valid,
     * or with status 500 (Internal Server Error) if the idealPlanDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ideal-plans")
    @Timed
    public ResponseEntity<IdealPlanDTO> updateIdealPlan(@Valid @RequestBody IdealPlanDTO idealPlanDTO) throws URISyntaxException {
        log.debug("REST request to update IdealPlan : {}", idealPlanDTO);
        if (idealPlanDTO.getId() == null) {
            return createIdealPlan(idealPlanDTO);
        }
        IdealPlanDTO result = idealPlanService.save(idealPlanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, idealPlanDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ideal-plans : get all the idealPlans.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of idealPlans in body
     */
    @GetMapping("/ideal-plans")
    @Timed
    public List<IdealPlanDTO> getAllIdealPlans() {
        log.debug("REST request to get all IdealPlans");
        return idealPlanService.findAll();
    }

    /**
     * GET  /ideal-plans/:id : get the "id" idealPlan.
     *
     * @param id the id of the idealPlanDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the idealPlanDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ideal-plans/{id}")
    @Timed
    public ResponseEntity<IdealPlanDTO> getIdealPlan(@PathVariable Long id) {
        log.debug("REST request to get IdealPlan : {}", id);
        IdealPlanDTO idealPlanDTO = idealPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(idealPlanDTO));
    }

    /**
     * DELETE  /ideal-plans/:id : delete the "id" idealPlan.
     *
     * @param id the id of the idealPlanDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ideal-plans/{id}")
    @Timed
    public ResponseEntity<Void> deleteIdealPlan(@PathVariable Long id) {
        log.debug("REST request to delete IdealPlan : {}", id);
        idealPlanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ideal-plans?query=:query : search for the idealPlan corresponding
     * to the query.
     *
     * @param query the query of the idealPlan search
     * @return the result of the search
     */
    @GetMapping("/_search/ideal-plans")
    @Timed
    public List<IdealPlanDTO> searchIdealPlans(@RequestParam String query) {
        log.debug("REST request to search IdealPlans for query {}", query);
        return idealPlanService.search(query);
    }

}
