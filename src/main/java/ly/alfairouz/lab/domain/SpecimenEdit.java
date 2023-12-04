package ly.alfairouz.lab.domain;

import java.io.Serializable;
import javax.persistence.*;

import ly.alfairouz.lab.domain.enumeration.SpecimenStatus;

/**
 * A SpecimenEdit.
 */
@Entity
@Table(name = "specimen_edit")
public class SpecimenEdit extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "specimen_id")
    private Long specimenId;

    @Column(name = "lab_ref_no")
    private String labRefNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "specimen_status_from")
    private SpecimenStatus specimenStatusFrom;

    @Enumerated(EnumType.STRING)
    @Column(name = "specimen_status_to")
    private SpecimenStatus specimenStatusTo;

    @Column(name = "user_type")
    private String userType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SpecimenEdit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpecimenId() {
        return this.specimenId;
    }

    public SpecimenEdit specimenId(Long specimenId) {
        this.setSpecimenId(specimenId);
        return this;
    }

    public void setSpecimenId(Long specimenId) {
        this.specimenId = specimenId;
    }

    public String getLabRefNo() {
        return this.labRefNo;
    }

    public SpecimenEdit labRefNo(String labRefNo) {
        this.setLabRefNo(labRefNo);
        return this;
    }

    public void setLabRefNo(String labRefNo) {
        this.labRefNo = labRefNo;
    }

    public SpecimenStatus getSpecimenStatusFrom() {
        return this.specimenStatusFrom;
    }

    public SpecimenEdit specimenStatusFrom(SpecimenStatus specimenStatusFrom) {
        this.setSpecimenStatusFrom(specimenStatusFrom);
        return this;
    }

    public void setSpecimenStatusFrom(SpecimenStatus specimenStatusFrom) {
        this.specimenStatusFrom = specimenStatusFrom;
    }

    public SpecimenStatus getSpecimenStatusTo() {
        return this.specimenStatusTo;
    }

    public SpecimenEdit specimenStatusTo(SpecimenStatus specimenStatusTo) {
        this.setSpecimenStatusTo(specimenStatusTo);
        return this;
    }

    public void setSpecimenStatusTo(SpecimenStatus specimenStatusTo) {
        this.specimenStatusTo = specimenStatusTo;
    }

    public String getUserType() {
        return this.userType;
    }

    public SpecimenEdit userType(String userType) {
        this.setUserType(userType);
        return this;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpecimenEdit)) {
            return false;
        }
        return id != null && id.equals(((SpecimenEdit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpecimenEdit{" +
            "id=" + getId() +
            ", specimenId=" + getSpecimenId() +
            ", labRefNo='" + getLabRefNo() + "'" +
            ", specimenStatusFrom='" + getSpecimenStatusFrom() + "'" +
            ", specimenStatusTo='" + getSpecimenStatusTo() + "'" +
            ", userType='" + getUserType() + "'" +
            "}";
    }
}
