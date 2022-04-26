package ly.alfairouz.lab.repository;

import ly.alfairouz.lab.domain.Organ;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Organ entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganRepository extends JpaRepository<Organ, Long>, JpaSpecificationExecutor<Organ> {}
