package ly.alfairouz.lab.repository;

import java.util.List;
import java.util.Optional;

import ly.alfairouz.lab.domain.ReferringCenterPrice;
import ly.alfairouz.lab.service.dto.ReferringCenterPriceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ReferringCenterPrice entity.
 */
@Repository
public interface ReferringCenterPriceRepository
    extends JpaRepository<ReferringCenterPrice, Long>, JpaSpecificationExecutor<ReferringCenterPrice> {
    default Optional<ReferringCenterPrice> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ReferringCenterPrice> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ReferringCenterPrice> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct referringCenterPrice from ReferringCenterPrice referringCenterPrice left join fetch referringCenterPrice.specimenType left join fetch referringCenterPrice.size left join fetch referringCenterPrice.referringCenter",
        countQuery = "select count(distinct referringCenterPrice) from ReferringCenterPrice referringCenterPrice"
    )
    Page<ReferringCenterPrice> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct referringCenterPrice from ReferringCenterPrice referringCenterPrice left join fetch referringCenterPrice.specimenType left join fetch referringCenterPrice.size left join fetch referringCenterPrice.referringCenter"
    )
    List<ReferringCenterPrice> findAllWithToOneRelationships();

    @Query(
        "select referringCenterPrice from ReferringCenterPrice referringCenterPrice left join fetch referringCenterPrice.specimenType left join fetch referringCenterPrice.size left join fetch referringCenterPrice.referringCenter where referringCenterPrice.id =:id"
    )
    Optional<ReferringCenterPrice> findOneWithToOneRelationships(@Param("id") Long id);

    List<ReferringCenterPrice> findByReferringCenterId(Long centerId);

    ReferringCenterPrice findOneByReferringCenterIdAndSizeId(Long referringCenter_id, Long size_id);

    ReferringCenterPrice findOneByReferringCenterIdAndSpecimenTypeId(Long referringCenter_id, Long specimenType_id);

    void deleteAllByReferringCenterId(Long referringCenter_id);
}
