package ly.alfairouz.lab.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import ly.alfairouz.lab.domain.ReferringCenter;
import ly.alfairouz.lab.domain.ReferringCenterLedgerEntry;
import ly.alfairouz.lab.domain.Specimen;
import ly.alfairouz.lab.domain.enumeration.PaymentType;
import ly.alfairouz.lab.domain.enumeration.ReferringCenterLedgerEntryType;
import ly.alfairouz.lab.repository.ReferringCenterLedgerEntryRepository;
import ly.alfairouz.lab.repository.ReferringCenterRepository;
import ly.alfairouz.lab.repository.SpecimenRepository;
import ly.alfairouz.lab.service.dto.ReferringCenterLedgerEntryDTO;
import ly.alfairouz.lab.service.dto.ReferringCenterLedgerSummaryDTO;
import ly.alfairouz.lab.service.util.FileTools;
import ly.alfairouz.lab.web.rest.errors.BadRequestAlertException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for referring-center ledger views and manual financial entries.
 */
@Service
@Transactional
public class ReferringCenterLedgerService {

    private static final String ENTITY_NAME = "referringCenterLedger";
    private static final String MANUAL_SOURCE = "MANUAL";
    private static final String SPECIMEN_SOURCE = "SPECIMEN";

    private final ReferringCenterLedgerEntryRepository referringCenterLedgerEntryRepository;
    private final ReferringCenterRepository referringCenterRepository;
    private final SpecimenRepository specimenRepository;

    public ReferringCenterLedgerService(
        ReferringCenterLedgerEntryRepository referringCenterLedgerEntryRepository,
        ReferringCenterRepository referringCenterRepository,
        SpecimenRepository specimenRepository
    ) {
        this.referringCenterLedgerEntryRepository = referringCenterLedgerEntryRepository;
        this.referringCenterRepository = referringCenterRepository;
        this.specimenRepository = specimenRepository;
    }

    @Transactional(readOnly = true)
    public ReferringCenterLedgerSummaryDTO getLedger(Long referringCenterId) {
        requireReferringCenter(referringCenterId);

        BigDecimal openingBalance = BigDecimal.ZERO;
        BigDecimal monthlyDebits = BigDecimal.ZERO;
        BigDecimal settlementPayments = BigDecimal.ZERO;
        List<ReferringCenterLedgerEntryDTO> entries = new ArrayList<>();

        List<ReferringCenterLedgerEntry> manualEntries = referringCenterLedgerEntryRepository.findByReferringCenterIdOrderByEntryDateAscIdAsc(
            referringCenterId
        );
        for (ReferringCenterLedgerEntry entry : manualEntries) {
            ReferringCenterLedgerEntryDTO dto = toManualDto(entry);
            if (entry.getEntryType() == ReferringCenterLedgerEntryType.OPENING_BALANCE) {
                openingBalance = openingBalance.add(nullToZero(dto.getDebit()));
            } else if (entry.getEntryType() == ReferringCenterLedgerEntryType.SETTLEMENT_PAYMENT) {
                settlementPayments = settlementPayments.add(nullToZero(dto.getCredit()));
            }
            entries.add(dto);
        }

        List<Specimen> specimens = specimenRepository.findByReferringCenterIdAndPaymentTypeOrderByReceivingDateAscIdAsc(
            referringCenterId,
            PaymentType.MONTHLY
        );
        for (Specimen specimen : specimens) {
            ReferringCenterLedgerEntryDTO dto = toSpecimenDebitDto(specimen, referringCenterId);
            monthlyDebits = monthlyDebits.add(nullToZero(dto.getDebit()));
            entries.add(dto);
        }

        entries.sort(
            Comparator
                .comparing((ReferringCenterLedgerEntryDTO entry) -> ledgerDate(entry.getEntryDate()))
                .thenComparing(this::ledgerSortOrder)
                .thenComparing(this::ledgerStableId)
        );

        BigDecimal runningBalance = BigDecimal.ZERO;
        for (ReferringCenterLedgerEntryDTO entry : entries) {
            runningBalance = runningBalance.add(nullToZero(entry.getDebit())).subtract(nullToZero(entry.getCredit()));
            entry.setRunningBalance(money(runningBalance));
        }

        ReferringCenterLedgerSummaryDTO summary = new ReferringCenterLedgerSummaryDTO();
        summary.setReferringCenterId(referringCenterId);
        summary.setOpeningBalance(money(openingBalance));
        summary.setMonthlyDebits(money(monthlyDebits));
        summary.setSettlementPayments(money(settlementPayments));
        summary.setBalance(money(openingBalance.add(monthlyDebits).subtract(settlementPayments)));
        summary.setEntries(entries);
        return summary;
    }

