package ly.alfairouz.lab.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import ly.alfairouz.lab.domain.enumeration.WithdrawType;
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
 * Criteria class for the {@link ly.alfairouz.lab.domain.BlockWithdraw} entity. This class is used
 * in {@link ly.alfairouz.lab.web.rest.BlockWithdrawResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /block-withdraws?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class BlockWithdrawCriteria implements Serializable, Criteria {

    /**
     * Class for filtering WithdrawType
     */
    public static class WithdrawTypeFilter extends Filter<WithdrawType> {

        public WithdrawTypeFilter() {}

        public WithdrawTypeFilter(WithdrawTypeFilter filter) {
            super(filter);
        }

        @Override
        public WithdrawTypeFilter copy() {
            return new WithdrawTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter personName;

    private StringFilter personId;

    private IntegerFilter quantity;

    private LocalDateFilter withdrawDate;

    private WithdrawTypeFilter withdrawType;

    private StringFilter pdfFileUrl;

    private LongFilter specimenId;

    private Boolean distinct;

    public BlockWithdrawCriteria() {}

    public BlockWithdrawCriteria(BlockWithdrawCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.personName = other.personName == null ? null : other.personName.copy();
        this.personId = other.personId == null ? null : other.personId.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.withdrawDate = other.withdrawDate == null ? null : other.withdrawDate.copy();
        this.withdrawType = other.withdrawType == null ? null : other.withdrawType.copy();
        this.pdfFileUrl = other.pdfFileUrl == null ? null : other.pdfFileUrl.copy();
        this.specimenId = other.specimenId == null ? null : other.specimenId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BlockWithdrawCriteria copy() {
        return new BlockWithdrawCriteria(this);
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

    public StringFilter getPersonName() {
        return personName;
    }

    public StringFilter personName() {
        if (personName == null) {
            personName = new StringFilter();
        }
        return personName;
    }

    public void setPersonName(StringFilter personName) {
        this.personName = personName;
    }

    public StringFilter getPersonId() {
        return personId;
    }

    public StringFilter personId() {
        if (personId == null) {
            personId = new StringFilter();
        }
        return personId;
    }

    public void setPersonId(StringFilter personId) {
        this.personId = personId;
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

    public LocalDateFilter getWithdrawDate() {
        return withdrawDate;
    }

    public LocalDateFilter withdrawDate() {
        if (withdrawDate == null) {
            withdrawDate = new LocalDateFilter();
        }
        return withdrawDate;
    }

    public void setWithdrawDate(LocalDateFilter withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    public WithdrawTypeFilter getWithdrawType() {
        return withdrawType;
    }

    public WithdrawTypeFilter withdrawType() {
        if (withdrawType == null) {
            withdrawType = new WithdrawTypeFilter();
        }
        return withdrawType;
    }

    public void setWithdrawType(WithdrawTypeFilter withdrawType) {
        this.withdrawType = withdrawType;
    }

    public StringFilter getPdfFileUrl() {
        return pdfFileUrl;
    }

    public StringFilter pdfFileUrl() {
        if (pdfFileUrl == null) {
            pdfFileUrl = new StringFilter();
        }
        return pdfFileUrl;
    }

    public void setPdfFileUrl(StringFilter pdfFileUrl) {
        this.pdfFileUrl = pdfFileUrl;
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
        final BlockWithdrawCriteria that = (BlockWithdrawCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(personName, that.personName) &&
            Objects.equals(personId, that.personId) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(withdrawDate, that.withdrawDate) &&
            Objects.equals(withdrawType, that.withdrawType) &&
            Objects.equals(pdfFileUrl, that.pdfFileUrl) &&
            Objects.equals(specimenId, that.specimenId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personName, personId, quantity, withdrawDate, withdrawType, pdfFileUrl, specimenId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlockWithdrawCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (personName != null ? "personName=" + personName + ", " : "") +
            (personId != null ? "personId=" + personId + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            (withdrawDate != null ? "withdrawDate=" + withdrawDate + ", " : "") +
            (withdrawType != null ? "withdrawType=" + withdrawType + ", " : "") +
            (pdfFileUrl != null ? "pdfFileUrl=" + pdfFileUrl + ", " : "") +
            (specimenId != null ? "specimenId=" + specimenId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
