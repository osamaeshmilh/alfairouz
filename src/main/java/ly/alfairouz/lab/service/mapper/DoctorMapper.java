package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.Doctor;
import ly.alfairouz.lab.domain.User;
import ly.alfairouz.lab.service.dto.DoctorDTO;
import ly.alfairouz.lab.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Doctor} and its DTO {@link DoctorDTO}.
 */
@Mapper(componentModel = "spring")
public interface DoctorMapper extends EntityMapper<DoctorDTO, Doctor> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userId")
    DoctorDTO toDto(Doctor s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
