package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.BlockWithdraw;
import ly.alfairouz.lab.domain.Specimen;
import ly.alfairouz.lab.service.dto.BlockWithdrawDTO;
import ly.alfairouz.lab.service.dto.SpecimenDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BlockWithdraw} and its DTO {@link BlockWithdrawDTO}.
 */
@Mapper(componentModel = "spring")
public interface BlockWithdrawMapper extends EntityMapper<BlockWithdrawDTO, BlockWithdraw> {
    @Mapping(target = "specimen", source = "specimen", qualifiedByName = "specimenLabRefNo")
    BlockWithdrawDTO toDto(BlockWithdraw s);

    @Named("specimenLabRefNo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "labRefNo", source = "labRefNo")
    SpecimenDTO toDtoSpecimenLabRefNo(Specimen specimen);
}
