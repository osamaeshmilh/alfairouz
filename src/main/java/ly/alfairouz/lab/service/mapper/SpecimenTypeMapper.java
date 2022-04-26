package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.SpecimenType;
import ly.alfairouz.lab.service.dto.SpecimenTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SpecimenType} and its DTO {@link SpecimenTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface SpecimenTypeMapper extends EntityMapper<SpecimenTypeDTO, SpecimenType> {}
