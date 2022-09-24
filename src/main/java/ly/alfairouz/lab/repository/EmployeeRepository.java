package ly.alfairouz.lab.repository;

import ly.alfairouz.lab.domain.Employee;
import ly.alfairouz.lab.domain.User;
import ly.alfairouz.lab.domain.enumeration.JobTitle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    Long countByJobTitleEquals(JobTitle jobTitle);

    Employee findByInternalUser(User user);
}
