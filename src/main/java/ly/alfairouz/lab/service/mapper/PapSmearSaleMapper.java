package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.PapSmearSale;
import ly.alfairouz.lab.domain.ReferringCenter;
import ly.alfairouz.lab.service.dto.PapSmearSaleDTO;
import ly.alfairouz.lab.service.dto.ReferringCenterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PapSmearSale} and its DTO {@link PapSmearSaleDTO}.
 */
@Mapper(componentModel = "spring")
public interface PapSmearSaleMapper extends EntityMapper<PapSmearSaleDTO, PapSmearSale> {
    @Mapping(target = "referringCenter", source = "referringCenter", qualifiedByName = "referringCenterName")
    PapSmearSaleDTO toDto(PapSmearSale s);

    @Named("referringCenterName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ReferringCenterDTO toDtoReferringCenterName(ReferringCenter referringCenter);
}
