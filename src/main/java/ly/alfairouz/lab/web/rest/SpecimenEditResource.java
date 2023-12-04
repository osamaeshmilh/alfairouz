package ly.alfairouz.lab.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ly.alfairouz.lab.repository.SpecimenEditRepository;
import ly.alfairouz.lab.service.SpecimenEditQueryService;
import ly.alfairouz.lab.service.SpecimenEditService;
import ly.alfairouz.lab.service.criteria.SpecimenEditCriteria;
import ly.alfairouz.lab.service.dto.SpecimenEditDTO;
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
 * REST controller for managing {@link ly.alfairouz.lab.domain.SpecimenEdit}.
 */
@RestController
@RequestMapping("/api")
public class SpecimenEditResource {

    private final Logger log = LoggerFactory.getLogger(SpecimenEditResource.class);

    private static final String ENTITY_NAME = "specimenEdit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecimenEditService specimenEditService;

    private final SpecimenEditRepository specimenEditRepository;

    private final SpecimenEditQueryService specimenEditQueryService;

    public SpecimenEditResource(
        SpecimenEditService specimenEditService,
        SpecimenEditRepository specimenEditRepository,
        SpecimenEditQueryService specimenEditQueryService
    ) {
        this.specimenEditService = specimenEditService;
        this.specimenEditRepository = specimenEditRepository;
        this.specimenEditQueryService = specimenEditQueryService;
    }

    /**
     * {@code POST  /specimen-edits} : Create a new specimenEdit.
     *
     * @param specimenEditDTO the specimenEditDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new specimenEditDTO, or with status {@code 400 (Bad Request)} if the specimenEdit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/specimen-edits")
    public ResponseEntity<SpecimenEditDTO> createSpecimenEdit(@RequestBody SpecimenEditDTO specimenEditDTO) throws URISyntaxException {
        log.debug("REST request to save SpecimenEdit : {}", specimenEditDTO);
        if (specimenEditDTO.getId() != null) {
            throw new BadRequestAlertException("A new specimenEdit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpecimenEditDTO result = specimenEditService.save(specimenEditDTO);
        return ResponseEntity
            .created(new URI("/api/specimen-edits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /specimen-edits/:id} : Updates an existing specimenEdit.
     *
     * @param id              the id of the specimenEditDTO to save.
     * @param specimenEditDTO the specimenEditDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specimenEditDTO,
     * or with status {@code 400 (Bad Request)} if the specimenEditDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the specimenEditDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/specimen-edits/{id}")
    public ResponseEntity<SpecimenEditDTO> updateSpecimenEdit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SpecimenEditDTO specimenEditDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SpecimenEdit : {}, {}", id, specimenEditDTO);
        if (specimenEditDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specimenEditDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specimenEditRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SpecimenEditDTO result = specimenEditService.save(specimenEditDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specimenEditDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /specimen-edits/:id} : Partial updates given fields of an existing specimenEdit, field will ignore if it is null
     *
     * @param id              the id of the specimenEditDTO to save.
     * @param specimenEditDTO the specimenEditDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specimenEditDTO,
     * or with status {@code 400 (Bad Request)} if the specimenEditDTO is not valid,
     * or with status {@code 404 (Not Found)} if the specimenEditDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the specimenEditDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/specimen-edits/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<SpecimenEditDTO> partialUpdateSpecimenEdit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SpecimenEditDTO specimenEditDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SpecimenEdit partially : {}, {}", id, specimenEditDTO);
        if (specimenEditDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specimenEditDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specimenEditRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SpecimenEditDTO> result = specimenEditService.partialUpdate(specimenEditDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specimenEditDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /specimen-edits} : get all the specimenEdits.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specimenEdits in body.
     */
    @GetMapping("/specimen-edits")
    public ResponseEntity<List<SpecimenEditDTO>> getAllSpecimenEdits(
        SpecimenEditCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SpecimenEdits by criteria: {}", criteria);
        Page<SpecimenEditDTO> page = specimenEditQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /specimen-edits/count} : count all the specimenEdits.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/specimen-edits/count")
    public ResponseEntity<Long> countSpecimenEdits(SpecimenEditCriteria criteria) {
        log.debug("REST request to count SpecimenEdits by criteria: {}", criteria);
        return ResponseEntity.ok().body(specimenEditQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /specimen-edits/:id} : get the "id" specimenEdit.
     *
     * @param id the id of the specimenEditDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the specimenEditDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/specimen-edits/{id}")
    public ResponseEntity<SpecimenEditDTO> getSpecimenEdit(@PathVariable Long id) {
        log.debug("REST request to get SpecimenEdit : {}", id);
        Optional<SpecimenEditDTO> specimenEditDTO = specimenEditService.findOne(id);
        return ResponseUtil.wrapOrNotFound(specimenEditDTO);
    }

    /**
     * {@code DELETE  /specimen-edits/:id} : delete the "id" specimenEdit.
     *
     * @param id the id of the specimenEditDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/specimen-edits/{id}")
    public ResponseEntity<Void> deleteSpecimenEdit(@PathVariable Long id) {
        log.debug("REST request to delete SpecimenEdit : {}", id);
        specimenEditService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
