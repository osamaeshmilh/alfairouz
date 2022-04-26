package ly.alfairouz.lab.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ly.alfairouz.lab.repository.CytologyRepository;
import ly.alfairouz.lab.service.CytologyQueryService;
import ly.alfairouz.lab.service.CytologyService;
import ly.alfairouz.lab.service.criteria.CytologyCriteria;
import ly.alfairouz.lab.service.dto.CytologyDTO;
import ly.alfairouz.lab.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ly.alfairouz.lab.domain.Cytology}.
 */
@RestController
@RequestMapping("/api")
public class CytologyResource {

    private final Logger log = LoggerFactory.getLogger(CytologyResource.class);

    private static final String ENTITY_NAME = "cytology";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CytologyService cytologyService;

    private final CytologyRepository cytologyRepository;

    private final CytologyQueryService cytologyQueryService;

    public CytologyResource(
        CytologyService cytologyService,
        CytologyRepository cytologyRepository,
        CytologyQueryService cytologyQueryService
    ) {
        this.cytologyService = cytologyService;
        this.cytologyRepository = cytologyRepository;
        this.cytologyQueryService = cytologyQueryService;
    }

    /**
     * {@code POST  /cytologies} : Create a new cytology.
     *
     * @param cytologyDTO the cytologyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cytologyDTO, or with status {@code 400 (Bad Request)} if the cytology has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cytologies")
    public ResponseEntity<CytologyDTO> createCytology(@RequestBody CytologyDTO cytologyDTO) throws URISyntaxException {
        log.debug("REST request to save Cytology : {}", cytologyDTO);
        if (cytologyDTO.getId() != null) {
            throw new BadRequestAlertException("A new cytology cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CytologyDTO result = cytologyService.save(cytologyDTO);
        return ResponseEntity
            .created(new URI("/api/cytologies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cytologies/:id} : Updates an existing cytology.
     *
     * @param id the id of the cytologyDTO to save.
     * @param cytologyDTO the cytologyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cytologyDTO,
     * or with status {@code 400 (Bad Request)} if the cytologyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cytologyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cytologies/{id}")
    public ResponseEntity<CytologyDTO> updateCytology(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CytologyDTO cytologyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Cytology : {}, {}", id, cytologyDTO);
        if (cytologyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cytologyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cytologyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CytologyDTO result = cytologyService.update(cytologyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cytologyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cytologies/:id} : Partial updates given fields of an existing cytology, field will ignore if it is null
     *
     * @param id the id of the cytologyDTO to save.
     * @param cytologyDTO the cytologyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cytologyDTO,
     * or with status {@code 400 (Bad Request)} if the cytologyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cytologyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cytologyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cytologies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CytologyDTO> partialUpdateCytology(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CytologyDTO cytologyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cytology partially : {}, {}", id, cytologyDTO);
        if (cytologyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cytologyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cytologyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CytologyDTO> result = cytologyService.partialUpdate(cytologyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cytologyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cytologies} : get all the cytologies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cytologies in body.
     */
    @GetMapping("/cytologies")
    public ResponseEntity<List<CytologyDTO>> getAllCytologies(
        CytologyCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Cytologies by criteria: {}", criteria);
        Page<CytologyDTO> page = cytologyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cytologies/count} : count all the cytologies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cytologies/count")
    public ResponseEntity<Long> countCytologies(CytologyCriteria criteria) {
        log.debug("REST request to count Cytologies by criteria: {}", criteria);
        return ResponseEntity.ok().body(cytologyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cytologies/:id} : get the "id" cytology.
     *
     * @param id the id of the cytologyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cytologyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cytologies/{id}")
    public ResponseEntity<CytologyDTO> getCytology(@PathVariable Long id) {
        log.debug("REST request to get Cytology : {}", id);
        Optional<CytologyDTO> cytologyDTO = cytologyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cytologyDTO);
    }

    /**
     * {@code DELETE  /cytologies/:id} : delete the "id" cytology.
     *
     * @param id the id of the cytologyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cytologies/{id}")
    public ResponseEntity<Void> deleteCytology(@PathVariable Long id) {
        log.debug("REST request to delete Cytology : {}", id);
        cytologyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
