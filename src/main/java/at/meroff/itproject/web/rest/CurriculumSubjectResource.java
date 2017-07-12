package at.meroff.itproject.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.itproject.service.CurriculumSubjectService;
import at.meroff.itproject.web.rest.util.HeaderUtil;
import at.meroff.itproject.service.dto.CurriculumSubjectDTO;
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
 * REST controller for managing CurriculumSubject.
 */
@RestController
@RequestMapping("/api")
public class CurriculumSubjectResource {

    private final Logger log = LoggerFactory.getLogger(CurriculumSubjectResource.class);

    private static final String ENTITY_NAME = "curriculumSubject";

    private final CurriculumSubjectService curriculumSubjectService;

    public CurriculumSubjectResource(CurriculumSubjectService curriculumSubjectService) {
        this.curriculumSubjectService = curriculumSubjectService;
    }

    /**
     * POST  /curriculum-subjects : Create a new curriculumSubject.
     *
     * @param curriculumSubjectDTO the curriculumSubjectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new curriculumSubjectDTO, or with status 400 (Bad Request) if the curriculumSubject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/curriculum-subjects")
    @Timed
    public ResponseEntity<CurriculumSubjectDTO> createCurriculumSubject(@Valid @RequestBody CurriculumSubjectDTO curriculumSubjectDTO) throws URISyntaxException {
        log.debug("REST request to save CurriculumSubject : {}", curriculumSubjectDTO);
        if (curriculumSubjectDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new curriculumSubject cannot already have an ID")).body(null);
        }
        CurriculumSubjectDTO result = curriculumSubjectService.save(curriculumSubjectDTO);
        return ResponseEntity.created(new URI("/api/curriculum-subjects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /curriculum-subjects : Updates an existing curriculumSubject.
     *
     * @param curriculumSubjectDTO the curriculumSubjectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated curriculumSubjectDTO,
     * or with status 400 (Bad Request) if the curriculumSubjectDTO is not valid,
     * or with status 500 (Internal Server Error) if the curriculumSubjectDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/curriculum-subjects")
    @Timed
    public ResponseEntity<CurriculumSubjectDTO> updateCurriculumSubject(@Valid @RequestBody CurriculumSubjectDTO curriculumSubjectDTO) throws URISyntaxException {
        log.debug("REST request to update CurriculumSubject : {}", curriculumSubjectDTO);
        if (curriculumSubjectDTO.getId() == null) {
            return createCurriculumSubject(curriculumSubjectDTO);
        }
        CurriculumSubjectDTO result = curriculumSubjectService.save(curriculumSubjectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, curriculumSubjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /curriculum-subjects : get all the curriculumSubjects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of curriculumSubjects in body
     */
    @GetMapping("/curriculum-subjects")
    @Timed
    public List<CurriculumSubjectDTO> getAllCurriculumSubjects() {
        log.debug("REST request to get all CurriculumSubjects");
        return curriculumSubjectService.findAll();
    }

    /**
     * GET  /curriculum-subjects/:id : get the "id" curriculumSubject.
     *
     * @param id the id of the curriculumSubjectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the curriculumSubjectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/curriculum-subjects/{id}")
    @Timed
    public ResponseEntity<CurriculumSubjectDTO> getCurriculumSubject(@PathVariable Long id) {
        log.debug("REST request to get CurriculumSubject : {}", id);
        CurriculumSubjectDTO curriculumSubjectDTO = curriculumSubjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(curriculumSubjectDTO));
    }

    /**
     * DELETE  /curriculum-subjects/:id : delete the "id" curriculumSubject.
     *
     * @param id the id of the curriculumSubjectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/curriculum-subjects/{id}")
    @Timed
    public ResponseEntity<Void> deleteCurriculumSubject(@PathVariable Long id) {
        log.debug("REST request to delete CurriculumSubject : {}", id);
        curriculumSubjectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/curriculum-subjects?query=:query : search for the curriculumSubject corresponding
     * to the query.
     *
     * @param query the query of the curriculumSubject search
     * @return the result of the search
     */
    @GetMapping("/_search/curriculum-subjects")
    @Timed
    public List<CurriculumSubjectDTO> searchCurriculumSubjects(@RequestParam String query) {
        log.debug("REST request to search CurriculumSubjects for query {}", query);
        return curriculumSubjectService.search(query);
    }

}
