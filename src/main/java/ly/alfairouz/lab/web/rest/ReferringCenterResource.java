package ly.alfairouz.lab.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ly.alfairouz.lab.repository.ReferringCenterRepository;
import ly.alfairouz.lab.service.ReferringCenterQueryService;
import ly.alfairouz.lab.service.ReferringCenterService;
import ly.alfairouz.lab.service.criteria.ReferringCenterCriteria;
import ly.alfairouz.lab.service.dto.ReferringCenterDTO;
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
 * REST controller for managing {@link ly.alfairouz.lab.domain.ReferringCenter}.
 */
@RestController
@RequestMapping("/api")
public class ReferringCenterResource {

    private final Logger log = LoggerFactory.getLogger(ReferringCenterResource.class);

    private static final String ENTITY_NAME = "referringCenter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReferringCenterService referringCenterService;

    private final ReferringCenterRepository referringCenterRepository;

    private final ReferringCenterQueryService referringCenterQueryService;

    public ReferringCenterResource(
        ReferringCenterService referringCenterService,
        ReferringCenterRepository referringCenterRepository,
        ReferringCenterQueryService referringCenterQueryService
    ) {
        this.referringCenterService = referringCenterService;
        this.referringCenterRepository = referringCenterRepository;
        this.referringCenterQueryService = referringCenterQueryService;
    }

    /**
     * {@code POST  /referring-centers} : Create a new referringCenter.
     *
     * @param referringCenterDTO the referringCenterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new referringCenterDTO, or with status {@code 400 (Bad Request)} if the referringCenter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/referring-centers")
    public ResponseEntity<ReferringCenterDTO> createReferringCenter(@RequestBody ReferringCenterDTO referringCenterDTO)
        throws URISyntaxException {
        log.debug("REST request to save ReferringCenter : {}", referringCenterDTO);
        if (referringCenterDTO.getId() != null) {
            throw new BadRequestAlertException("A new referringCenter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReferringCenterDTO result = referringCenterService.save(referringCenterDTO);
        return ResponseEntity
            .created(new URI("/api/referring-centers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /referring-centers/:id} : Updates an existing referringCenter.
     *
     * @param id the id of the referringCenterDTO to save.
     * @param referringCenterDTO the referringCenterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated referringCenterDTO,
     * or with status {@code 400 (Bad Request)} if the referringCenterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the referringCenterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/referring-centers/{id}")
    public ResponseEntity<ReferringCenterDTO> updateReferringCenter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReferringCenterDTO referringCenterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReferringCenter : {}, {}", id, referringCenterDTO);
        if (referringCenterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, referringCenterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!referringCenterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReferringCenterDTO result = referringCenterService.update(referringCenterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, referringCenterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /referring-centers/:id} : Partial updates given fields of an existing referringCenter, field will ignore if it is null
     *
     * @param id the id of the referringCenterDTO to save.
     * @param referringCenterDTO the referringCenterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated referringCenterDTO,
     * or with status {@code 400 (Bad Request)} if the referringCenterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the referringCenterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the referringCenterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/referring-centers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReferringCenterDTO> partialUpdateReferringCenter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReferringCenterDTO referringCenterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReferringCenter partially : {}, {}", id, referringCenterDTO);
        if (referringCenterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, referringCenterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!referringCenterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReferringCenterDTO> result = referringCenterService.partialUpdate(referringCenterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, referringCenterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /referring-centers} : get all the referringCenters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of referringCenters in body.
     */
    @GetMapping("/referring-centers")
    public ResponseEntity<List<ReferringCenterDTO>> getAllReferringCenters(
        ReferringCenterCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ReferringCenters by criteria: {}", criteria);
        Page<ReferringCenterDTO> page = referringCenterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /referring-centers/count} : count all the referringCenters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/referring-centers/count")
    public ResponseEntity<Long> countReferringCenters(ReferringCenterCriteria criteria) {
        log.debug("REST request to count ReferringCenters by criteria: {}", criteria);
        return ResponseEntity.ok().body(referringCenterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /referring-centers/:id} : get the "id" referringCenter.
     *
     * @param id the id of the referringCenterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the referringCenterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/referring-centers/{id}")
    public ResponseEntity<ReferringCenterDTO> getReferringCenter(@PathVariable Long id) {
        log.debug("REST request to get ReferringCenter : {}", id);
        Optional<ReferringCenterDTO> referringCenterDTO = referringCenterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(referringCenterDTO);
    }

    /**
     * {@code DELETE  /referring-centers/:id} : delete the "id" referringCenter.
     *
     * @param id the id of the referringCenterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/referring-centers/{id}")
    public ResponseEntity<Void> deleteReferringCenter(@PathVariable Long id) {
        log.debug("REST request to delete ReferringCenter : {}", id);
        referringCenterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
