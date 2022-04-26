package ly.alfairouz.lab.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ly.alfairouz.lab.repository.SpecimenTypeRepository;
import ly.alfairouz.lab.service.SpecimenTypeQueryService;
import ly.alfairouz.lab.service.SpecimenTypeService;
import ly.alfairouz.lab.service.criteria.SpecimenTypeCriteria;
import ly.alfairouz.lab.service.dto.SpecimenTypeDTO;
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
 * REST controller for managing {@link ly.alfairouz.lab.domain.SpecimenType}.
 */
@RestController
@RequestMapping("/api")
public class SpecimenTypeResource {

    private final Logger log = LoggerFactory.getLogger(SpecimenTypeResource.class);

    private static final String ENTITY_NAME = "specimenType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecimenTypeService specimenTypeService;

    private final SpecimenTypeRepository specimenTypeRepository;

    private final SpecimenTypeQueryService specimenTypeQueryService;

    public SpecimenTypeResource(
        SpecimenTypeService specimenTypeService,
        SpecimenTypeRepository specimenTypeRepository,
        SpecimenTypeQueryService specimenTypeQueryService
    ) {
        this.specimenTypeService = specimenTypeService;
        this.specimenTypeRepository = specimenTypeRepository;
        this.specimenTypeQueryService = specimenTypeQueryService;
    }

    /**
     * {@code POST  /specimen-types} : Create a new specimenType.
     *
     * @param specimenTypeDTO the specimenTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new specimenTypeDTO, or with status {@code 400 (Bad Request)} if the specimenType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/specimen-types")
    public ResponseEntity<SpecimenTypeDTO> createSpecimenType(@RequestBody SpecimenTypeDTO specimenTypeDTO) throws URISyntaxException {
        log.debug("REST request to save SpecimenType : {}", specimenTypeDTO);
        if (specimenTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new specimenType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpecimenTypeDTO result = specimenTypeService.save(specimenTypeDTO);
        return ResponseEntity
            .created(new URI("/api/specimen-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /specimen-types/:id} : Updates an existing specimenType.
     *
     * @param id the id of the specimenTypeDTO to save.
     * @param specimenTypeDTO the specimenTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specimenTypeDTO,
     * or with status {@code 400 (Bad Request)} if the specimenTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the specimenTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/specimen-types/{id}")
    public ResponseEntity<SpecimenTypeDTO> updateSpecimenType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SpecimenTypeDTO specimenTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SpecimenType : {}, {}", id, specimenTypeDTO);
        if (specimenTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specimenTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specimenTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SpecimenTypeDTO result = specimenTypeService.update(specimenTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specimenTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /specimen-types/:id} : Partial updates given fields of an existing specimenType, field will ignore if it is null
     *
     * @param id the id of the specimenTypeDTO to save.
     * @param specimenTypeDTO the specimenTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specimenTypeDTO,
     * or with status {@code 400 (Bad Request)} if the specimenTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the specimenTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the specimenTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/specimen-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SpecimenTypeDTO> partialUpdateSpecimenType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SpecimenTypeDTO specimenTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SpecimenType partially : {}, {}", id, specimenTypeDTO);
        if (specimenTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specimenTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specimenTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SpecimenTypeDTO> result = specimenTypeService.partialUpdate(specimenTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specimenTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /specimen-types} : get all the specimenTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specimenTypes in body.
     */
    @GetMapping("/specimen-types")
    public ResponseEntity<List<SpecimenTypeDTO>> getAllSpecimenTypes(
        SpecimenTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SpecimenTypes by criteria: {}", criteria);
        Page<SpecimenTypeDTO> page = specimenTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /specimen-types/count} : count all the specimenTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/specimen-types/count")
    public ResponseEntity<Long> countSpecimenTypes(SpecimenTypeCriteria criteria) {
        log.debug("REST request to count SpecimenTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(specimenTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /specimen-types/:id} : get the "id" specimenType.
     *
     * @param id the id of the specimenTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the specimenTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/specimen-types/{id}")
    public ResponseEntity<SpecimenTypeDTO> getSpecimenType(@PathVariable Long id) {
        log.debug("REST request to get SpecimenType : {}", id);
        Optional<SpecimenTypeDTO> specimenTypeDTO = specimenTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(specimenTypeDTO);
    }

    /**
     * {@code DELETE  /specimen-types/:id} : delete the "id" specimenType.
     *
     * @param id the id of the specimenTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/specimen-types/{id}")
    public ResponseEntity<Void> deleteSpecimenType(@PathVariable Long id) {
        log.debug("REST request to delete SpecimenType : {}", id);
        specimenTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
