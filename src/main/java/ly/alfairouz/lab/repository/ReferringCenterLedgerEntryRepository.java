package ly.alfairouz.lab.repository;

import java.util.List;
import java.util.Optional;
import ly.alfairouz.lab.domain.ReferringCenterLedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for referring-center manual ledger entries.
 */
@Repository
public interface ReferringCenterLedgerEntryRepository extends JpaRepository<ReferringCenterLedgerEntry, Long> {
    List<ReferringCenterLedgerEntry> findByReferringCenterIdOrderByEntryDateAscIdAsc(Long referringCenterId);

    Optional<ReferringCenterLedgerEntry> findByIdAndReferringCenterId(Long id, Long referringCenterId);
}
