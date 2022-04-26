package ly.alfairouz.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import ly.alfairouz.lab.domain.enumeration.PaymentType;

/**
 * A PapSmearSale.
 */
@Entity
@Table(name = "pap_smear_sale")
public class PapSmearSale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_at")
    private LocalDate dateAt;

    @Column(name = "details")
    private String details;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total")
    private Float total;

    @ManyToOne
    @JsonIgnoreProperties(value = { "internalUser" }, allowSetters = true)
    private ReferringCenter referringCenter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PapSmearSale id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateAt() {
        return this.dateAt;
    }

    public PapSmearSale dateAt(LocalDate dateAt) {
        this.setDateAt(dateAt);
        return this;
    }

    public void setDateAt(LocalDate dateAt) {
        this.dateAt = dateAt;
    }

    public String getDetails() {
        return this.details;
    }

    public PapSmearSale details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public PaymentType getPaymentType() {
        return this.paymentType;
    }

    public PapSmearSale paymentType(PaymentType paymentType) {
        this.setPaymentType(paymentType);
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public PapSmearSale quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getTotal() {
        return this.total;
    }

    public PapSmearSale total(Float total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public ReferringCenter getReferringCenter() {
        return this.referringCenter;
    }

    public void setReferringCenter(ReferringCenter referringCenter) {
        this.referringCenter = referringCenter;
    }

    public PapSmearSale referringCenter(ReferringCenter referringCenter) {
        this.setReferringCenter(referringCenter);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PapSmearSale)) {
            return false;
        }
        return id != null && id.equals(((PapSmearSale) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PapSmearSale{" +
            "id=" + getId() +
            ", dateAt='" + getDateAt() + "'" +
            ", details='" + getDetails() + "'" +
            ", paymentType='" + getPaymentType() + "'" +
            ", quantity=" + getQuantity() +
            ", total=" + getTotal() +
            "}";
    }
}
