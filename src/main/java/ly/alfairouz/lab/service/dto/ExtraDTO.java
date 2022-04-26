package ly.alfairouz.lab.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import ly.alfairouz.lab.domain.enumeration.ExtraAction;

/**
 * A DTO for the {@link ly.alfairouz.lab.domain.Extra} entity.
 */
public class ExtraDTO implements Serializable {

    private Long id;

    private LocalDate dateAt;

    private ExtraAction extraAction;

    private String details;

    private Float amount;

    private EmployeeDTO employee;

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

    public ExtraAction getExtraAction() {
        return extraAction;
    }

    public void setExtraAction(ExtraAction extraAction) {
        this.extraAction = extraAction;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExtraDTO)) {
            return false;
        }

        ExtraDTO extraDTO = (ExtraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, extraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExtraDTO{" +
            "id=" + getId() +
            ", dateAt='" + getDateAt() + "'" +
            ", extraAction='" + getExtraAction() + "'" +
            ", details='" + getDetails() + "'" +
            ", amount=" + getAmount() +
            ", employee=" + getEmployee() +
            "}";
    }
}
