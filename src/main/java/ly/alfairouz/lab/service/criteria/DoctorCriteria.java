package ly.alfairouz.lab.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import ly.alfairouz.lab.domain.enumeration.DoctorType;
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
 * Criteria class for the {@link ly.alfairouz.lab.domain.Doctor} entity. This class is used
 * in {@link ly.alfairouz.lab.web.rest.DoctorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /doctors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class DoctorCriteria implements Serializable, Criteria {

    /**
     * Class for filtering DoctorType
     */
    public static class DoctorTypeFilter extends Filter<DoctorType> {

        public DoctorTypeFilter() {}

        public DoctorTypeFilter(DoctorTypeFilter filter) {
            super(filter);
        }

        @Override
        public DoctorTypeFilter copy() {
            return new DoctorTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter nameAr;

    private StringFilter description;

    private StringFilter mobileNo;

    private StringFilter email;

    private BooleanFilter onlineReport;

    private BooleanFilter emailReport;

    private FloatFilter percentage;

    private DoctorTypeFilter doctorType;

    private LongFilter internalUserId;

    private Boolean distinct;

    public DoctorCriteria() {}

    public DoctorCriteria(DoctorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.nameAr = other.nameAr == null ? null : other.nameAr.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.mobileNo = other.mobileNo == null ? null : other.mobileNo.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.onlineReport = other.onlineReport == null ? null : other.onlineReport.copy();
        this.emailReport = other.emailReport == null ? null : other.emailReport.copy();
        this.percentage = other.percentage == null ? null : other.percentage.copy();
        this.doctorType = other.doctorType == null ? null : other.doctorType.copy();
        this.internalUserId = other.internalUserId == null ? null : other.internalUserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DoctorCriteria copy() {
        return new DoctorCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getNameAr() {
        return nameAr;
    }

    public StringFilter nameAr() {
        if (nameAr == null) {
            nameAr = new StringFilter();
        }
        return nameAr;
    }

    public void setNameAr(StringFilter nameAr) {
        this.nameAr = nameAr;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getMobileNo() {
        return mobileNo;
    }

    public StringFilter mobileNo() {
        if (mobileNo == null) {
            mobileNo = new StringFilter();
        }
        return mobileNo;
    }

    public void setMobileNo(StringFilter mobileNo) {
        this.mobileNo = mobileNo;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public BooleanFilter getOnlineReport() {
        return onlineReport;
    }

    public BooleanFilter onlineReport() {
        if (onlineReport == null) {
            onlineReport = new BooleanFilter();
        }
        return onlineReport;
    }

    public void setOnlineReport(BooleanFilter onlineReport) {
        this.onlineReport = onlineReport;
    }

    public BooleanFilter getEmailReport() {
        return emailReport;
    }

    public BooleanFilter emailReport() {
        if (emailReport == null) {
            emailReport = new BooleanFilter();
        }
        return emailReport;
    }

    public void setEmailReport(BooleanFilter emailReport) {
        this.emailReport = emailReport;
    }

    public FloatFilter getPercentage() {
        return percentage;
    }

    public FloatFilter percentage() {
        if (percentage == null) {
            percentage = new FloatFilter();
        }
        return percentage;
    }

    public void setPercentage(FloatFilter percentage) {
        this.percentage = percentage;
    }

    public DoctorTypeFilter getDoctorType() {
        return doctorType;
    }

    public DoctorTypeFilter doctorType() {
        if (doctorType == null) {
            doctorType = new DoctorTypeFilter();
        }
        return doctorType;
    }

    public void setDoctorType(DoctorTypeFilter doctorType) {
        this.doctorType = doctorType;
    }

    public LongFilter getInternalUserId() {
        return internalUserId;
    }

    public LongFilter internalUserId() {
        if (internalUserId == null) {
            internalUserId = new LongFilter();
        }
        return internalUserId;
    }

    public void setInternalUserId(LongFilter internalUserId) {
        this.internalUserId = internalUserId;
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
        final DoctorCriteria that = (DoctorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(nameAr, that.nameAr) &&
            Objects.equals(description, that.description) &&
            Objects.equals(mobileNo, that.mobileNo) &&
            Objects.equals(email, that.email) &&
            Objects.equals(onlineReport, that.onlineReport) &&
            Objects.equals(emailReport, that.emailReport) &&
            Objects.equals(percentage, that.percentage) &&
            Objects.equals(doctorType, that.doctorType) &&
            Objects.equals(internalUserId, that.internalUserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            nameAr,
            description,
            mobileNo,
            email,
            onlineReport,
            emailReport,
            percentage,
            doctorType,
            internalUserId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoctorCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (nameAr != null ? "nameAr=" + nameAr + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (mobileNo != null ? "mobileNo=" + mobileNo + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (onlineReport != null ? "onlineReport=" + onlineReport + ", " : "") +
            (emailReport != null ? "emailReport=" + emailReport + ", " : "") +
            (percentage != null ? "percentage=" + percentage + ", " : "") +
            (doctorType != null ? "doctorType=" + doctorType + ", " : "") +
            (internalUserId != null ? "internalUserId=" + internalUserId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
