package ly.alfairouz.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;

import ly.alfairouz.lab.domain.enumeration.SpecimenStatus;

/**
 * A DTO for the {@link ly.alfairouz.lab.domain.SpecimenEdit} entity.
 */
public class SpecimenEditDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private Long specimenId;

    private String labRefNo;

    private SpecimenStatus specimenStatusFrom;

    private SpecimenStatus specimenStatusTo;

    private String userType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpecimenId() {
        return specimenId;
    }

    public void setSpecimenId(Long specimenId) {
        this.specimenId = specimenId;
    }

    public String getLabRefNo() {
        return labRefNo;
    }

    public void setLabRefNo(String labRefNo) {
        this.labRefNo = labRefNo;
    }

    public SpecimenStatus getSpecimenStatusFrom() {
        return specimenStatusFrom;
    }

    public void setSpecimenStatusFrom(SpecimenStatus specimenStatusFrom) {
        this.specimenStatusFrom = specimenStatusFrom;
    }

    public SpecimenStatus getSpecimenStatusTo() {
        return specimenStatusTo;
    }

    public void setSpecimenStatusTo(SpecimenStatus specimenStatusTo) {
        this.specimenStatusTo = specimenStatusTo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpecimenEditDTO)) {
            return false;
        }

        SpecimenEditDTO specimenEditDTO = (SpecimenEditDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, specimenEditDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpecimenEditDTO{" +
            "id=" + getId() +
            ", specimenId=" + getSpecimenId() +
            ", labRefNo='" + getLabRefNo() + "'" +
            ", specimenStatusFrom='" + getSpecimenStatusFrom() + "'" +
            ", specimenStatusTo='" + getSpecimenStatusTo() + "'" +
            ", userType='" + getUserType() + "'" +
            "}";
    }
}
