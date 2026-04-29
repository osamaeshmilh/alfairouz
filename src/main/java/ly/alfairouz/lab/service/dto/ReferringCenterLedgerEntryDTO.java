package ly.alfairouz.lab.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import ly.alfairouz.lab.domain.enumeration.ReferringCenterLedgerEntryType;

/**
 * DTO for referring-center ledger rows.
 */
public class ReferringCenterLedgerEntryDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private String source;

    private ReferringCenterLedgerEntryType entryType;

    private LocalDate entryDate;

    private BigDecimal amount;

    private BigDecimal debit;

    private BigDecimal credit;

    private BigDecimal runningBalance;

    private String description;

    private String paymentMethod;

    private String paymentReference;

    private String notes;

    private byte[] proofFile;

    private String proofFileContentType;

    private String proofFileUrl;

    private Long referringCenterId;

    private Long specimenId;

    private String labRefNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ReferringCenterLedgerEntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(ReferringCenterLedgerEntryType entryType) {
        this.entryType = entryType;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getRunningBalance() {
        return runningBalance;
    }

    public void setRunningBalance(BigDecimal runningBalance) {
        this.runningBalance = runningBalance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public byte[] getProofFile() {
        return proofFile;
    }

    public void setProofFile(byte[] proofFile) {
        this.proofFile = proofFile;
    }

    public String getProofFileContentType() {
        return proofFileContentType;
    }

    public void setProofFileContentType(String proofFileContentType) {
        this.proofFileContentType = proofFileContentType;
    }

    public String getProofFileUrl() {
        return proofFileUrl;
    }

    public void setProofFileUrl(String proofFileUrl) {
        this.proofFileUrl = proofFileUrl;
    }

    public Long getReferringCenterId() {
        return referringCenterId;
    }

    public void setReferringCenterId(Long referringCenterId) {
        this.referringCenterId = referringCenterId;
    }

    public Long getSpecimenId() {
        return specimenId;
    }

    public void setSpecimenId(Long specimenId) {
        this.specimenId = specimenId;
    }

    public String getLabRefNo() {
        return labRefNo;
    }

    public void setLabRefNo(String labRefNo) {
        this.labRefNo = labRefNo;
    }
}
