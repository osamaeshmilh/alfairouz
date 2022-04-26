package ly.alfairouz.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import ly.alfairouz.lab.domain.enumeration.WithdrawType;

/**
 * A BlockWithdraw.
 */
@Entity
@Table(name = "block_withdraw")
public class BlockWithdraw implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "person_name")
    private String personName;

    @Column(name = "person_id")
    private String personId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "withdraw_date")
    private LocalDate withdrawDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "withdraw_type")
    private WithdrawType withdrawType;

    @Lob
    @Column(name = "pdf_file")
    private byte[] pdfFile;

    @Column(name = "pdf_file_content_type")
    private String pdfFileContentType;

    @Column(name = "pdf_file_url")
    private String pdfFileUrl;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "patient",
            "biopsy",
            "cytology",
            "organ",
            "specimenType",
            "size",
            "referringCenter",
            "grossingDoctor",
            "referringDoctor",
            "pathologistDoctor",
            "operatorEmployee",
            "correctorEmployee",
        },
        allowSetters = true
    )
    private Specimen specimen;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BlockWithdraw id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonName() {
        return this.personName;
    }

    public BlockWithdraw personName(String personName) {
        this.setPersonName(personName);
        return this;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonId() {
        return this.personId;
    }

    public BlockWithdraw personId(String personId) {
        this.setPersonId(personId);
        return this;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public BlockWithdraw quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getWithdrawDate() {
        return this.withdrawDate;
    }

    public BlockWithdraw withdrawDate(LocalDate withdrawDate) {
        this.setWithdrawDate(withdrawDate);
        return this;
    }

    public void setWithdrawDate(LocalDate withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    public WithdrawType getWithdrawType() {
        return this.withdrawType;
    }

    public BlockWithdraw withdrawType(WithdrawType withdrawType) {
        this.setWithdrawType(withdrawType);
        return this;
    }

    public void setWithdrawType(WithdrawType withdrawType) {
        this.withdrawType = withdrawType;
    }

    public byte[] getPdfFile() {
        return this.pdfFile;
    }

    public BlockWithdraw pdfFile(byte[] pdfFile) {
        this.setPdfFile(pdfFile);
        return this;
    }

    public void setPdfFile(byte[] pdfFile) {
        this.pdfFile = pdfFile;
    }

    public String getPdfFileContentType() {
        return this.pdfFileContentType;
    }

    public BlockWithdraw pdfFileContentType(String pdfFileContentType) {
        this.pdfFileContentType = pdfFileContentType;
        return this;
    }

    public void setPdfFileContentType(String pdfFileContentType) {
        this.pdfFileContentType = pdfFileContentType;
    }

    public String getPdfFileUrl() {
        return this.pdfFileUrl;
    }

    public BlockWithdraw pdfFileUrl(String pdfFileUrl) {
        this.setPdfFileUrl(pdfFileUrl);
        return this;
    }

    public void setPdfFileUrl(String pdfFileUrl) {
        this.pdfFileUrl = pdfFileUrl;
    }

    public Specimen getSpecimen() {
        return this.specimen;
    }

    public void setSpecimen(Specimen specimen) {
        this.specimen = specimen;
    }

    public BlockWithdraw specimen(Specimen specimen) {
        this.setSpecimen(specimen);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlockWithdraw)) {
            return false;
        }
        return id != null && id.equals(((BlockWithdraw) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlockWithdraw{" +
            "id=" + getId() +
            ", personName='" + getPersonName() + "'" +
            ", personId='" + getPersonId() + "'" +
            ", quantity=" + getQuantity() +
            ", withdrawDate='" + getWithdrawDate() + "'" +
            ", withdrawType='" + getWithdrawType() + "'" +
            ", pdfFile='" + getPdfFile() + "'" +
            ", pdfFileContentType='" + getPdfFileContentType() + "'" +
            ", pdfFileUrl='" + getPdfFileUrl() + "'" +
            "}";
    }
}
