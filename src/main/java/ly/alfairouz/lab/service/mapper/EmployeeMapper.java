package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.Employee;
import ly.alfairouz.lab.domain.User;
import ly.alfairouz.lab.service.dto.EmployeeDTO;
import ly.alfairouz.lab.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userId")
    EmployeeDTO toDto(Employee s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserId(User user);
}
