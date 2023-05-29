package ly.alfairouz.lab.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import ly.alfairouz.lab.domain.enumeration.ContractType;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ly.alfairouz.lab.domain.ReferringCenterPrice} entity. This class is used
 * in {@link ly.alfairouz.lab.web.rest.ReferringCenterPriceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /referring-center-prices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ReferringCenterPriceCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ContractType
     */
    public static class ContractTypeFilter extends Filter<ContractType> {

        public ContractTypeFilter() {
        }

        public ContractTypeFilter(ContractTypeFilter filter) {
            super(filter);
        }

        @Override
        public ContractTypeFilter copy() {
            return new ContractTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ContractTypeFilter pricingType;

    private FloatFilter price;

    private LongFilter specimenTypeId;

    private LongFilter sizeId;

    private LongFilter referringCenterId;

    private Boolean distinct;

    public ReferringCenterPriceCriteria() {
    }

    public ReferringCenterPriceCriteria(ReferringCenterPriceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.pricingType = other.pricingType == null ? null : other.pricingType.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.specimenTypeId = other.specimenTypeId == null ? null : other.specimenTypeId.copy();
        this.sizeId = other.sizeId == null ? null : other.sizeId.copy();
        this.referringCenterId = other.referringCenterId == null ? null : other.referringCenterId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ReferringCenterPriceCriteria copy() {
        return new ReferringCenterPriceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ContractTypeFilter getPricingType() {
        return pricingType;
    }

    public ContractTypeFilter pricingType() {
        if (pricingType == null) {
            pricingType = new ContractTypeFilter();
        }
        return pricingType;
    }

    public void setPricingType(ContractTypeFilter pricingType) {
        this.pricingType = pricingType;
    }

    public FloatFilter getPrice() {
        return price;
    }

    public FloatFilter price() {
        if (price == null) {
            price = new FloatFilter();
        }
        return price;
    }

    public void setPrice(FloatFilter price) {
        this.price = price;
    }

    public LongFilter getSpecimenTypeId() {
        return specimenTypeId;
    }

    public LongFilter specimenTypeId() {
        if (specimenTypeId == null) {
            specimenTypeId = new LongFilter();
        }
        return specimenTypeId;
    }

    public void setSpecimenTypeId(LongFilter specimenTypeId) {
        this.specimenTypeId = specimenTypeId;
    }

    public LongFilter getSizeId() {
        return sizeId;
    }

    public LongFilter sizeId() {
        if (sizeId == null) {
            sizeId = new LongFilter();
        }
        return sizeId;
    }

    public void setSizeId(LongFilter sizeId) {
        this.sizeId = sizeId;
    }

    public LongFilter getReferringCenterId() {
        return referringCenterId;
    }

    public LongFilter referringCenterId() {
        if (referringCenterId == null) {
            referringCenterId = new LongFilter();
        }
        return referringCenterId;
    }

    public void setReferringCenterId(LongFilter referringCenterId) {
        this.referringCenterId = referringCenterId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ReferringCenterPriceCriteria that = (ReferringCenterPriceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
                Objects.equals(pricingType, that.pricingType) &&
                Objects.equals(price, that.price) &&
                Objects.equals(specimenTypeId, that.specimenTypeId) &&
                Objects.equals(sizeId, that.sizeId) &&
                Objects.equals(referringCenterId, that.referringCenterId) &&
                Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pricingType, price, specimenTypeId, sizeId, referringCenterId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReferringCenterPriceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (pricingType != null ? "pricingType=" + pricingType + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (specimenTypeId != null ? "specimenTypeId=" + specimenTypeId + ", " : "") +
            (sizeId != null ? "sizeId=" + sizeId + ", " : "") +
            (referringCenterId != null ? "referringCenterId=" + referringCenterId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
