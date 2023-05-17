package ly.alfairouz.lab.web.rest;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ly.alfairouz.lab.repository.RepresentativeDeliveryRepository;
import ly.alfairouz.lab.service.RepresentativeDeliveryQueryService;
import ly.alfairouz.lab.service.RepresentativeDeliveryService;
import ly.alfairouz.lab.service.criteria.ExpenseCriteria;
import ly.alfairouz.lab.service.criteria.RepresentativeDeliveryCriteria;
import ly.alfairouz.lab.service.dto.ExpenseDTO;
import ly.alfairouz.lab.service.dto.RepresentativeDeliveryDTO;
import ly.alfairouz.lab.web.rest.errors.BadRequestAlertException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ly.alfairouz.lab.domain.RepresentativeDelivery}.
 */
@RestController
@RequestMapping("/api")
public class RepresentativeDeliveryResource {

    private final Logger log = LoggerFactory.getLogger(RepresentativeDeliveryResource.class);

    private static final String ENTITY_NAME = "representativeDelivery";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RepresentativeDeliveryService representativeDeliveryService;

    private final RepresentativeDeliveryRepository representativeDeliveryRepository;

    private final RepresentativeDeliveryQueryService representativeDeliveryQueryService;

    public RepresentativeDeliveryResource(
        RepresentativeDeliveryService representativeDeliveryService,
        RepresentativeDeliveryRepository representativeDeliveryRepository,
        RepresentativeDeliveryQueryService representativeDeliveryQueryService
    ) {
        this.representativeDeliveryService = representativeDeliveryService;
        this.representativeDeliveryRepository = representativeDeliveryRepository;
        this.representativeDeliveryQueryService = representativeDeliveryQueryService;
    }

    /**
     * {@code POST  /representative-deliveries} : Create a new representativeDelivery.
     *
     * @param representativeDeliveryDTO the representativeDeliveryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new representativeDeliveryDTO, or with status {@code 400 (Bad Request)} if the representativeDelivery has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/representative-deliveries")
    public ResponseEntity<RepresentativeDeliveryDTO> createRepresentativeDelivery(
        @RequestBody RepresentativeDeliveryDTO representativeDeliveryDTO
    ) throws URISyntaxException {
        log.debug("REST request to save RepresentativeDelivery : {}", representativeDeliveryDTO);
        if (representativeDeliveryDTO.getId() != null) {
            throw new BadRequestAlertException("A new representativeDelivery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RepresentativeDeliveryDTO result = representativeDeliveryService.save(representativeDeliveryDTO);
        return ResponseEntity
            .created(new URI("/api/representative-deliveries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /representative-deliveries/:id} : Updates an existing representativeDelivery.
     *
     * @param id the id of the representativeDeliveryDTO to save.
     * @param representativeDeliveryDTO the representativeDeliveryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated representativeDeliveryDTO,
     * or with status {@code 400 (Bad Request)} if the representativeDeliveryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the representativeDeliveryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/representative-deliveries/{id}")
    public ResponseEntity<RepresentativeDeliveryDTO> updateRepresentativeDelivery(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RepresentativeDeliveryDTO representativeDeliveryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RepresentativeDelivery : {}, {}", id, representativeDeliveryDTO);
        if (representativeDeliveryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, representativeDeliveryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!representativeDeliveryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RepresentativeDeliveryDTO result = representativeDeliveryService.update(representativeDeliveryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, representativeDeliveryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /representative-deliveries/:id} : Partial updates given fields of an existing representativeDelivery, field will ignore if it is null
     *
     * @param id the id of the representativeDeliveryDTO to save.
     * @param representativeDeliveryDTO the representativeDeliveryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated representativeDeliveryDTO,
     * or with status {@code 400 (Bad Request)} if the representativeDeliveryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the representativeDeliveryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the representativeDeliveryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/representative-deliveries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RepresentativeDeliveryDTO> partialUpdateRepresentativeDelivery(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RepresentativeDeliveryDTO representativeDeliveryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RepresentativeDelivery partially : {}, {}", id, representativeDeliveryDTO);
        if (representativeDeliveryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, representativeDeliveryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!representativeDeliveryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RepresentativeDeliveryDTO> result = representativeDeliveryService.partialUpdate(representativeDeliveryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, representativeDeliveryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /representative-deliveries} : get all the representativeDeliveries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of representativeDeliveries in body.
     */
    @GetMapping("/representative-deliveries")
    public ResponseEntity<List<RepresentativeDeliveryDTO>> getAllRepresentativeDeliveries(
        RepresentativeDeliveryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get RepresentativeDeliveries by criteria: {}", criteria);
        Page<RepresentativeDeliveryDTO> page = representativeDeliveryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /representative-deliveries/count} : count all the representativeDeliveries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/representative-deliveries/count")
    public ResponseEntity<Long> countRepresentativeDeliveries(RepresentativeDeliveryCriteria criteria) {
        log.debug("REST request to count RepresentativeDeliveries by criteria: {}", criteria);
        return ResponseEntity.ok().body(representativeDeliveryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /representative-deliveries/:id} : get the "id" representativeDelivery.
     *
     * @param id the id of the representativeDeliveryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the representativeDeliveryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/representative-deliveries/{id}")
    public ResponseEntity<RepresentativeDeliveryDTO> getRepresentativeDelivery(@PathVariable Long id) {
        log.debug("REST request to get RepresentativeDelivery : {}", id);
        Optional<RepresentativeDeliveryDTO> representativeDeliveryDTO = representativeDeliveryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(representativeDeliveryDTO);
    }

    /**
     * {@code DELETE  /representative-deliveries/:id} : delete the "id" representativeDelivery.
     *
     * @param id the id of the representativeDeliveryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/representative-deliveries/{id}")
    public ResponseEntity<Void> deleteRepresentativeDelivery(@PathVariable Long id) {
        log.debug("REST request to delete RepresentativeDelivery : {}", id);
        representativeDeliveryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping(value = "/public/representative-deliveries/xlsx/criteria/", produces = "application/vnd.ms-excel")
    public ResponseEntity<byte[]> getRepresentativeDeliveryAsXSLXByCriteria(RepresentativeDeliveryCriteria criteria) {
        log.debug("REST request to get xslx");

        String[] columns = {
            "Id", "Date", "Details", "Total"
        };

        List<RepresentativeDeliveryDTO> representativeDeliveryDTOList = representativeDeliveryQueryService.findByCriteria(criteria);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("RepresentativeDeliveries");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Other rows and cells with contacts data
        int rowNum = 1;

        for (RepresentativeDeliveryDTO representativeDeliveryDTO : representativeDeliveryDTOList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(representativeDeliveryDTO.getId());
            row.createCell(1).setCellValue(representativeDeliveryDTO.getDateAt().toString());
            row.createCell(2).setCellValue(representativeDeliveryDTO.getDetails());
            row.createCell(3).setCellValue(representativeDeliveryDTO.getTotal());
        }

        //Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        byte[] bytes = new byte[0];

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            bos.close();
            bytes = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf("application/vnd.ms-excel"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + new Date() + ".xlsx");
        header.setContentLength(bytes.length);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bytes), header);
    }
}
