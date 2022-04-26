package ly.alfairouz.lab.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ly.alfairouz.lab.repository.OrganRepository;
import ly.alfairouz.lab.service.OrganQueryService;
import ly.alfairouz.lab.service.OrganService;
import ly.alfairouz.lab.service.criteria.OrganCriteria;
import ly.alfairouz.lab.service.dto.OrganDTO;
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
 * REST controller for managing {@link ly.alfairouz.lab.domain.Organ}.
 */
@RestController
@RequestMapping("/api")
public class OrganResource {

    private final Logger log = LoggerFactory.getLogger(OrganResource.class);

    private static final String ENTITY_NAME = "organ";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganService organService;

    private final OrganRepository organRepository;

    private final OrganQueryService organQueryService;

    public OrganResource(OrganService organService, OrganRepository organRepository, OrganQueryService organQueryService) {
        this.organService = organService;
        this.organRepository = organRepository;
        this.organQueryService = organQueryService;
    }

    /**
     * {@code POST  /organs} : Create a new organ.
     *
     * @param organDTO the organDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organDTO, or with status {@code 400 (Bad Request)} if the organ has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organs")
    public ResponseEntity<OrganDTO> createOrgan(@RequestBody OrganDTO organDTO) throws URISyntaxException {
        log.debug("REST request to save Organ : {}", organDTO);
        if (organDTO.getId() != null) {
            throw new BadRequestAlertException("A new organ cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganDTO result = organService.save(organDTO);
        return ResponseEntity
            .created(new URI("/api/organs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organs/:id} : Updates an existing organ.
     *
     * @param id the id of the organDTO to save.
     * @param organDTO the organDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organDTO,
     * or with status {@code 400 (Bad Request)} if the organDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organs/{id}")
    public ResponseEntity<OrganDTO> updateOrgan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrganDTO organDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Organ : {}, {}", id, organDTO);
        if (organDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrganDTO result = organService.update(organDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /organs/:id} : Partial updates given fields of an existing organ, field will ignore if it is null
     *
     * @param id the id of the organDTO to save.
     * @param organDTO the organDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organDTO,
     * or with status {@code 400 (Bad Request)} if the organDTO is not valid,
     * or with status {@code 404 (Not Found)} if the organDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the organDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/organs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrganDTO> partialUpdateOrgan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrganDTO organDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Organ partially : {}, {}", id, organDTO);
        if (organDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganDTO> result = organService.partialUpdate(organDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /organs} : get all the organs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organs in body.
     */
    @GetMapping("/organs")
    public ResponseEntity<List<OrganDTO>> getAllOrgans(
        OrganCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Organs by criteria: {}", criteria);
        Page<OrganDTO> page = organQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organs/count} : count all the organs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/organs/count")
    public ResponseEntity<Long> countOrgans(OrganCriteria criteria) {
        log.debug("REST request to count Organs by criteria: {}", criteria);
        return ResponseEntity.ok().body(organQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /organs/:id} : get the "id" organ.
     *
     * @param id the id of the organDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organs/{id}")
    public ResponseEntity<OrganDTO> getOrgan(@PathVariable Long id) {
        log.debug("REST request to get Organ : {}", id);
        Optional<OrganDTO> organDTO = organService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organDTO);
    }

    /**
     * {@code DELETE  /organs/:id} : delete the "id" organ.
     *
     * @param id the id of the organDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organs/{id}")
    public ResponseEntity<Void> deleteOrgan(@PathVariable Long id) {
        log.debug("REST request to delete Organ : {}", id);
        organService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
