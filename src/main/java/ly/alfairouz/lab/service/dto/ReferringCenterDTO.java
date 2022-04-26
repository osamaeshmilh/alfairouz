package ly.alfairouz.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;
import ly.alfairouz.lab.domain.enumeration.ContractType;

/**
 * A DTO for the {@link ly.alfairouz.lab.domain.ReferringCenter} entity.
 */
public class ReferringCenterDTO implements Serializable {

    private Long id;

    private String name;

    private String nameAr;

    private String mobileNumber;

    private String email;

    private Boolean onlineReport;

    private ContractType contractType;

    private Float discount;

    private UserDTO internalUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getOnlineReport() {
        return onlineReport;
    }

    public void setOnlineReport(Boolean onlineReport) {
        this.onlineReport = onlineReport;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public UserDTO getInternalUser() {
        return internalUser;
    }

    public void setInternalUser(UserDTO internalUser) {
        this.internalUser = internalUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReferringCenterDTO)) {
            return false;
        }

        ReferringCenterDTO referringCenterDTO = (ReferringCenterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, referringCenterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReferringCenterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nameAr='" + getNameAr() + "'" +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", onlineReport='" + getOnlineReport() + "'" +
            ", contractType='" + getContractType() + "'" +
            ", discount=" + getDiscount() +
            ", internalUser=" + getInternalUser() +
            "}";
    }
}
