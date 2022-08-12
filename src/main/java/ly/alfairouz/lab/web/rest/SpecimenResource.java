package ly.alfairouz.lab.web.rest;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;

import ly.alfairouz.lab.repository.SpecimenRepository;
import ly.alfairouz.lab.service.SpecimenQueryService;
import ly.alfairouz.lab.service.SpecimenService;
import ly.alfairouz.lab.service.criteria.SpecimenCriteria;
import ly.alfairouz.lab.service.dto.SpecimenDTO;
import ly.alfairouz.lab.service.util.JasperReportsUtil;
import ly.alfairouz.lab.web.rest.errors.BadRequestAlertException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing {@link ly.alfairouz.lab.domain.Specimen}.
 */
@RestController
@RequestMapping("/api")
public class SpecimenResource {

    private final Logger log = LoggerFactory.getLogger(SpecimenResource.class);

    private static final String ENTITY_NAME = "specimen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecimenService specimenService;

    private final SpecimenRepository specimenRepository;

    private final SpecimenQueryService specimenQueryService;

    @Autowired
    JasperReportsUtil jasperReportsUtil;

    public SpecimenResource(
        SpecimenService specimenService,
        SpecimenRepository specimenRepository,
        SpecimenQueryService specimenQueryService
    ) {
        this.specimenService = specimenService;
        this.specimenRepository = specimenRepository;
        this.specimenQueryService = specimenQueryService;
    }

    /**
     * {@code POST  /specimen} : Create a new specimen.
     *
     * @param specimenDTO the specimenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new specimenDTO, or with status {@code 400 (Bad Request)} if the specimen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/specimen")
    public ResponseEntity<SpecimenDTO> createSpecimen(@RequestBody SpecimenDTO specimenDTO) throws URISyntaxException {
        log.debug("REST request to save Specimen : {}", specimenDTO);
        if (specimenDTO.getId() != null) {
            throw new BadRequestAlertException("A new specimen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpecimenDTO result = specimenService.create(specimenDTO);
        return ResponseEntity
            .created(new URI("/api/specimen/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /specimen/:id} : Updates an existing specimen.
     *
     * @param id the id of the specimenDTO to save.
     * @param specimenDTO the specimenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specimenDTO,
     * or with status {@code 400 (Bad Request)} if the specimenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the specimenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/specimen/{id}")
    public ResponseEntity<SpecimenDTO> updateSpecimen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SpecimenDTO specimenDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Specimen : {}, {}", id, specimenDTO);
        if (specimenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specimenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specimenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SpecimenDTO result = specimenService.update(specimenDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specimenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /specimen/:id} : Partial updates given fields of an existing specimen, field will ignore if it is null
     *
     * @param id the id of the specimenDTO to save.
     * @param specimenDTO the specimenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specimenDTO,
     * or with status {@code 400 (Bad Request)} if the specimenDTO is not valid,
     * or with status {@code 404 (Not Found)} if the specimenDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the specimenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/specimen/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SpecimenDTO> partialUpdateSpecimen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SpecimenDTO specimenDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Specimen partially : {}, {}", id, specimenDTO);
        if (specimenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specimenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specimenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SpecimenDTO> result = specimenService.partialUpdate(specimenDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specimenDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /specimen} : get all the specimen.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specimen in body.
     */
    @GetMapping("/specimen")
    public ResponseEntity<List<SpecimenDTO>> getAllSpecimen(
        SpecimenCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Specimen by criteria: {}", criteria);
        Page<SpecimenDTO> page = specimenQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /specimen/count} : count all the specimen.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/specimen/count")
    public ResponseEntity<Long> countSpecimen(SpecimenCriteria criteria) {
        log.debug("REST request to count Specimen by criteria: {}", criteria);
        return ResponseEntity.ok().body(specimenQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /specimen/:id} : get the "id" specimen.
     *
     * @param id the id of the specimenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the specimenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/specimen/{id}")
    public ResponseEntity<SpecimenDTO> getSpecimen(@PathVariable Long id) {
        log.debug("REST request to get Specimen : {}", id);
        Optional<SpecimenDTO> specimenDTO = specimenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(specimenDTO);
    }

    /**
     * {@code DELETE  /specimen/:id} : delete the "id" specimen.
     *
     * @param id the id of the specimenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/specimen/{id}")
    public ResponseEntity<Void> deleteSpecimen(@PathVariable Long id) {
        log.debug("REST request to delete Specimen : {}", id);
        specimenService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/public/specimen/{id}")
    public ResponseEntity<SpecimenDTO> getSpecimenPublic(@PathVariable Long id) {
        Optional<SpecimenDTO> specimenDTO = specimenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(specimenDTO);
    }


    @GetMapping(value = "/public/specimen/xlsx/", produces = "application/vnd.ms-excel")
    public ResponseEntity<byte[]> getSpecimensAsXSLX() {
        log.debug("REST request to get xslx");

        String[] columns = {"Id", "QR", , "Patient Name"};

        //List<SpecimenDTO> specimenDTOList = specimenService.findAllByCreatedDateBetween(from, to);
        List<SpecimenDTO> specimenDTOList = specimenService.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("specimen");

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

        for (SpecimenDTO specimenDTO : specimenDTOList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(specimenDTO.getId());
            row.createCell(1).setCellValue(specimenDTO.getLabQr());
            row.createCell(2).setCellValue(specimenDTO.getPatient() != null ? specimenDTO.getPatient().getName() : "");

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

    @GetMapping(value = "/public/specimen/report/", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> printSharedInvoicePDF() {
        log.debug("REST request to get report");
        Map<String, Object> parameters = new HashMap<>();
        byte[] fileBytes = jasperReportsUtil.getReportAsPDF(parameters, "report");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report_" + System.currentTimeMillis() + ".pdf");
        header.setContentLength(fileBytes.length);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fileBytes), header);
    }

}
