package ly.alfairouz.lab.repository;

import java.util.List;
import java.util.Optional;
import ly.alfairouz.lab.domain.BlockWithdraw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BlockWithdraw entity.
 */
@Repository
public interface BlockWithdrawRepository extends JpaRepository<BlockWithdraw, Long>, JpaSpecificationExecutor<BlockWithdraw> {
    default Optional<BlockWithdraw> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<BlockWithdraw> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<BlockWithdraw> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct blockWithdraw from BlockWithdraw blockWithdraw left join fetch blockWithdraw.specimen",
        countQuery = "select count(distinct blockWithdraw) from BlockWithdraw blockWithdraw"
    )
    Page<BlockWithdraw> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct blockWithdraw from BlockWithdraw blockWithdraw left join fetch blockWithdraw.specimen")
    List<BlockWithdraw> findAllWithToOneRelationships();

    @Query("select blockWithdraw from BlockWithdraw blockWithdraw left join fetch blockWithdraw.specimen where blockWithdraw.id =:id")
    Optional<BlockWithdraw> findOneWithToOneRelationships(@Param("id") Long id);
}
