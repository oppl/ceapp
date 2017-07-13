package at.meroff.itproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.itproject.service.CurriculumSemesterService;
import at.meroff.itproject.web.rest.util.HeaderUtil;
import at.meroff.itproject.service.dto.CurriculumSemesterDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CurriculumSemester.
 */
@RestController
@RequestMapping("/api")
public class CurriculumSemesterResource {

    private final Logger log = LoggerFactory.getLogger(CurriculumSemesterResource.class);

    private static final String ENTITY_NAME = "curriculumSemester";

    private final CurriculumSemesterService curriculumSemesterService;

    public CurriculumSemesterResource(CurriculumSemesterService curriculumSemesterService) {
        this.curriculumSemesterService = curriculumSemesterService;
    }

    /**
     * POST  /curriculum-semesters : Create a new curriculumSemester.
     *
     * @param curriculumSemesterDTO the curriculumSemesterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new curriculumSemesterDTO, or with status 400 (Bad Request) if the curriculumSemester has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/curriculum-semesters")
    @Timed
    public ResponseEntity<CurriculumSemesterDTO> createCurriculumSemester(@RequestBody CurriculumSemesterDTO curriculumSemesterDTO) throws URISyntaxException {
        log.debug("REST request to save CurriculumSemester : {}", curriculumSemesterDTO);
        if (curriculumSemesterDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new curriculumSemester cannot already have an ID")).body(null);
        }
        CurriculumSemesterDTO result = curriculumSemesterService.save(curriculumSemesterDTO);
        return ResponseEntity.created(new URI("/api/curriculum-semesters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /curriculum-semesters : Updates an existing curriculumSemester.
     *
     * @param curriculumSemesterDTO the curriculumSemesterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated curriculumSemesterDTO,
     * or with status 400 (Bad Request) if the curriculumSemesterDTO is not valid,
     * or with status 500 (Internal Server Error) if the curriculumSemesterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/curriculum-semesters")
    @Timed
    public ResponseEntity<CurriculumSemesterDTO> updateCurriculumSemester(@RequestBody CurriculumSemesterDTO curriculumSemesterDTO) throws URISyntaxException {
        log.debug("REST request to update CurriculumSemester : {}", curriculumSemesterDTO);
        if (curriculumSemesterDTO.getId() == null) {
            return createCurriculumSemester(curriculumSemesterDTO);
        }
        CurriculumSemesterDTO result = curriculumSemesterService.save(curriculumSemesterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, curriculumSemesterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /curriculum-semesters : get all the curriculumSemesters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of curriculumSemesters in body
     */
    @GetMapping("/curriculum-semesters")
    @Timed
    public List<CurriculumSemesterDTO> getAllCurriculumSemesters() {
        log.debug("REST request to get all CurriculumSemesters");
        return curriculumSemesterService.findAll();
    }

    /**
     * GET  /curriculum-semesters/:id : get the "id" curriculumSemester.
     *
     * @param id the id of the curriculumSemesterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the curriculumSemesterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/curriculum-semesters/{id}")
    @Timed
    public ResponseEntity<CurriculumSemesterDTO> getCurriculumSemester(@PathVariable Long id) {
        log.debug("REST request to get CurriculumSemester : {}", id);
        CurriculumSemesterDTO curriculumSemesterDTO = curriculumSemesterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(curriculumSemesterDTO));
    }

    /**
     * DELETE  /curriculum-semesters/:id : delete the "id" curriculumSemester.
     *
     * @param id the id of the curriculumSemesterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/curriculum-semesters/{id}")
    @Timed
    public ResponseEntity<Void> deleteCurriculumSemester(@PathVariable Long id) {
        log.debug("REST request to delete CurriculumSemester : {}", id);
        curriculumSemesterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/curriculum-semesters?query=:query : search for the curriculumSemester corresponding
     * to the query.
     *
     * @param query the query of the curriculumSemester search
     * @return the result of the search
     */
    @GetMapping("/_search/curriculum-semesters")
    @Timed
    public List<CurriculumSemesterDTO> searchCurriculumSemesters(@RequestParam String query) {
        log.debug("REST request to search CurriculumSemesters for query {}", query);
        return curriculumSemesterService.search(query);
    }

}
