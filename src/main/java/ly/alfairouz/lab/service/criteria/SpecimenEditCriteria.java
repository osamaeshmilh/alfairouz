package ly.alfairouz.lab.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import ly.alfairouz.lab.domain.enumeration.SpecimenStatus;
import ly.alfairouz.lab.domain.enumeration.SpecimenStatus;
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
 * Criteria class for the {@link ly.alfairouz.lab.domain.SpecimenEdit} entity. This class is used
 * in {@link ly.alfairouz.lab.web.rest.SpecimenEditResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /specimen-edits?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class SpecimenEditCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SpecimenStatus
     */
    public static class SpecimenStatusFilter extends Filter<SpecimenStatus> {

        public SpecimenStatusFilter() {
        }

        public SpecimenStatusFilter(SpecimenStatusFilter filter) {
            super(filter);
        }

        @Override
        public SpecimenStatusFilter copy() {
            return new SpecimenStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter specimenId;

    private StringFilter labRefNo;

    private SpecimenStatusFilter specimenStatusFrom;

    private SpecimenStatusFilter specimenStatusTo;

    private StringFilter userType;

    private Boolean distinct;

    public SpecimenEditCriteria() {
    }

    public SpecimenEditCriteria(SpecimenEditCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.specimenId = other.specimenId == null ? null : other.specimenId.copy();
        this.labRefNo = other.labRefNo == null ? null : other.labRefNo.copy();
        this.specimenStatusFrom = other.specimenStatusFrom == null ? null : other.specimenStatusFrom.copy();
        this.specimenStatusTo = other.specimenStatusTo == null ? null : other.specimenStatusTo.copy();
        this.userType = other.userType == null ? null : other.userType.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SpecimenEditCriteria copy() {
        return new SpecimenEditCriteria(this);
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

    public StringFilter getLabRefNo() {
        return labRefNo;
    }

    public StringFilter labRefNo() {
        if (labRefNo == null) {
            labRefNo = new StringFilter();
        }
        return labRefNo;
    }

    public void setLabRefNo(StringFilter labRefNo) {
        this.labRefNo = labRefNo;
    }

    public SpecimenStatusFilter getSpecimenStatusFrom() {
        return specimenStatusFrom;
    }

    public SpecimenStatusFilter specimenStatusFrom() {
        if (specimenStatusFrom == null) {
            specimenStatusFrom = new SpecimenStatusFilter();
        }
        return specimenStatusFrom;
    }

    public void setSpecimenStatusFrom(SpecimenStatusFilter specimenStatusFrom) {
        this.specimenStatusFrom = specimenStatusFrom;
    }

    public SpecimenStatusFilter getSpecimenStatusTo() {
        return specimenStatusTo;
    }

    public SpecimenStatusFilter specimenStatusTo() {
        if (specimenStatusTo == null) {
            specimenStatusTo = new SpecimenStatusFilter();
        }
        return specimenStatusTo;
    }

    public void setSpecimenStatusTo(SpecimenStatusFilter specimenStatusTo) {
        this.specimenStatusTo = specimenStatusTo;
    }

    public StringFilter getUserType() {
        return userType;
    }

    public StringFilter userType() {
        if (userType == null) {
            userType = new StringFilter();
        }
        return userType;
    }

    public void setUserType(StringFilter userType) {
        this.userType = userType;
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
        final SpecimenEditCriteria that = (SpecimenEditCriteria) o;
        return (
            Objects.equals(id, that.id) &&
                Objects.equals(specimenId, that.specimenId) &&
                Objects.equals(labRefNo, that.labRefNo) &&
                Objects.equals(specimenStatusFrom, that.specimenStatusFrom) &&
                Objects.equals(specimenStatusTo, that.specimenStatusTo) &&
                Objects.equals(userType, that.userType) &&
                Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, specimenId, labRefNo, specimenStatusFrom, specimenStatusTo, userType, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpecimenEditCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (specimenId != null ? "specimenId=" + specimenId + ", " : "") +
            (labRefNo != null ? "labRefNo=" + labRefNo + ", " : "") +
            (specimenStatusFrom != null ? "specimenStatusFrom=" + specimenStatusFrom + ", " : "") +
            (specimenStatusTo != null ? "specimenStatusTo=" + specimenStatusTo + ", " : "") +
            (userType != null ? "userType=" + userType + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
