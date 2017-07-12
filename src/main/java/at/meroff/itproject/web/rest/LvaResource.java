package at.meroff.itproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.itproject.service.LvaService;
import at.meroff.itproject.web.rest.util.HeaderUtil;
import at.meroff.itproject.service.dto.LvaDTO;
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
 * REST controller for managing Lva.
 */
@RestController
@RequestMapping("/api")
public class LvaResource {

    private final Logger log = LoggerFactory.getLogger(LvaResource.class);

    private static final String ENTITY_NAME = "lva";

    private final LvaService lvaService;

    public LvaResource(LvaService lvaService) {
        this.lvaService = lvaService;
    }

    /**
     * POST  /lvas : Create a new lva.
     *
     * @param lvaDTO the lvaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lvaDTO, or with status 400 (Bad Request) if the lva has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lvas")
    @Timed
    public ResponseEntity<LvaDTO> createLva(@Valid @RequestBody LvaDTO lvaDTO) throws URISyntaxException {
        log.debug("REST request to save Lva : {}", lvaDTO);
        if (lvaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new lva cannot already have an ID")).body(null);
        }
        LvaDTO result = lvaService.save(lvaDTO);
        return ResponseEntity.created(new URI("/api/lvas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lvas : Updates an existing lva.
     *
     * @param lvaDTO the lvaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lvaDTO,
     * or with status 400 (Bad Request) if the lvaDTO is not valid,
     * or with status 500 (Internal Server Error) if the lvaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lvas")
    @Timed
    public ResponseEntity<LvaDTO> updateLva(@Valid @RequestBody LvaDTO lvaDTO) throws URISyntaxException {
        log.debug("REST request to update Lva : {}", lvaDTO);
        if (lvaDTO.getId() == null) {
            return createLva(lvaDTO);
        }
        LvaDTO result = lvaService.save(lvaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lvaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lvas : get all the lvas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lvas in body
     */
    @GetMapping("/lvas")
    @Timed
    public List<LvaDTO> getAllLvas() {
        log.debug("REST request to get all Lvas");
        return lvaService.findAll();
    }

    /**
     * GET  /lvas/:id : get the "id" lva.
     *
     * @param id the id of the lvaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lvaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lvas/{id}")
    @Timed
    public ResponseEntity<LvaDTO> getLva(@PathVariable Long id) {
        log.debug("REST request to get Lva : {}", id);
        LvaDTO lvaDTO = lvaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lvaDTO));
    }

    /**
     * DELETE  /lvas/:id : delete the "id" lva.
     *
     * @param id the id of the lvaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lvas/{id}")
    @Timed
    public ResponseEntity<Void> deleteLva(@PathVariable Long id) {
        log.debug("REST request to delete Lva : {}", id);
        lvaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/lvas?query=:query : search for the lva corresponding
     * to the query.
     *
     * @param query the query of the lva search
     * @return the result of the search
     */
    @GetMapping("/_search/lvas")
    @Timed
    public List<LvaDTO> searchLvas(@RequestParam String query) {
        log.debug("REST request to search Lvas for query {}", query);
        return lvaService.search(query);
    }

}
