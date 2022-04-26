package ly.alfairouz.lab.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import ly.alfairouz.lab.domain.enumeration.ExpenseType;
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
 * Criteria class for the {@link ly.alfairouz.lab.domain.Expense} entity. This class is used
 * in {@link ly.alfairouz.lab.web.rest.ExpenseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /expenses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ExpenseCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ExpenseType
     */
    public static class ExpenseTypeFilter extends Filter<ExpenseType> {

        public ExpenseTypeFilter() {}

        public ExpenseTypeFilter(ExpenseTypeFilter filter) {
            super(filter);
        }

        @Override
        public ExpenseTypeFilter copy() {
            return new ExpenseTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dateAt;

    private StringFilter details;

    private FloatFilter amount;

    private ExpenseTypeFilter expenseType;

    private LongFilter employeeId;

    private Boolean distinct;

    public ExpenseCriteria() {}

    public ExpenseCriteria(ExpenseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateAt = other.dateAt == null ? null : other.dateAt.copy();
        this.details = other.details == null ? null : other.details.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.expenseType = other.expenseType == null ? null : other.expenseType.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ExpenseCriteria copy() {
        return new ExpenseCriteria(this);
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

    public FloatFilter getAmount() {
        return amount;
    }

    public FloatFilter amount() {
        if (amount == null) {
            amount = new FloatFilter();
        }
        return amount;
    }

    public void setAmount(FloatFilter amount) {
        this.amount = amount;
    }

    public ExpenseTypeFilter getExpenseType() {
        return expenseType;
    }

    public ExpenseTypeFilter expenseType() {
        if (expenseType == null) {
            expenseType = new ExpenseTypeFilter();
        }
        return expenseType;
    }

    public void setExpenseType(ExpenseTypeFilter expenseType) {
        this.expenseType = expenseType;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
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
        final ExpenseCriteria that = (ExpenseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dateAt, that.dateAt) &&
            Objects.equals(details, that.details) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(expenseType, that.expenseType) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateAt, details, amount, expenseType, employeeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExpenseCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dateAt != null ? "dateAt=" + dateAt + ", " : "") +
            (details != null ? "details=" + details + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (expenseType != null ? "expenseType=" + expenseType + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
