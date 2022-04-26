package ly.alfairouz.lab.repository;

import ly.alfairouz.lab.domain.RepresentativeDelivery;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RepresentativeDelivery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RepresentativeDeliveryRepository
    extends JpaRepository<RepresentativeDelivery, Long>, JpaSpecificationExecutor<RepresentativeDelivery> {}
