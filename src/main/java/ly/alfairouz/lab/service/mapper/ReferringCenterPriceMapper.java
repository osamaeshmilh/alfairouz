package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.ReferringCenter;
import ly.alfairouz.lab.domain.ReferringCenterPrice;
import ly.alfairouz.lab.domain.Size;
import ly.alfairouz.lab.domain.SpecimenType;
import ly.alfairouz.lab.service.dto.ReferringCenterDTO;
import ly.alfairouz.lab.service.dto.ReferringCenterPriceDTO;
import ly.alfairouz.lab.service.dto.SizeDTO;
import ly.alfairouz.lab.service.dto.SpecimenTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReferringCenterPrice} and its DTO {@link ReferringCenterPriceDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReferringCenterPriceMapper extends EntityMapper<ReferringCenterPriceDTO, ReferringCenterPrice> {
    @Mapping(target = "specimenType", source = "specimenType", qualifiedByName = "specimenTypeName")
    @Mapping(target = "size", source = "size", qualifiedByName = "sizeName")
    @Mapping(target = "referringCenter", source = "referringCenter", qualifiedByName = "referringCenterName")
    ReferringCenterPriceDTO toDto(ReferringCenterPrice s);

    @Named("specimenTypeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SpecimenTypeDTO toDtoSpecimenTypeName(SpecimenType specimenType);

    @Named("sizeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SizeDTO toDtoSizeName(Size size);

    @Named("referringCenterName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ReferringCenterDTO toDtoReferringCenterName(ReferringCenter referringCenter);

}
