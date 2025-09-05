package ly.alfairouz.lab.repository;

import java.util.List;
import java.util.Optional;
import ly.alfairouz.lab.domain.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Receipt entity.
 */
@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long>, JpaSpecificationExecutor<Receipt> {
    default Optional<Receipt> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Receipt> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Receipt> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct receipt from Receipt receipt left join fetch receipt.specimen left join fetch receipt.patient",
        countQuery = "select count(distinct receipt) from Receipt receipt"
    )
    Page<Receipt> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct receipt from Receipt receipt left join fetch receipt.specimen left join fetch receipt.patient")
    List<Receipt> findAllWithToOneRelationships();

    @Query("select receipt from Receipt receipt left join fetch receipt.specimen left join fetch receipt.patient where receipt.id =:id")
    Optional<Receipt> findOneWithToOneRelationships(@Param("id") Long id);

    List<Receipt> findBySpecimenId(Long specimenId);
}
