package ly.alfairouz.lab.repository;

import ly.alfairouz.lab.domain.SpecimenEdit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SpecimenEdit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecimenEditRepository extends JpaRepository<SpecimenEdit, Long>, JpaSpecificationExecutor<SpecimenEdit> {
}
