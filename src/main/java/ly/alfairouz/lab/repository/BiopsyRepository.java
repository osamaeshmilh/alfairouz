package ly.alfairouz.lab.repository;

import ly.alfairouz.lab.domain.Biopsy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Biopsy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BiopsyRepository extends JpaRepository<Biopsy, Long>, JpaSpecificationExecutor<Biopsy> {}
