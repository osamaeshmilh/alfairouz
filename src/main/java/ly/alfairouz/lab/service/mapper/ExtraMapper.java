package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.Employee;
import ly.alfairouz.lab.domain.Extra;
import ly.alfairouz.lab.service.dto.EmployeeDTO;
import ly.alfairouz.lab.service.dto.ExtraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Extra} and its DTO {@link ExtraDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExtraMapper extends EntityMapper<ExtraDTO, Extra> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeName")
    ExtraDTO toDto(Extra s);

    @Named("employeeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    EmployeeDTO toDtoEmployeeName(Employee employee);
}
