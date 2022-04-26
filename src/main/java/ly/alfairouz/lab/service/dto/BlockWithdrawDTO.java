package ly.alfairouz.lab.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import ly.alfairouz.lab.domain.enumeration.WithdrawType;

/**
 * A DTO for the {@link ly.alfairouz.lab.domain.BlockWithdraw} entity.
 */
public class BlockWithdrawDTO implements Serializable {

    private Long id;

    private String personName;

    private String personId;

    private Integer quantity;

    private LocalDate withdrawDate;

    private WithdrawType withdrawType;

    @Lob
    private byte[] pdfFile;

    private String pdfFileContentType;
    private String pdfFileUrl;

    private SpecimenDTO specimen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getWithdrawDate() {
        return withdrawDate;
    }

    public void setWithdrawDate(LocalDate withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    public WithdrawType getWithdrawType() {
        return withdrawType;
    }

    public void setWithdrawType(WithdrawType withdrawType) {
        this.withdrawType = withdrawType;
    }

    public byte[] getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(byte[] pdfFile) {
        this.pdfFile = pdfFile;
    }

    public String getPdfFileContentType() {
        return pdfFileContentType;
    }

    public void setPdfFileContentType(String pdfFileContentType) {
        this.pdfFileContentType = pdfFileContentType;
    }

    public String getPdfFileUrl() {
        return pdfFileUrl;
    }

    public void setPdfFileUrl(String pdfFileUrl) {
        this.pdfFileUrl = pdfFileUrl;
    }

    public SpecimenDTO getSpecimen() {
        return specimen;
    }

    public void setSpecimen(SpecimenDTO specimen) {
        this.specimen = specimen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlockWithdrawDTO)) {
            return false;
        }

        BlockWithdrawDTO blockWithdrawDTO = (BlockWithdrawDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, blockWithdrawDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlockWithdrawDTO{" +
            "id=" + getId() +
            ", personName='" + getPersonName() + "'" +
            ", personId='" + getPersonId() + "'" +
            ", quantity=" + getQuantity() +
            ", withdrawDate='" + getWithdrawDate() + "'" +
            ", withdrawType='" + getWithdrawType() + "'" +
            ", pdfFile='" + getPdfFile() + "'" +
            ", pdfFileUrl='" + getPdfFileUrl() + "'" +
            ", specimen=" + getSpecimen() +
            "}";
    }
}
