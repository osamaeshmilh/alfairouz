package ly.alfairouz.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ly.alfairouz.lab.domain.Organ} entity.
 */
public class OrganDTO implements Serializable {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganDTO)) {
            return false;
        }

        OrganDTO organDTO = (OrganDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
