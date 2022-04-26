package ly.alfairouz.lab.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import ly.alfairouz.lab.domain.enumeration.PaymentType;

/**
 * A DTO for the {@link ly.alfairouz.lab.domain.PapSmearSale} entity.
 */
public class PapSmearSaleDTO implements Serializable {

    private Long id;

    private LocalDate dateAt;

    private String details;

    private PaymentType paymentType;

    private Integer quantity;

    private Float total;

    private ReferringCenterDTO referringCenter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateAt() {
        return dateAt;
    }

    public void setDateAt(LocalDate dateAt) {
        this.dateAt = dateAt;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public ReferringCenterDTO getReferringCenter() {
        return referringCenter;
    }

    public void setReferringCenter(ReferringCenterDTO referringCenter) {
        this.referringCenter = referringCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PapSmearSaleDTO)) {
            return false;
        }

        PapSmearSaleDTO papSmearSaleDTO = (PapSmearSaleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, papSmearSaleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PapSmearSaleDTO{" +
            "id=" + getId() +
            ", dateAt='" + getDateAt() + "'" +
            ", details='" + getDetails() + "'" +
            ", paymentType='" + getPaymentType() + "'" +
            ", quantity=" + getQuantity() +
            ", total=" + getTotal() +
            ", referringCenter=" + getReferringCenter() +
            "}";
    }
}
