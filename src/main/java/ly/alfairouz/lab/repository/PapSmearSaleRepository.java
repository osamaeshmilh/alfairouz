package ly.alfairouz.lab.repository;

import java.util.List;
import java.util.Optional;
import ly.alfairouz.lab.domain.PapSmearSale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PapSmearSale entity.
 */
@Repository
public interface PapSmearSaleRepository extends JpaRepository<PapSmearSale, Long>, JpaSpecificationExecutor<PapSmearSale> {
    default Optional<PapSmearSale> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PapSmearSale> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PapSmearSale> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct papSmearSale from PapSmearSale papSmearSale left join fetch papSmearSale.referringCenter",
        countQuery = "select count(distinct papSmearSale) from PapSmearSale papSmearSale"
    )
    Page<PapSmearSale> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct papSmearSale from PapSmearSale papSmearSale left join fetch papSmearSale.referringCenter")
    List<PapSmearSale> findAllWithToOneRelationships();

    @Query("select papSmearSale from PapSmearSale papSmearSale left join fetch papSmearSale.referringCenter where papSmearSale.id =:id")
    Optional<PapSmearSale> findOneWithToOneRelationships(@Param("id") Long id);
}
