package ly.alfairouz.lab.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ly.alfairouz.lab.repository.ReferringCenterPriceRepository;
import ly.alfairouz.lab.service.ReferringCenterPriceQueryService;
import ly.alfairouz.lab.service.ReferringCenterPriceService;
import ly.alfairouz.lab.service.criteria.ReferringCenterPriceCriteria;
import ly.alfairouz.lab.service.dto.ReferringCenterPriceDTO;
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
 * REST controller for managing {@link ly.alfairouz.lab.domain.ReferringCenterPrice}.
 */
@RestController
@RequestMapping("/api")
public class ReferringCenterPriceResource {

    private final Logger log = LoggerFactory.getLogger(ReferringCenterPriceResource.class);

    private static final String ENTITY_NAME = "referringCenterPrice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReferringCenterPriceService referringCenterPriceService;

    private final ReferringCenterPriceRepository referringCenterPriceRepository;

    private final ReferringCenterPriceQueryService referringCenterPriceQueryService;

    public ReferringCenterPriceResource(
        ReferringCenterPriceService referringCenterPriceService,
        ReferringCenterPriceRepository referringCenterPriceRepository,
        ReferringCenterPriceQueryService referringCenterPriceQueryService
    ) {
        this.referringCenterPriceService = referringCenterPriceService;
        this.referringCenterPriceRepository = referringCenterPriceRepository;
        this.referringCenterPriceQueryService = referringCenterPriceQueryService;
    }

    /**
     * {@code POST  /referring-center-prices} : Create a new referringCenterPrice.
     *
     * @param referringCenterPriceDTO the referringCenterPriceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new referringCenterPriceDTO, or with status {@code 400 (Bad Request)} if the referringCenterPrice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/referring-center-prices")
    public ResponseEntity<ReferringCenterPriceDTO> createReferringCenterPrice(@RequestBody ReferringCenterPriceDTO referringCenterPriceDTO)
        throws URISyntaxException {
        log.debug("REST request to save ReferringCenterPrice : {}", referringCenterPriceDTO);
        if (referringCenterPriceDTO.getId() != null) {
            throw new BadRequestAlertException("A new referringCenterPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReferringCenterPriceDTO result = referringCenterPriceService.save(referringCenterPriceDTO);
        return ResponseEntity
            .created(new URI("/api/referring-center-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /referring-center-prices/:id} : Updates an existing referringCenterPrice.
     *
     * @param id                      the id of the referringCenterPriceDTO to save.
     * @param referringCenterPriceDTO the referringCenterPriceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated referringCenterPriceDTO,
     * or with status {@code 400 (Bad Request)} if the referringCenterPriceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the referringCenterPriceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/referring-center-prices/{id}")
    public ResponseEntity<ReferringCenterPriceDTO> updateReferringCenterPrice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReferringCenterPriceDTO referringCenterPriceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReferringCenterPrice : {}, {}", id, referringCenterPriceDTO);
        if (referringCenterPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, referringCenterPriceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!referringCenterPriceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReferringCenterPriceDTO result = referringCenterPriceService.save(referringCenterPriceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, referringCenterPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /referring-center-prices/:id} : Partial updates given fields of an existing referringCenterPrice, field will ignore if it is null
     *
     * @param id                      the id of the referringCenterPriceDTO to save.
     * @param referringCenterPriceDTO the referringCenterPriceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated referringCenterPriceDTO,
     * or with status {@code 400 (Bad Request)} if the referringCenterPriceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the referringCenterPriceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the referringCenterPriceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/referring-center-prices/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<ReferringCenterPriceDTO> partialUpdateReferringCenterPrice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReferringCenterPriceDTO referringCenterPriceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReferringCenterPrice partially : {}, {}", id, referringCenterPriceDTO);
        if (referringCenterPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, referringCenterPriceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!referringCenterPriceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReferringCenterPriceDTO> result = referringCenterPriceService.partialUpdate(referringCenterPriceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, referringCenterPriceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /referring-center-prices} : get all the referringCenterPrices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of referringCenterPrices in body.
     */
    @GetMapping("/referring-center-prices")
    public ResponseEntity<List<ReferringCenterPriceDTO>> getAllReferringCenterPrices(
        ReferringCenterPriceCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ReferringCenterPrices by criteria: {}", criteria);
        Page<ReferringCenterPriceDTO> page = referringCenterPriceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /referring-center-prices/count} : count all the referringCenterPrices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/referring-center-prices/count")
    public ResponseEntity<Long> countReferringCenterPrices(ReferringCenterPriceCriteria criteria) {
        log.debug("REST request to count ReferringCenterPrices by criteria: {}", criteria);
        return ResponseEntity.ok().body(referringCenterPriceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /referring-center-prices/:id} : get the "id" referringCenterPrice.
     *
     * @param id the id of the referringCenterPriceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the referringCenterPriceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/referring-center-prices/{id}")
    public ResponseEntity<ReferringCenterPriceDTO> getReferringCenterPrice(@PathVariable Long id) {
        log.debug("REST request to get ReferringCenterPrice : {}", id);
        Optional<ReferringCenterPriceDTO> referringCenterPriceDTO = referringCenterPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(referringCenterPriceDTO);
    }

    /**
     * {@code DELETE  /referring-center-prices/:id} : delete the "id" referringCenterPrice.
     *
     * @param id the id of the referringCenterPriceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/referring-center-prices/{id}")
    public ResponseEntity<Void> deleteReferringCenterPrice(@PathVariable Long id) {
        log.debug("REST request to delete ReferringCenterPrice : {}", id);
        referringCenterPriceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
