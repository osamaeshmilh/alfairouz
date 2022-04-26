package ly.alfairouz.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;
import ly.alfairouz.lab.domain.enumeration.DoctorType;

/**
 * A DTO for the {@link ly.alfairouz.lab.domain.Doctor} entity.
 */
public class DoctorDTO implements Serializable {

    private Long id;

    private String name;

    private String nameAr;

    private String description;

    private String mobileNo;

    private String email;

    private Boolean onlineReport;

    private Boolean emailReport;

    private Float percentage;

    private DoctorType doctorType;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    public Boolean getEmailReport() {
        return emailReport;
    }

    public void setEmailReport(Boolean emailReport) {
        this.emailReport = emailReport;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public DoctorType getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(DoctorType doctorType) {
        this.doctorType = doctorType;
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
        if (!(o instanceof DoctorDTO)) {
            return false;
        }

        DoctorDTO doctorDTO = (DoctorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, doctorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoctorDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nameAr='" + getNameAr() + "'" +
            ", description='" + getDescription() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", email='" + getEmail() + "'" +
            ", onlineReport='" + getOnlineReport() + "'" +
            ", emailReport='" + getEmailReport() + "'" +
            ", percentage=" + getPercentage() +
            ", doctorType='" + getDoctorType() + "'" +
            ", internalUser=" + getInternalUser() +
            "}";
    }
}
