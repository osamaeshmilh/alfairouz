package ly.alfairouz.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import ly.alfairouz.lab.domain.enumeration.ExtraAction;

/**
 * A Extra.
 */
@Entity
@Table(name = "extra")
public class Extra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_at")
    private LocalDate dateAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "extra_action")
    private ExtraAction extraAction;

    @Column(name = "details")
    private String details;

    @Column(name = "amount")
    private Float amount;

    @ManyToOne
    @JsonIgnoreProperties(value = { "internalUser" }, allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Extra id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateAt() {
        return this.dateAt;
    }

    public Extra dateAt(LocalDate dateAt) {
        this.setDateAt(dateAt);
        return this;
    }

    public void setDateAt(LocalDate dateAt) {
        this.dateAt = dateAt;
    }

    public ExtraAction getExtraAction() {
        return this.extraAction;
    }

    public Extra extraAction(ExtraAction extraAction) {
        this.setExtraAction(extraAction);
        return this;
    }

    public void setExtraAction(ExtraAction extraAction) {
        this.extraAction = extraAction;
    }

    public String getDetails() {
        return this.details;
    }

    public Extra details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Float getAmount() {
        return this.amount;
    }

    public Extra amount(Float amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Extra employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Extra)) {
            return false;
        }
        return id != null && id.equals(((Extra) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Extra{" +
            "id=" + getId() +
            ", dateAt='" + getDateAt() + "'" +
            ", extraAction='" + getExtraAction() + "'" +
            ", details='" + getDetails() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
