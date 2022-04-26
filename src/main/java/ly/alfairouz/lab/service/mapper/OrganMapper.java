package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.Organ;
import ly.alfairouz.lab.service.dto.OrganDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Organ} and its DTO {@link OrganDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganMapper extends EntityMapper<OrganDTO, Organ> {}
