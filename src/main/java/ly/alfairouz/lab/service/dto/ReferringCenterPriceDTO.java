package ly.alfairouz.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;

import ly.alfairouz.lab.domain.enumeration.ContractType;

/**
 * A DTO for the {@link ly.alfairouz.lab.domain.ReferringCenterPrice} entity.
 */
public class ReferringCenterPriceDTO implements Serializable {

    private Long id;

    private ContractType pricingType;

    private Float price;

    private SpecimenTypeDTO specimenType;

    private SizeDTO size;

    private ReferringCenterDTO referringCenter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContractType getPricingType() {
        return pricingType;
    }

    public void setPricingType(ContractType pricingType) {
        this.pricingType = pricingType;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public SpecimenTypeDTO getSpecimenType() {
        return specimenType;
    }

    public void setSpecimenType(SpecimenTypeDTO specimenType) {
        this.specimenType = specimenType;
    }

    public SizeDTO getSize() {
        return size;
    }

    public void setSize(SizeDTO size) {
        this.size = size;
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
        if (!(o instanceof ReferringCenterPriceDTO)) {
            return false;
        }

        ReferringCenterPriceDTO referringCenterPriceDTO = (ReferringCenterPriceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, referringCenterPriceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReferringCenterPriceDTO{" +
            "id=" + getId() +
            ", pricingType='" + getPricingType() + "'" +
            ", price=" + getPrice() +
            ", specimenType=" + getSpecimenType() +
            ", size=" + getSize() +
            ", referringCenter=" + getReferringCenter() +
            "}";
    }
}
