package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.Size;
import ly.alfairouz.lab.service.dto.SizeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Size} and its DTO {@link SizeDTO}.
 */
@Mapper(componentModel = "spring")
public interface SizeMapper extends EntityMapper<SizeDTO, Size> {}
