package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.Biopsy;
import ly.alfairouz.lab.service.dto.BiopsyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Biopsy} and its DTO {@link BiopsyDTO}.
 */
@Mapper(componentModel = "spring")
public interface BiopsyMapper extends EntityMapper<BiopsyDTO, Biopsy> {}
