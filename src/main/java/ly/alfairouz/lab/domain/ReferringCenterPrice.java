package ly.alfairouz.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import javax.persistence.*;

import ly.alfairouz.lab.domain.enumeration.ContractType;

/**
 * A ReferringCenterPrice.
 */
@Entity
@Table(name = "referring_center_price")
public class ReferringCenterPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "pricing_type")
    private ContractType pricingType;

    @Column(name = "price")
    private Float price;

    @ManyToOne
    private SpecimenType specimenType;

    @ManyToOne
    private Size size;

    @ManyToOne
    @JsonIgnoreProperties(value = {"internalUser"}, allowSetters = true)
    private ReferringCenter referringCenter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReferringCenterPrice id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContractType getPricingType() {
        return this.pricingType;
    }

    public ReferringCenterPrice pricingType(ContractType pricingType) {
        this.setPricingType(pricingType);
        return this;
    }

    public void setPricingType(ContractType pricingType) {
        this.pricingType = pricingType;
    }

    public Float getPrice() {
        return this.price;
    }

    public ReferringCenterPrice price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public SpecimenType getSpecimenType() {
        return this.specimenType;
    }

    public void setSpecimenType(SpecimenType specimenType) {
        this.specimenType = specimenType;
    }

    public ReferringCenterPrice specimenType(SpecimenType specimenType) {
        this.setSpecimenType(specimenType);
        return this;
    }

    public Size getSize() {
        return this.size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public ReferringCenterPrice size(Size size) {
        this.setSize(size);
        return this;
    }

    public ReferringCenter getReferringCenter() {
        return this.referringCenter;
    }

    public void setReferringCenter(ReferringCenter referringCenter) {
        this.referringCenter = referringCenter;
    }

    public ReferringCenterPrice referringCenter(ReferringCenter referringCenter) {
        this.setReferringCenter(referringCenter);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReferringCenterPrice)) {
            return false;
        }
        return id != null && id.equals(((ReferringCenterPrice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReferringCenterPrice{" +
            "id=" + getId() +
            ", pricingType='" + getPricingType() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
