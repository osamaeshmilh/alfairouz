package ly.alfairouz.lab.repository;

import ly.alfairouz.lab.domain.SpecimenType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SpecimenType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecimenTypeRepository extends JpaRepository<SpecimenType, Long>, JpaSpecificationExecutor<SpecimenType> {}
