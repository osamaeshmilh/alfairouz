package ly.alfairouz.lab.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link ly.alfairouz.lab.domain.Receipt} entity.
 */
public class ReceiptDTO implements Serializable {

    private Long id;

    private LocalDate dateAt;

    private String details;

    private String paymentMethod;

    private Float price;

    private Float paid;

    private Float notPaid;

    private SpecimenDTO specimen;

    private PatientDTO patient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateAt() {
        return dateAt;
    }

    public void setDateAt(LocalDate dateAt) {
        this.dateAt = dateAt;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPaid() {
        return paid;
    }

    public void setPaid(Float paid) {
        this.paid = paid;
    }

    public Float getNotPaid() {
        return notPaid;
    }

    public void setNotPaid(Float notPaid) {
        this.notPaid = notPaid;
    }

    public SpecimenDTO getSpecimen() {
        return specimen;
    }

    public void setSpecimen(SpecimenDTO specimen) {
        this.specimen = specimen;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReceiptDTO)) {
            return false;
        }

        ReceiptDTO receiptDTO = (ReceiptDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, receiptDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReceiptDTO{" +
            "id=" + getId() +
            ", dateAt='" + getDateAt() + "'" +
            ", details='" + getDetails() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", price=" + getPrice() +
            ", paid=" + getPaid() +
            ", notPaid=" + getNotPaid() +
            ", specimen=" + getSpecimen() +
            ", patient=" + getPatient() +
            "}";
    }
}
