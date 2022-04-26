package ly.alfairouz.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A Receipt.
 */
@Entity
@Table(name = "receipt")
public class Receipt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_at")
    private LocalDate dateAt;

    @Column(name = "details")
    private String details;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "price")
    private Float price;

    @Column(name = "paid")
    private Float paid;

    @Column(name = "not_paid")
    private Float notPaid;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "patient",
            "biopsy",
            "cytology",
            "organ",
            "specimenType",
            "size",
            "referringCenter",
            "grossingDoctor",
            "referringDoctor",
            "pathologistDoctor",
            "operatorEmployee",
            "correctorEmployee",
        },
        allowSetters = true
    )
    private Specimen specimen;

    @ManyToOne
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Receipt id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateAt() {
        return this.dateAt;
    }

    public Receipt dateAt(LocalDate dateAt) {
        this.setDateAt(dateAt);
        return this;
    }

    public void setDateAt(LocalDate dateAt) {
        this.dateAt = dateAt;
    }

    public String getDetails() {
        return this.details;
    }

    public Receipt details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public Receipt paymentMethod(String paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Float getPrice() {
        return this.price;
    }

    public Receipt price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPaid() {
        return this.paid;
    }

    public Receipt paid(Float paid) {
        this.setPaid(paid);
        return this;
    }

    public void setPaid(Float paid) {
        this.paid = paid;
    }

    public Float getNotPaid() {
        return this.notPaid;
    }

    public Receipt notPaid(Float notPaid) {
        this.setNotPaid(notPaid);
        return this;
    }

    public void setNotPaid(Float notPaid) {
        this.notPaid = notPaid;
    }

    public Specimen getSpecimen() {
        return this.specimen;
    }

    public void setSpecimen(Specimen specimen) {
        this.specimen = specimen;
    }

    public Receipt specimen(Specimen specimen) {
        this.setSpecimen(specimen);
        return this;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Receipt patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Receipt)) {
            return false;
        }
        return id != null && id.equals(((Receipt) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Receipt{" +
            "id=" + getId() +
            ", dateAt='" + getDateAt() + "'" +
            ", details='" + getDetails() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", price=" + getPrice() +
            ", paid=" + getPaid() +
            ", notPaid=" + getNotPaid() +
            "}";
    }
}
