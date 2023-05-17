package ly.alfairouz.lab.web.rest;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ly.alfairouz.lab.repository.ExpenseRepository;
import ly.alfairouz.lab.service.ExpenseQueryService;
import ly.alfairouz.lab.service.ExpenseService;
import ly.alfairouz.lab.service.criteria.ExpenseCriteria;
import ly.alfairouz.lab.service.criteria.PapSmearSaleCriteria;
import ly.alfairouz.lab.service.dto.ExpenseDTO;
import ly.alfairouz.lab.service.dto.PapSmearSaleDTO;
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
 * REST controller for managing {@link ly.alfairouz.lab.domain.Expense}.
 */
@RestController
@RequestMapping("/api")
public class ExpenseResource {

    private final Logger log = LoggerFactory.getLogger(ExpenseResource.class);

    private static final String ENTITY_NAME = "expense";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExpenseService expenseService;

    private final ExpenseRepository expenseRepository;

    private final ExpenseQueryService expenseQueryService;

    public ExpenseResource(ExpenseService expenseService, ExpenseRepository expenseRepository, ExpenseQueryService expenseQueryService) {
        this.expenseService = expenseService;
        this.expenseRepository = expenseRepository;
        this.expenseQueryService = expenseQueryService;
    }

    /**
     * {@code POST  /expenses} : Create a new expense.
     *
     * @param expenseDTO the expenseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expenseDTO, or with status {@code 400 (Bad Request)} if the expense has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/expenses")
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDTO) throws URISyntaxException {
        log.debug("REST request to save Expense : {}", expenseDTO);
        if (expenseDTO.getId() != null) {
            throw new BadRequestAlertException("A new expense cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExpenseDTO result = expenseService.save(expenseDTO);
        return ResponseEntity
            .created(new URI("/api/expenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /expenses/:id} : Updates an existing expense.
     *
     * @param id the id of the expenseDTO to save.
     * @param expenseDTO the expenseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expenseDTO,
     * or with status {@code 400 (Bad Request)} if the expenseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the expenseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/expenses/{id}")
    public ResponseEntity<ExpenseDTO> updateExpense(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExpenseDTO expenseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Expense : {}, {}", id, expenseDTO);
        if (expenseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, expenseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!expenseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExpenseDTO result = expenseService.update(expenseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, expenseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /expenses/:id} : Partial updates given fields of an existing expense, field will ignore if it is null
     *
     * @param id the id of the expenseDTO to save.
     * @param expenseDTO the expenseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expenseDTO,
     * or with status {@code 400 (Bad Request)} if the expenseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the expenseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the expenseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/expenses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExpenseDTO> partialUpdateExpense(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExpenseDTO expenseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Expense partially : {}, {}", id, expenseDTO);
        if (expenseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, expenseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!expenseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExpenseDTO> result = expenseService.partialUpdate(expenseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, expenseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /expenses} : get all the expenses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expenses in body.
     */
    @GetMapping("/expenses")
    public ResponseEntity<List<ExpenseDTO>> getAllExpenses(
        ExpenseCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Expenses by criteria: {}", criteria);
        Page<ExpenseDTO> page = expenseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /expenses/count} : count all the expenses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/expenses/count")
    public ResponseEntity<Long> countExpenses(ExpenseCriteria criteria) {
        log.debug("REST request to count Expenses by criteria: {}", criteria);
        return ResponseEntity.ok().body(expenseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /expenses/:id} : get the "id" expense.
     *
     * @param id the id of the expenseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the expenseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/expenses/{id}")
    public ResponseEntity<ExpenseDTO> getExpense(@PathVariable Long id) {
        log.debug("REST request to get Expense : {}", id);
        Optional<ExpenseDTO> expenseDTO = expenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expenseDTO);
    }

    /**
     * {@code DELETE  /expenses/:id} : delete the "id" expense.
     *
     * @param id the id of the expenseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        log.debug("REST request to delete Expense : {}", id);
        expenseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping(value = "/public/expenses/xlsx/criteria/", produces = "application/vnd.ms-excel")
    public ResponseEntity<byte[]> getExpensesAsXSLXByCriteria(ExpenseCriteria criteria) {
        log.debug("REST request to get xslx");

        String[] columns = {
            "Id", "Date", "Details", "Amount", "Expense Type", "Employee"
        };

        List<ExpenseDTO> expenseDTOList = expenseQueryService.findByCriteria(criteria);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("expenses");

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

        for (ExpenseDTO expenseDTO : expenseDTOList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(expenseDTO.getId());
            row.createCell(1).setCellValue(expenseDTO.getDateAt().toString());
            row.createCell(2).setCellValue(expenseDTO.getDetails());
            row.createCell(3).setCellValue(expenseDTO.getAmount());
            row.createCell(4).setCellValue(expenseDTO.getExpenseType().toString());
            row.createCell(5).setCellValue(expenseDTO.getEmployee() != null ? expenseDTO.getEmployee().getName() + " - " + expenseDTO.getEmployee().getJobTitle() : "");
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
