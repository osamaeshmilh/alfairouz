package ly.alfairouz.lab.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ly.alfairouz.lab.repository.BiopsyRepository;
import ly.alfairouz.lab.service.BiopsyQueryService;
import ly.alfairouz.lab.service.BiopsyService;
import ly.alfairouz.lab.service.criteria.BiopsyCriteria;
import ly.alfairouz.lab.service.dto.BiopsyDTO;
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
 * REST controller for managing {@link ly.alfairouz.lab.domain.Biopsy}.
 */
@RestController
@RequestMapping("/api")
public class BiopsyResource {

    private final Logger log = LoggerFactory.getLogger(BiopsyResource.class);

    private static final String ENTITY_NAME = "biopsy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BiopsyService biopsyService;

    private final BiopsyRepository biopsyRepository;

    private final BiopsyQueryService biopsyQueryService;

    public BiopsyResource(BiopsyService biopsyService, BiopsyRepository biopsyRepository, BiopsyQueryService biopsyQueryService) {
        this.biopsyService = biopsyService;
        this.biopsyRepository = biopsyRepository;
        this.biopsyQueryService = biopsyQueryService;
    }

    /**
     * {@code POST  /biopsies} : Create a new biopsy.
     *
     * @param biopsyDTO the biopsyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new biopsyDTO, or with status {@code 400 (Bad Request)} if the biopsy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/biopsies")
    public ResponseEntity<BiopsyDTO> createBiopsy(@RequestBody BiopsyDTO biopsyDTO) throws URISyntaxException {
        log.debug("REST request to save Biopsy : {}", biopsyDTO);
        if (biopsyDTO.getId() != null) {
            throw new BadRequestAlertException("A new biopsy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BiopsyDTO result = biopsyService.save(biopsyDTO);
        return ResponseEntity
            .created(new URI("/api/biopsies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /biopsies/:id} : Updates an existing biopsy.
     *
     * @param id the id of the biopsyDTO to save.
     * @param biopsyDTO the biopsyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated biopsyDTO,
     * or with status {@code 400 (Bad Request)} if the biopsyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the biopsyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/biopsies/{id}")
    public ResponseEntity<BiopsyDTO> updateBiopsy(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BiopsyDTO biopsyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Biopsy : {}, {}", id, biopsyDTO);
        if (biopsyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, biopsyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!biopsyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BiopsyDTO result = biopsyService.update(biopsyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, biopsyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /biopsies/:id} : Partial updates given fields of an existing biopsy, field will ignore if it is null
     *
     * @param id the id of the biopsyDTO to save.
     * @param biopsyDTO the biopsyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated biopsyDTO,
     * or with status {@code 400 (Bad Request)} if the biopsyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the biopsyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the biopsyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/biopsies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BiopsyDTO> partialUpdateBiopsy(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BiopsyDTO biopsyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Biopsy partially : {}, {}", id, biopsyDTO);
        if (biopsyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, biopsyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!biopsyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BiopsyDTO> result = biopsyService.partialUpdate(biopsyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, biopsyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /biopsies} : get all the biopsies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of biopsies in body.
     */
    @GetMapping("/biopsies")
    public ResponseEntity<List<BiopsyDTO>> getAllBiopsies(
        BiopsyCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Biopsies by criteria: {}", criteria);
        Page<BiopsyDTO> page = biopsyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /biopsies/count} : count all the biopsies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/biopsies/count")
    public ResponseEntity<Long> countBiopsies(BiopsyCriteria criteria) {
        log.debug("REST request to count Biopsies by criteria: {}", criteria);
        return ResponseEntity.ok().body(biopsyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /biopsies/:id} : get the "id" biopsy.
     *
     * @param id the id of the biopsyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the biopsyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/biopsies/{id}")
    public ResponseEntity<BiopsyDTO> getBiopsy(@PathVariable Long id) {
        log.debug("REST request to get Biopsy : {}", id);
        Optional<BiopsyDTO> biopsyDTO = biopsyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(biopsyDTO);
    }

    /**
     * {@code DELETE  /biopsies/:id} : delete the "id" biopsy.
     *
     * @param id the id of the biopsyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/biopsies/{id}")
    public ResponseEntity<Void> deleteBiopsy(@PathVariable Long id) {
        log.debug("REST request to delete Biopsy : {}", id);
        biopsyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
