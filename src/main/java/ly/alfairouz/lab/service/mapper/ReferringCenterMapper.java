package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.ReferringCenter;
import ly.alfairouz.lab.domain.User;
import ly.alfairouz.lab.service.dto.ReferringCenterDTO;
import ly.alfairouz.lab.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReferringCenter} and its DTO {@link ReferringCenterDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReferringCenterMapper extends EntityMapper<ReferringCenterDTO, ReferringCenter> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userId")
    ReferringCenterDTO toDto(ReferringCenter s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
