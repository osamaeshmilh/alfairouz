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
 * Criteria class for the {@link ly.alfairouz.lab.domain.RepresentativeDelivery} entity. This class is used
 * in {@link ly.alfairouz.lab.web.rest.RepresentativeDeliveryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /representative-deliveries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class RepresentativeDeliveryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dateAt;

    private StringFilter details;

    private FloatFilter total;

    private Boolean distinct;

    public RepresentativeDeliveryCriteria() {}

    public RepresentativeDeliveryCriteria(RepresentativeDeliveryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateAt = other.dateAt == null ? null : other.dateAt.copy();
        this.details = other.details == null ? null : other.details.copy();
        this.total = other.total == null ? null : other.total.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RepresentativeDeliveryCriteria copy() {
        return new RepresentativeDeliveryCriteria(this);
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
        final RepresentativeDeliveryCriteria that = (RepresentativeDeliveryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dateAt, that.dateAt) &&
            Objects.equals(details, that.details) &&
            Objects.equals(total, that.total) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateAt, details, total, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RepresentativeDeliveryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dateAt != null ? "dateAt=" + dateAt + ", " : "") +
            (details != null ? "details=" + details + ", " : "") +
            (total != null ? "total=" + total + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
