package ly.alfairouz.lab.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ly.alfairouz.lab.repository.BlockWithdrawRepository;
import ly.alfairouz.lab.service.BlockWithdrawQueryService;
import ly.alfairouz.lab.service.BlockWithdrawService;
import ly.alfairouz.lab.service.criteria.BlockWithdrawCriteria;
import ly.alfairouz.lab.service.dto.BlockWithdrawDTO;
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
 * REST controller for managing {@link ly.alfairouz.lab.domain.BlockWithdraw}.
 */
@RestController
@RequestMapping("/api")
public class BlockWithdrawResource {

    private final Logger log = LoggerFactory.getLogger(BlockWithdrawResource.class);

    private static final String ENTITY_NAME = "blockWithdraw";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlockWithdrawService blockWithdrawService;

    private final BlockWithdrawRepository blockWithdrawRepository;

    private final BlockWithdrawQueryService blockWithdrawQueryService;

    public BlockWithdrawResource(
        BlockWithdrawService blockWithdrawService,
        BlockWithdrawRepository blockWithdrawRepository,
        BlockWithdrawQueryService blockWithdrawQueryService
    ) {
        this.blockWithdrawService = blockWithdrawService;
        this.blockWithdrawRepository = blockWithdrawRepository;
        this.blockWithdrawQueryService = blockWithdrawQueryService;
    }

    /**
     * {@code POST  /block-withdraws} : Create a new blockWithdraw.
     *
     * @param blockWithdrawDTO the blockWithdrawDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blockWithdrawDTO, or with status {@code 400 (Bad Request)} if the blockWithdraw has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/block-withdraws")
    public ResponseEntity<BlockWithdrawDTO> createBlockWithdraw(@RequestBody BlockWithdrawDTO blockWithdrawDTO) throws URISyntaxException {
        log.debug("REST request to save BlockWithdraw : {}", blockWithdrawDTO);
        if (blockWithdrawDTO.getId() != null) {
            throw new BadRequestAlertException("A new blockWithdraw cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BlockWithdrawDTO result = blockWithdrawService.save(blockWithdrawDTO);
        return ResponseEntity
            .created(new URI("/api/block-withdraws/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /block-withdraws/:id} : Updates an existing blockWithdraw.
     *
     * @param id the id of the blockWithdrawDTO to save.
     * @param blockWithdrawDTO the blockWithdrawDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blockWithdrawDTO,
     * or with status {@code 400 (Bad Request)} if the blockWithdrawDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blockWithdrawDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/block-withdraws/{id}")
    public ResponseEntity<BlockWithdrawDTO> updateBlockWithdraw(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BlockWithdrawDTO blockWithdrawDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BlockWithdraw : {}, {}", id, blockWithdrawDTO);
        if (blockWithdrawDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blockWithdrawDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blockWithdrawRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BlockWithdrawDTO result = blockWithdrawService.update(blockWithdrawDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blockWithdrawDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /block-withdraws/:id} : Partial updates given fields of an existing blockWithdraw, field will ignore if it is null
     *
     * @param id the id of the blockWithdrawDTO to save.
     * @param blockWithdrawDTO the blockWithdrawDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blockWithdrawDTO,
     * or with status {@code 400 (Bad Request)} if the blockWithdrawDTO is not valid,
     * or with status {@code 404 (Not Found)} if the blockWithdrawDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the blockWithdrawDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/block-withdraws/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BlockWithdrawDTO> partialUpdateBlockWithdraw(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BlockWithdrawDTO blockWithdrawDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BlockWithdraw partially : {}, {}", id, blockWithdrawDTO);
        if (blockWithdrawDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blockWithdrawDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blockWithdrawRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BlockWithdrawDTO> result = blockWithdrawService.partialUpdate(blockWithdrawDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blockWithdrawDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /block-withdraws} : get all the blockWithdraws.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blockWithdraws in body.
     */
    @GetMapping("/block-withdraws")
    public ResponseEntity<List<BlockWithdrawDTO>> getAllBlockWithdraws(
        BlockWithdrawCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BlockWithdraws by criteria: {}", criteria);
        Page<BlockWithdrawDTO> page = blockWithdrawQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /block-withdraws/count} : count all the blockWithdraws.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/block-withdraws/count")
    public ResponseEntity<Long> countBlockWithdraws(BlockWithdrawCriteria criteria) {
        log.debug("REST request to count BlockWithdraws by criteria: {}", criteria);
        return ResponseEntity.ok().body(blockWithdrawQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /block-withdraws/:id} : get the "id" blockWithdraw.
     *
     * @param id the id of the blockWithdrawDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blockWithdrawDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/block-withdraws/{id}")
    public ResponseEntity<BlockWithdrawDTO> getBlockWithdraw(@PathVariable Long id) {
        log.debug("REST request to get BlockWithdraw : {}", id);
        Optional<BlockWithdrawDTO> blockWithdrawDTO = blockWithdrawService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blockWithdrawDTO);
    }

    /**
     * {@code DELETE  /block-withdraws/:id} : delete the "id" blockWithdraw.
     *
     * @param id the id of the blockWithdrawDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/block-withdraws/{id}")
    public ResponseEntity<Void> deleteBlockWithdraw(@PathVariable Long id) {
        log.debug("REST request to delete BlockWithdraw : {}", id);
        blockWithdrawService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
