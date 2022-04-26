package ly.alfairouz.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ly.alfairouz.lab.domain.SpecimenType} entity.
 */
public class SpecimenTypeDTO implements Serializable {

    private Long id;

    private String name;

    private String category;

    private Float price;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpecimenTypeDTO)) {
            return false;
        }

        SpecimenTypeDTO specimenTypeDTO = (SpecimenTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, specimenTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpecimenTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
