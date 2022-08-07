package ly.alfairouz.lab.repository;

import ly.alfairouz.lab.domain.Doctor;
import ly.alfairouz.lab.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Doctor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {

    Doctor findByInternalUser(User user);

}
