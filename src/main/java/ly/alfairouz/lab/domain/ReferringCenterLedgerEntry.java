package ly.alfairouz.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import ly.alfairouz.lab.domain.enumeration.ReferringCenterLedgerEntryType;

/**
 * Manual ledger entry for referring-center finances.
 *
 * Monthly specimen debit rows are derived from Specimen and are not duplicated here.
 */
@Entity
@Table(name = "referring_center_ledger_entry")
public class ReferringCenterLedgerEntry extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "entry_type")
    private ReferringCenterLedgerEntryType entryType;

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_reference")
    private String paymentReference;

    @Column(name = "notes")
    private String notes;

    @Column(name = "proof_file_url")
    private String proofFileUrl;

    @Column(name = "proof_file_content_type")
    private String proofFileContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "internalUser" }, allowSetters = true)
    private ReferringCenter referringCenter;

    public Long getId() {
        return this.id;
    }

    public ReferringCenterLedgerEntry id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReferringCenterLedgerEntryType getEntryType() {
        return this.entryType;
    }

    public ReferringCenterLedgerEntry entryType(ReferringCenterLedgerEntryType entryType) {
        this.setEntryType(entryType);
        return this;
    }

    public void setEntryType(ReferringCenterLedgerEntryType entryType) {
        this.entryType = entryType;
    }

    public LocalDate getEntryDate() {
        return this.entryDate;
    }

    public ReferringCenterLedgerEntry entryDate(LocalDate entryDate) {
        this.setEntryDate(entryDate);
        return this;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public ReferringCenterLedgerEntry amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public ReferringCenterLedgerEntry paymentMethod(String paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentReference() {
        return this.paymentReference;
    }

    public ReferringCenterLedgerEntry paymentReference(String paymentReference) {
        this.setPaymentReference(paymentReference);
        return this;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getNotes() {
        return this.notes;
    }

    public ReferringCenterLedgerEntry notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getProofFileUrl() {
        return this.proofFileUrl;
    }

    public ReferringCenterLedgerEntry proofFileUrl(String proofFileUrl) {
        this.setProofFileUrl(proofFileUrl);
        return this;
    }

    public void setProofFileUrl(String proofFileUrl) {
        this.proofFileUrl = proofFileUrl;
    }

    public String getProofFileContentType() {
        return this.proofFileContentType;
    }

    public ReferringCenterLedgerEntry proofFileContentType(String proofFileContentType) {
        this.setProofFileContentType(proofFileContentType);
        return this;
    }

    public void setProofFileContentType(String proofFileContentType) {
        this.proofFileContentType = proofFileContentType;
    }

    public ReferringCenter getReferringCenter() {
        return this.referringCenter;
    }

    public ReferringCenterLedgerEntry referringCenter(ReferringCenter referringCenter) {
        this.setReferringCenter(referringCenter);
        return this;
    }

    public void setReferringCenter(ReferringCenter referringCenter) {
        this.referringCenter = referringCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReferringCenterLedgerEntry)) {
            return false;
        }
        return id != null && id.equals(((ReferringCenterLedgerEntry) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReferringCenterLedgerEntry{" +
            "id=" + getId() +
            ", entryType='" + getEntryType() + "'" +
            ", entryDate='" + getEntryDate() + "'" +
            ", amount=" + getAmount() +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", paymentReference='" + getPaymentReference() + "'" +
            ", proofFileUrl='" + getProofFileUrl() + "'" +
            "}";
    }
}