    public ReferringCenterLedgerSummaryDTO createOpeningBalance(Long referringCenterId, ReferringCenterLedgerEntryDTO dto) {
        ReferringCenter referringCenter = requireReferringCenter(referringCenterId);
        BigDecimal amount = requirePositiveAmount(dto);

        ReferringCenterLedgerEntry entry = new ReferringCenterLedgerEntry()
            .referringCenter(referringCenter)
            .entryType(ReferringCenterLedgerEntryType.OPENING_BALANCE)
            .entryDate(dto.getEntryDate() != null ? dto.getEntryDate() : LocalDate.now())
            .amount(amount)
            .notes(dto.getNotes());

        referringCenterLedgerEntryRepository.save(entry);
        return getLedger(referringCenterId);
    }

    public ReferringCenterLedgerSummaryDTO createSettlementPayment(Long referringCenterId, ReferringCenterLedgerEntryDTO dto) {
        ReferringCenter referringCenter = requireReferringCenter(referringCenterId);
        BigDecimal amount = requirePositiveAmount(dto);
        BigDecimal currentBalance = getLedger(referringCenterId).getBalance();

        if (currentBalance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestAlertException("Referring center has no debit to settle", ENTITY_NAME, "nodebit");
        }
        if (amount.compareTo(currentBalance) > 0) {
            throw new BadRequestAlertException("Settlement amount exceeds current debit", ENTITY_NAME, "settlementexceedsbalance");
        }

        ReferringCenterLedgerEntry entry = new ReferringCenterLedgerEntry()
            .referringCenter(referringCenter)
            .entryType(ReferringCenterLedgerEntryType.SETTLEMENT_PAYMENT)
            .entryDate(dto.getEntryDate() != null ? dto.getEntryDate() : LocalDate.now())
            .amount(amount)
            .paymentMethod(dto.getPaymentMethod())
            .paymentReference(dto.getPaymentReference())
            .notes(dto.getNotes());

        if (dto.getProofFile() != null) {
            String proofFileContentType = dto.getProofFileContentType() != null
                ? dto.getProofFileContentType()
                : MediaType.APPLICATION_OCTET_STREAM_VALUE;
            String proofFileUrl = FileTools.upload(
                dto.getProofFile(),
                proofFileContentType,
                "referring_center_settlement_" + referringCenterId
            );
            entry.setProofFileContentType(proofFileContentType);
            entry.setProofFileUrl(proofFileUrl);
        }

        referringCenterLedgerEntryRepository.save(entry);
        return getLedger(referringCenterId);
    }

    private ReferringCenter requireReferringCenter(Long referringCenterId) {
        return referringCenterRepository
            .findById(referringCenterId)
            .orElseThrow(() -> new BadRequestAlertException("Referring center not found", ENTITY_NAME, "referringcenternotfound"));
    }

