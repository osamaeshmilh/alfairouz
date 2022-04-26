package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.RepresentativeDelivery;
import ly.alfairouz.lab.service.dto.RepresentativeDeliveryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RepresentativeDelivery} and its DTO {@link RepresentativeDeliveryDTO}.
 */
@Mapper(componentModel = "spring")
public interface RepresentativeDeliveryMapper extends EntityMapper<RepresentativeDeliveryDTO, RepresentativeDelivery> {}
