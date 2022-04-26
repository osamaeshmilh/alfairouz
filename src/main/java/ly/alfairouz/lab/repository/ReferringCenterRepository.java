package ly.alfairouz.lab.repository;

import ly.alfairouz.lab.domain.ReferringCenter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReferringCenter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReferringCenterRepository extends JpaRepository<ReferringCenter, Long>, JpaSpecificationExecutor<ReferringCenter> {}