    private BigDecimal requirePositiveAmount(ReferringCenterLedgerEntryDTO dto) {
        BigDecimal amount = dto.getAmount() != null ? dto.getAmount() : dto.getDebit();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestAlertException("Amount must be greater than zero", ENTITY_NAME, "invalidamount");
        }
        return money(amount);
    }

    private ReferringCenterLedgerEntryDTO toManualDto(ReferringCenterLedgerEntry entry) {
        ReferringCenterLedgerEntryDTO dto = new ReferringCenterLedgerEntryDTO();
        BigDecimal amount = money(nullToZero(entry.getAmount()));

        dto.setId(entry.getId());
        dto.setSource(MANUAL_SOURCE);
        dto.setEntryType(entry.getEntryType());
        dto.setEntryDate(entry.getEntryDate());
        dto.setAmount(amount);
        dto.setDescription(descriptionFor(entry.getEntryType()));
        dto.setPaymentMethod(entry.getPaymentMethod());
        dto.setPaymentReference(entry.getPaymentReference());
        dto.setNotes(entry.getNotes());
        dto.setProofFileContentType(entry.getProofFileContentType());
        dto.setProofFileUrl(entry.getProofFileUrl());
        dto.setReferringCenterId(entry.getReferringCenter() != null ? entry.getReferringCenter().getId() : null);
        dto.setCreatedBy(entry.getCreatedBy());
        dto.setCreatedDate(entry.getCreatedDate());
        dto.setLastModifiedBy(entry.getLastModifiedBy());
        dto.setLastModifiedDate(entry.getLastModifiedDate());

        if (entry.getEntryType() == ReferringCenterLedgerEntryType.SETTLEMENT_PAYMENT) {
            dto.setDebit(BigDecimal.ZERO);
            dto.setCredit(amount);
        } else {
            dto.setDebit(amount);
            dto.setCredit(BigDecimal.ZERO);
        }

        return dto;
    }

    private ReferringCenterLedgerEntryDTO toSpecimenDebitDto(Specimen specimen, Long referringCenterId) {
        ReferringCenterLedgerEntryDTO dto = new ReferringCenterLedgerEntryDTO();
        BigDecimal amount = money(specimen.getPrice() != null ? BigDecimal.valueOf(specimen.getPrice()) : BigDecimal.ZERO);

        dto.setSource(SPECIMEN_SOURCE);
        dto.setEntryType(ReferringCenterLedgerEntryType.MONTHLY_SPECIMEN_DEBIT);
        dto.setEntryDate(specimen.getReceivingDate() != null ? specimen.getReceivingDate() : specimen.getSamplingDate());
        dto.setAmount(amount);
        dto.setDebit(amount);
        dto.setCredit(BigDecimal.ZERO);
        dto.setDescription("Monthly specimen debit");
        dto.setReferringCenterId(referringCenterId);
        dto.setSpecimenId(specimen.getId());
        dto.setLabRefNo(specimen.getLabRefNo());
        return dto;
    }

    private String descriptionFor(ReferringCenterLedgerEntryType entryType) {
        if (entryType == ReferringCenterLedgerEntryType.OPENING_BALANCE) {
            return "Opening balance";
        }
        if (entryType == ReferringCenterLedgerEntryType.SETTLEMENT_PAYMENT) {
            return "Settlement payment";
        }
        return "Monthly specimen debit";
    }

    private LocalDate ledgerDate(LocalDate date) {
        return date != null ? date : LocalDate.MAX;
    }

    private int ledgerSortOrder(ReferringCenterLedgerEntryDTO entry) {
        if (entry.getEntryType() == ReferringCenterLedgerEntryType.OPENING_BALANCE) {
            return 0;
        }
        if (entry.getEntryType() == ReferringCenterLedgerEntryType.MONTHLY_SPECIMEN_DEBIT) {
            return 1;
        }
        return 2;
    }

    private Long ledgerStableId(ReferringCenterLedgerEntryDTO entry) {
        if (entry.getId() != null) {
            return entry.getId();
        }
        return entry.getSpecimenId() != null ? entry.getSpecimenId() : 0L;
    }

    private BigDecimal nullToZero(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }

    private BigDecimal money(BigDecimal value) {
        return nullToZero(value).setScale(2, RoundingMode.HALF_UP);
    }
}
