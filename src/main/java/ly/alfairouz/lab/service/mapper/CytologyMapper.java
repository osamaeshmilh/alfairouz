package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.Cytology;
import ly.alfairouz.lab.service.dto.CytologyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cytology} and its DTO {@link CytologyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CytologyMapper extends EntityMapper<CytologyDTO, Cytology> {}
