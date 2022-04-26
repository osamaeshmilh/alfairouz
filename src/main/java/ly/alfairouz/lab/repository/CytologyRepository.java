package ly.alfairouz.lab.repository;

import ly.alfairouz.lab.domain.Cytology;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cytology entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CytologyRepository extends JpaRepository<Cytology, Long>, JpaSpecificationExecutor<Cytology> {}
