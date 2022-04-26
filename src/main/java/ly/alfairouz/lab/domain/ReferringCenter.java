package ly.alfairouz.lab.domain;

import java.io.Serializable;
import javax.persistence.*;
import ly.alfairouz.lab.domain.enumeration.ContractType;

/**
 * A ReferringCenter.
 */
@Entity
@Table(name = "referring_center")
public class ReferringCenter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "name_ar")
    private String nameAr;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "online_report")
    private Boolean onlineReport;

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type")
    private ContractType contractType;

    @Column(name = "discount")
    private Float discount;

    @OneToOne
    @JoinColumn(unique = true)
    private User internalUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReferringCenter id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ReferringCenter name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameAr() {
        return this.nameAr;
    }

    public ReferringCenter nameAr(String nameAr) {
        this.setNameAr(nameAr);
        return this;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public ReferringCenter mobileNumber(String mobileNumber) {
        this.setMobileNumber(mobileNumber);
        return this;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public ReferringCenter email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getOnlineReport() {
        return this.onlineReport;
    }

    public ReferringCenter onlineReport(Boolean onlineReport) {
        this.setOnlineReport(onlineReport);
        return this;
    }

    public void setOnlineReport(Boolean onlineReport) {
        this.onlineReport = onlineReport;
    }

    public ContractType getContractType() {
        return this.contractType;
    }

    public ReferringCenter contractType(ContractType contractType) {
        this.setContractType(contractType);
        return this;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public Float getDiscount() {
        return this.discount;
    }

    public ReferringCenter discount(Float discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public ReferringCenter internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReferringCenter)) {
            return false;
        }
        return id != null && id.equals(((ReferringCenter) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReferringCenter{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nameAr='" + getNameAr() + "'" +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", onlineReport='" + getOnlineReport() + "'" +
            ", contractType='" + getContractType() + "'" +
            ", discount=" + getDiscount() +
            "}";
    }
}
