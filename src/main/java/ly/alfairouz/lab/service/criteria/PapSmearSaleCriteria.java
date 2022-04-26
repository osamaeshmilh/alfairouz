package ly.alfairouz.lab.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import ly.alfairouz.lab.domain.enumeration.PaymentType;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ly.alfairouz.lab.domain.PapSmearSale} entity. This class is used
 * in {@link ly.alfairouz.lab.web.rest.PapSmearSaleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pap-smear-sales?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PapSmearSaleCriteria implements Serializable, Criteria {

    /**
     * Class for filtering PaymentType
     */
    public static class PaymentTypeFilter extends Filter<PaymentType> {

        public PaymentTypeFilter() {}

        public PaymentTypeFilter(PaymentTypeFilter filter) {
            super(filter);
        }

        @Override
        public PaymentTypeFilter copy() {
            return new PaymentTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dateAt;

    private StringFilter details;

    private PaymentTypeFilter paymentType;

    private IntegerFilter quantity;

    private FloatFilter total;

    private LongFilter referringCenterId;

    private Boolean distinct;

    public PapSmearSaleCriteria() {}

    public PapSmearSaleCriteria(PapSmearSaleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateAt = other.dateAt == null ? null : other.dateAt.copy();
        this.details = other.details == null ? null : other.details.copy();
        this.paymentType = other.paymentType == null ? null : other.paymentType.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.total = other.total == null ? null : other.total.copy();
        this.referringCenterId = other.referringCenterId == null ? null : other.referringCenterId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PapSmearSaleCriteria copy() {
        return new PapSmearSaleCriteria(this);
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

    public LocalDateFilter getDateAt() {
        return dateAt;
    }

    public LocalDateFilter dateAt() {
        if (dateAt == null) {
            dateAt = new LocalDateFilter();
        }
        return dateAt;
    }

    public void setDateAt(LocalDateFilter dateAt) {
        this.dateAt = dateAt;
    }

    public StringFilter getDetails() {
        return details;
    }

    public StringFilter details() {
        if (details == null) {
            details = new StringFilter();
        }
        return details;
    }

    public void setDetails(StringFilter details) {
        this.details = details;
    }

    public PaymentTypeFilter getPaymentType() {
        return paymentType;
    }

    public PaymentTypeFilter paymentType() {
        if (paymentType == null) {
            paymentType = new PaymentTypeFilter();
        }
        return paymentType;
    }

    public void setPaymentType(PaymentTypeFilter paymentType) {
        this.paymentType = paymentType;
    }

    public IntegerFilter getQuantity() {
        return quantity;
    }

    public IntegerFilter quantity() {
        if (quantity == null) {
            quantity = new IntegerFilter();
        }
        return quantity;
    }

    public void setQuantity(IntegerFilter quantity) {
        this.quantity = quantity;
    }

    public FloatFilter getTotal() {
        return total;
    }

    public FloatFilter total() {
        if (total == null) {
            total = new FloatFilter();
        }
        return total;
    }

    public void setTotal(FloatFilter total) {
        this.total = total;
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
        final PapSmearSaleCriteria that = (PapSmearSaleCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dateAt, that.dateAt) &&
            Objects.equals(details, that.details) &&
            Objects.equals(paymentType, that.paymentType) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(total, that.total) &&
            Objects.equals(referringCenterId, that.referringCenterId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateAt, details, paymentType, quantity, total, referringCenterId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PapSmearSaleCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dateAt != null ? "dateAt=" + dateAt + ", " : "") +
            (details != null ? "details=" + details + ", " : "") +
            (paymentType != null ? "paymentType=" + paymentType + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            (total != null ? "total=" + total + ", " : "") +
            (referringCenterId != null ? "referringCenterId=" + referringCenterId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
