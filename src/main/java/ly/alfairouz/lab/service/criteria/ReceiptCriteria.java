package ly.alfairouz.lab.service.criteria;

import java.io.Serializable;
import java.util.Objects;
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
 * Criteria class for the {@link ly.alfairouz.lab.domain.Receipt} entity. This class is used
 * in {@link ly.alfairouz.lab.web.rest.ReceiptResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /receipts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ReceiptCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dateAt;

    private StringFilter details;

    private StringFilter paymentMethod;

    private FloatFilter price;

    private FloatFilter paid;

    private FloatFilter notPaid;

    private LongFilter specimenId;

    private LongFilter patientId;

    private Boolean distinct;

    public ReceiptCriteria() {}

    public ReceiptCriteria(ReceiptCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateAt = other.dateAt == null ? null : other.dateAt.copy();
        this.details = other.details == null ? null : other.details.copy();
        this.paymentMethod = other.paymentMethod == null ? null : other.paymentMethod.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.paid = other.paid == null ? null : other.paid.copy();
        this.notPaid = other.notPaid == null ? null : other.notPaid.copy();
        this.specimenId = other.specimenId == null ? null : other.specimenId.copy();
        this.patientId = other.patientId == null ? null : other.patientId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ReceiptCriteria copy() {
        return new ReceiptCriteria(this);
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

    public StringFilter getPaymentMethod() {
        return paymentMethod;
    }

    public StringFilter paymentMethod() {
        if (paymentMethod == null) {
            paymentMethod = new StringFilter();
        }
        return paymentMethod;
    }

    public void setPaymentMethod(StringFilter paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    public FloatFilter getPaid() {
        return paid;
    }

    public FloatFilter paid() {
        if (paid == null) {
            paid = new FloatFilter();
        }
        return paid;
    }

    public void setPaid(FloatFilter paid) {
        this.paid = paid;
    }

    public FloatFilter getNotPaid() {
        return notPaid;
    }

    public FloatFilter notPaid() {
        if (notPaid == null) {
            notPaid = new FloatFilter();
        }
        return notPaid;
    }

    public void setNotPaid(FloatFilter notPaid) {
        this.notPaid = notPaid;
    }

    public LongFilter getSpecimenId() {
        return specimenId;
    }

    public LongFilter specimenId() {
        if (specimenId == null) {
            specimenId = new LongFilter();
        }
        return specimenId;
    }

    public void setSpecimenId(LongFilter specimenId) {
        this.specimenId = specimenId;
    }

    public LongFilter getPatientId() {
        return patientId;
    }

    public LongFilter patientId() {
        if (patientId == null) {
            patientId = new LongFilter();
        }
        return patientId;
    }

    public void setPatientId(LongFilter patientId) {
        this.patientId = patientId;
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
        final ReceiptCriteria that = (ReceiptCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dateAt, that.dateAt) &&
            Objects.equals(details, that.details) &&
            Objects.equals(paymentMethod, that.paymentMethod) &&
            Objects.equals(price, that.price) &&
            Objects.equals(paid, that.paid) &&
            Objects.equals(notPaid, that.notPaid) &&
            Objects.equals(specimenId, that.specimenId) &&
            Objects.equals(patientId, that.patientId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateAt, details, paymentMethod, price, paid, notPaid, specimenId, patientId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReceiptCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dateAt != null ? "dateAt=" + dateAt + ", " : "") +
            (details != null ? "details=" + details + ", " : "") +
            (paymentMethod != null ? "paymentMethod=" + paymentMethod + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (paid != null ? "paid=" + paid + ", " : "") +
            (notPaid != null ? "notPaid=" + notPaid + ", " : "") +
            (specimenId != null ? "specimenId=" + specimenId + ", " : "") +
            (patientId != null ? "patientId=" + patientId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
