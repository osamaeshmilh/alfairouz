package ly.alfairouz.lab.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link ly.alfairouz.lab.domain.RepresentativeDelivery} entity.
 */
public class RepresentativeDeliveryDTO implements Serializable {

    private Long id;

    private LocalDate dateAt;

    private String details;

    private Float total;

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

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RepresentativeDeliveryDTO)) {
            return false;
        }

        RepresentativeDeliveryDTO representativeDeliveryDTO = (RepresentativeDeliveryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, representativeDeliveryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RepresentativeDeliveryDTO{" +
            "id=" + getId() +
            ", dateAt='" + getDateAt() + "'" +
            ", details='" + getDetails() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}
