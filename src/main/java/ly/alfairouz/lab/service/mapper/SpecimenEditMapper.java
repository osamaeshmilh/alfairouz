package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.SpecimenEdit;
import ly.alfairouz.lab.service.dto.SpecimenEditDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SpecimenEdit} and its DTO {@link SpecimenEditDTO}.
 */
@Mapper(componentModel = "spring")
public interface SpecimenEditMapper extends EntityMapper<SpecimenEditDTO, SpecimenEdit> {
}
