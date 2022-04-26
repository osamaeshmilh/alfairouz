package ly.alfairouz.lab.repository;

import java.util.List;
import java.util.Optional;
import ly.alfairouz.lab.domain.Extra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Extra entity.
 */
@Repository
public interface ExtraRepository extends JpaRepository<Extra, Long>, JpaSpecificationExecutor<Extra> {
    default Optional<Extra> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Extra> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Extra> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct extra from Extra extra left join fetch extra.employee",
        countQuery = "select count(distinct extra) from Extra extra"
    )
    Page<Extra> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct extra from Extra extra left join fetch extra.employee")
    List<Extra> findAllWithToOneRelationships();

    @Query("select extra from Extra extra left join fetch extra.employee where extra.id =:id")
    Optional<Extra> findOneWithToOneRelationships(@Param("id") Long id);
}
