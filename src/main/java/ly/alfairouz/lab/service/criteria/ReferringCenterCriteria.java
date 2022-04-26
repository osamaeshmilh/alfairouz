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
 * Criteria class for the {@link ly.alfairouz.lab.domain.ReferringCenter} entity. This class is used
 * in {@link ly.alfairouz.lab.web.rest.ReferringCenterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /referring-centers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ReferringCenterCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ContractType
     */
    public static class ContractTypeFilter extends Filter<ContractType> {

        public ContractTypeFilter() {}

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

    private StringFilter name;

    private StringFilter nameAr;

    private StringFilter mobileNumber;

    private StringFilter email;

    private BooleanFilter onlineReport;

    private ContractTypeFilter contractType;

    private FloatFilter discount;

    private LongFilter internalUserId;

    private Boolean distinct;

    public ReferringCenterCriteria() {}

    public ReferringCenterCriteria(ReferringCenterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.nameAr = other.nameAr == null ? null : other.nameAr.copy();
        this.mobileNumber = other.mobileNumber == null ? null : other.mobileNumber.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.onlineReport = other.onlineReport == null ? null : other.onlineReport.copy();
        this.contractType = other.contractType == null ? null : other.contractType.copy();
        this.discount = other.discount == null ? null : other.discount.copy();
        this.internalUserId = other.internalUserId == null ? null : other.internalUserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ReferringCenterCriteria copy() {
        return new ReferringCenterCriteria(this);
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

    public StringFilter getMobileNumber() {
        return mobileNumber;
    }

    public StringFilter mobileNumber() {
        if (mobileNumber == null) {
            mobileNumber = new StringFilter();
        }
        return mobileNumber;
    }

    public void setMobileNumber(StringFilter mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    public ContractTypeFilter getContractType() {
        return contractType;
    }

    public ContractTypeFilter contractType() {
        if (contractType == null) {
            contractType = new ContractTypeFilter();
        }
        return contractType;
    }

    public void setContractType(ContractTypeFilter contractType) {
        this.contractType = contractType;
    }

    public FloatFilter getDiscount() {
        return discount;
    }

    public FloatFilter discount() {
        if (discount == null) {
            discount = new FloatFilter();
        }
        return discount;
    }

    public void setDiscount(FloatFilter discount) {
        this.discount = discount;
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
        final ReferringCenterCriteria that = (ReferringCenterCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(nameAr, that.nameAr) &&
            Objects.equals(mobileNumber, that.mobileNumber) &&
            Objects.equals(email, that.email) &&
            Objects.equals(onlineReport, that.onlineReport) &&
            Objects.equals(contractType, that.contractType) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(internalUserId, that.internalUserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nameAr, mobileNumber, email, onlineReport, contractType, discount, internalUserId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReferringCenterCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (nameAr != null ? "nameAr=" + nameAr + ", " : "") +
            (mobileNumber != null ? "mobileNumber=" + mobileNumber + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (onlineReport != null ? "onlineReport=" + onlineReport + ", " : "") +
            (contractType != null ? "contractType=" + contractType + ", " : "") +
            (discount != null ? "discount=" + discount + ", " : "") +
            (internalUserId != null ? "internalUserId=" + internalUserId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
