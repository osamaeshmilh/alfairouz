package ly.alfairouz.lab.domain;

import java.io.Serializable;
import javax.persistence.*;
import ly.alfairouz.lab.domain.enumeration.DoctorType;

/**
 * A Doctor.
 */
@Entity
@Table(name = "doctor")
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "name_ar")
    private String nameAr;

    @Column(name = "description")
    private String description;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "email")
    private String email;

    @Column(name = "online_report")
    private Boolean onlineReport;

    @Column(name = "email_report")
    private Boolean emailReport;

    @Column(name = "percentage")
    private Float percentage;

    @Enumerated(EnumType.STRING)
    @Column(name = "doctor_type")
    private DoctorType doctorType;

    @OneToOne
    @JoinColumn(unique = true)
    private User internalUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Doctor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Doctor name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameAr() {
        return this.nameAr;
    }

    public Doctor nameAr(String nameAr) {
        this.setNameAr(nameAr);
        return this;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getDescription() {
        return this.description;
    }

    public Doctor description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public Doctor mobileNo(String mobileNo) {
        this.setMobileNo(mobileNo);
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return this.email;
    }

    public Doctor email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getOnlineReport() {
        return this.onlineReport;
    }

    public Doctor onlineReport(Boolean onlineReport) {
        this.setOnlineReport(onlineReport);
        return this;
    }

    public void setOnlineReport(Boolean onlineReport) {
        this.onlineReport = onlineReport;
    }

    public Boolean getEmailReport() {
        return this.emailReport;
    }

    public Doctor emailReport(Boolean emailReport) {
        this.setEmailReport(emailReport);
        return this;
    }

    public void setEmailReport(Boolean emailReport) {
        this.emailReport = emailReport;
    }

    public Float getPercentage() {
        return this.percentage;
    }

    public Doctor percentage(Float percentage) {
        this.setPercentage(percentage);
        return this;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public DoctorType getDoctorType() {
        return this.doctorType;
    }

    public Doctor doctorType(DoctorType doctorType) {
        this.setDoctorType(doctorType);
        return this;
    }

    public void setDoctorType(DoctorType doctorType) {
        this.doctorType = doctorType;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public Doctor internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doctor)) {
            return false;
        }
        return id != null && id.equals(((Doctor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Doctor{" +
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
            "}";
    }
}
