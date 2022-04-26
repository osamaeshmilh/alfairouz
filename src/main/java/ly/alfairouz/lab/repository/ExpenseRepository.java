package ly.alfairouz.lab.repository;

import java.util.List;
import java.util.Optional;
import ly.alfairouz.lab.domain.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Expense entity.
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {
    default Optional<Expense> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Expense> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Expense> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct expense from Expense expense left join fetch expense.employee",
        countQuery = "select count(distinct expense) from Expense expense"
    )
    Page<Expense> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct expense from Expense expense left join fetch expense.employee")
    List<Expense> findAllWithToOneRelationships();

    @Query("select expense from Expense expense left join fetch expense.employee where expense.id =:id")
    Optional<Expense> findOneWithToOneRelationships(@Param("id") Long id);
}
