package at.meroff.itproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.itproject.service.CurriculumService;
import at.meroff.itproject.web.rest.util.HeaderUtil;
import at.meroff.itproject.service.dto.CurriculumDTO;
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
 * REST controller for managing Curriculum.
 */
@RestController
@RequestMapping("/api")
public class CurriculumResource {

    private final Logger log = LoggerFactory.getLogger(CurriculumResource.class);

    private static final String ENTITY_NAME = "curriculum";

    private final CurriculumService curriculumService;

    public CurriculumResource(CurriculumService curriculumService) {
        this.curriculumService = curriculumService;
    }

    /**
     * POST  /curricula : Create a new curriculum.
     *
     * @param curriculumDTO the curriculumDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new curriculumDTO, or with status 400 (Bad Request) if the curriculum has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/curricula")
    @Timed
    public ResponseEntity<CurriculumDTO> createCurriculum(@Valid @RequestBody CurriculumDTO curriculumDTO) throws URISyntaxException {
        log.debug("REST request to save Curriculum : {}", curriculumDTO);
        if (curriculumDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new curriculum cannot already have an ID")).body(null);
        }
        CurriculumDTO result = curriculumService.save(curriculumDTO);
        return ResponseEntity.created(new URI("/api/curricula/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /curricula : Updates an existing curriculum.
     *
     * @param curriculumDTO the curriculumDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated curriculumDTO,
     * or with status 400 (Bad Request) if the curriculumDTO is not valid,
     * or with status 500 (Internal Server Error) if the curriculumDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/curricula")
    @Timed
    public ResponseEntity<CurriculumDTO> updateCurriculum(@Valid @RequestBody CurriculumDTO curriculumDTO) throws URISyntaxException {
        log.debug("REST request to update Curriculum : {}", curriculumDTO);
        if (curriculumDTO.getId() == null) {
            return createCurriculum(curriculumDTO);
        }
        CurriculumDTO result = curriculumService.save(curriculumDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, curriculumDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /curricula : get all the curricula.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of curricula in body
     */
    @GetMapping("/curricula")
    @Timed
    public List<CurriculumDTO> getAllCurricula() {
        log.debug("REST request to get all Curricula");
        return curriculumService.findAll();
    }

    /**
     * GET  /curricula/:id : get the "id" curriculum.
     *
     * @param id the id of the curriculumDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the curriculumDTO, or with status 404 (Not Found)
     */
    @GetMapping("/curricula/{id}")
    @Timed
    public ResponseEntity<CurriculumDTO> getCurriculum(@PathVariable Long id) {
        log.debug("REST request to get Curriculum : {}", id);
        CurriculumDTO curriculumDTO = curriculumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(curriculumDTO));
    }

    /**
     * DELETE  /curricula/:id : delete the "id" curriculum.
     *
     * @param id the id of the curriculumDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/curricula/{id}")
    @Timed
    public ResponseEntity<Void> deleteCurriculum(@PathVariable Long id) {
        log.debug("REST request to delete Curriculum : {}", id);
        curriculumService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/curricula?query=:query : search for the curriculum corresponding
     * to the query.
     *
     * @param query the query of the curriculum search
     * @return the result of the search
     */
    @GetMapping("/_search/curricula")
    @Timed
    public List<CurriculumDTO> searchCurricula(@RequestParam String query) {
        log.debug("REST request to search Curricula for query {}", query);
        return curriculumService.search(query);
    }

}
