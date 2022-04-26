package ly.alfairouz.lab.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A RepresentativeDelivery.
 */
@Entity
@Table(name = "representative_delivery")
public class RepresentativeDelivery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_at")
    private LocalDate dateAt;

    @Column(name = "details")
    private String details;

    @Column(name = "total")
    private Float total;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RepresentativeDelivery id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateAt() {
        return this.dateAt;
    }

    public RepresentativeDelivery dateAt(LocalDate dateAt) {
        this.setDateAt(dateAt);
        return this;
    }

    public void setDateAt(LocalDate dateAt) {
        this.dateAt = dateAt;
    }

    public String getDetails() {
        return this.details;
    }

    public RepresentativeDelivery details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Float getTotal() {
        return this.total;
    }

    public RepresentativeDelivery total(Float total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RepresentativeDelivery)) {
            return false;
        }
        return id != null && id.equals(((RepresentativeDelivery) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RepresentativeDelivery{" +
            "id=" + getId() +
            ", dateAt='" + getDateAt() + "'" +
            ", details='" + getDetails() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}
