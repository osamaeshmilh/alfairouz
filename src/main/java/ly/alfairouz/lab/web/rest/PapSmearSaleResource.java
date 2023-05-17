package ly.alfairouz.lab.web.rest;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ly.alfairouz.lab.repository.PapSmearSaleRepository;
import ly.alfairouz.lab.service.PapSmearSaleQueryService;
import ly.alfairouz.lab.service.PapSmearSaleService;
import ly.alfairouz.lab.service.criteria.PapSmearSaleCriteria;
import ly.alfairouz.lab.service.criteria.SpecimenCriteria;
import ly.alfairouz.lab.service.dto.PapSmearSaleDTO;
import ly.alfairouz.lab.service.dto.SpecimenDTO;
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
 * REST controller for managing {@link ly.alfairouz.lab.domain.PapSmearSale}.
 */
@RestController
@RequestMapping("/api")
public class PapSmearSaleResource {

    private final Logger log = LoggerFactory.getLogger(PapSmearSaleResource.class);

    private static final String ENTITY_NAME = "papSmearSale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PapSmearSaleService papSmearSaleService;

    private final PapSmearSaleRepository papSmearSaleRepository;

    private final PapSmearSaleQueryService papSmearSaleQueryService;

    public PapSmearSaleResource(
        PapSmearSaleService papSmearSaleService,
        PapSmearSaleRepository papSmearSaleRepository,
        PapSmearSaleQueryService papSmearSaleQueryService
    ) {
        this.papSmearSaleService = papSmearSaleService;
        this.papSmearSaleRepository = papSmearSaleRepository;
        this.papSmearSaleQueryService = papSmearSaleQueryService;
    }

    /**
     * {@code POST  /pap-smear-sales} : Create a new papSmearSale.
     *
     * @param papSmearSaleDTO the papSmearSaleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new papSmearSaleDTO, or with status {@code 400 (Bad Request)} if the papSmearSale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pap-smear-sales")
    public ResponseEntity<PapSmearSaleDTO> createPapSmearSale(@RequestBody PapSmearSaleDTO papSmearSaleDTO) throws URISyntaxException {
        log.debug("REST request to save PapSmearSale : {}", papSmearSaleDTO);
        if (papSmearSaleDTO.getId() != null) {
            throw new BadRequestAlertException("A new papSmearSale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PapSmearSaleDTO result = papSmearSaleService.save(papSmearSaleDTO);
        return ResponseEntity
            .created(new URI("/api/pap-smear-sales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pap-smear-sales/:id} : Updates an existing papSmearSale.
     *
     * @param id the id of the papSmearSaleDTO to save.
     * @param papSmearSaleDTO the papSmearSaleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated papSmearSaleDTO,
     * or with status {@code 400 (Bad Request)} if the papSmearSaleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the papSmearSaleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pap-smear-sales/{id}")
    public ResponseEntity<PapSmearSaleDTO> updatePapSmearSale(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PapSmearSaleDTO papSmearSaleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PapSmearSale : {}, {}", id, papSmearSaleDTO);
        if (papSmearSaleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, papSmearSaleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!papSmearSaleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PapSmearSaleDTO result = papSmearSaleService.update(papSmearSaleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, papSmearSaleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pap-smear-sales/:id} : Partial updates given fields of an existing papSmearSale, field will ignore if it is null
     *
     * @param id the id of the papSmearSaleDTO to save.
     * @param papSmearSaleDTO the papSmearSaleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated papSmearSaleDTO,
     * or with status {@code 400 (Bad Request)} if the papSmearSaleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the papSmearSaleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the papSmearSaleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pap-smear-sales/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PapSmearSaleDTO> partialUpdatePapSmearSale(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PapSmearSaleDTO papSmearSaleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PapSmearSale partially : {}, {}", id, papSmearSaleDTO);
        if (papSmearSaleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, papSmearSaleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!papSmearSaleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PapSmearSaleDTO> result = papSmearSaleService.partialUpdate(papSmearSaleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, papSmearSaleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pap-smear-sales} : get all the papSmearSales.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of papSmearSales in body.
     */
    @GetMapping("/pap-smear-sales")
    public ResponseEntity<List<PapSmearSaleDTO>> getAllPapSmearSales(
        PapSmearSaleCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PapSmearSales by criteria: {}", criteria);
        Page<PapSmearSaleDTO> page = papSmearSaleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pap-smear-sales/count} : count all the papSmearSales.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/pap-smear-sales/count")
    public ResponseEntity<Long> countPapSmearSales(PapSmearSaleCriteria criteria) {
        log.debug("REST request to count PapSmearSales by criteria: {}", criteria);
        return ResponseEntity.ok().body(papSmearSaleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pap-smear-sales/:id} : get the "id" papSmearSale.
     *
     * @param id the id of the papSmearSaleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the papSmearSaleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pap-smear-sales/{id}")
    public ResponseEntity<PapSmearSaleDTO> getPapSmearSale(@PathVariable Long id) {
        log.debug("REST request to get PapSmearSale : {}", id);
        Optional<PapSmearSaleDTO> papSmearSaleDTO = papSmearSaleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(papSmearSaleDTO);
    }

    /**
     * {@code DELETE  /pap-smear-sales/:id} : delete the "id" papSmearSale.
     *
     * @param id the id of the papSmearSaleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pap-smear-sales/{id}")
    public ResponseEntity<Void> deletePapSmearSale(@PathVariable Long id) {
        log.debug("REST request to delete PapSmearSale : {}", id);
        papSmearSaleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping(value = "/public/pap-smear-sales/xlsx/criteria/", produces = "application/vnd.ms-excel")
    public ResponseEntity<byte[]> getPapSmearSaleAsXSLXByCriteria(PapSmearSaleCriteria criteria) {
        log.debug("REST request to get xslx");

        String[] columns = {
            "Id", "Date At", "Details", "Payment Type", "Quantity", "Total", "Referring Center"
        };

        List<PapSmearSaleDTO> papSmearSaleDTOList = papSmearSaleQueryService.findByCriteria(criteria);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("papSmearSale");

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

        for (PapSmearSaleDTO papSmearSaleDTO : papSmearSaleDTOList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(papSmearSaleDTO.getId());
            row.createCell(1).setCellValue(papSmearSaleDTO.getDateAt().toString());
            row.createCell(2).setCellValue(papSmearSaleDTO.getDetails());
            row.createCell(3).setCellValue(papSmearSaleDTO.getPaymentType().toString());
            row.createCell(4).setCellValue(papSmearSaleDTO.getQuantity());
            row.createCell(5).setCellValue(papSmearSaleDTO.getTotal());
            row.createCell(6).setCellValue(papSmearSaleDTO.getReferringCenter() != null ? papSmearSaleDTO.getReferringCenter().getName() : "");
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
