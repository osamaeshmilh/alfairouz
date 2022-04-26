package ly.alfairouz.lab.service.mapper;

import ly.alfairouz.lab.domain.Employee;
import ly.alfairouz.lab.domain.Expense;
import ly.alfairouz.lab.service.dto.EmployeeDTO;
import ly.alfairouz.lab.service.dto.ExpenseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Expense} and its DTO {@link ExpenseDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExpenseMapper extends EntityMapper<ExpenseDTO, Expense> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeName")
    ExpenseDTO toDto(Expense s);

    @Named("employeeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    EmployeeDTO toDtoEmployeeName(Employee employee);
}
