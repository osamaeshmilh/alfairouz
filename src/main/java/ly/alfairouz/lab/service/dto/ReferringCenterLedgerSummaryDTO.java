package ly.alfairouz.lab.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Summary and rows for a referring-center ledger.
 */
public class ReferringCenterLedgerSummaryDTO implements Serializable {

    private Long referringCenterId;

    private BigDecimal openingBalance = BigDecimal.ZERO;

    private BigDecimal monthlyDebits = BigDecimal.ZERO;

    private BigDecimal settlementPayments = BigDecimal.ZERO;

    private BigDecimal balance = BigDecimal.ZERO;

    private List<ReferringCenterLedgerEntryDTO> entries = new ArrayList<>();

    public Long getReferringCenterId() {
        return referringCenterId;
    }

    public void setReferringCenterId(Long referringCenterId) {
        this.referringCenterId = referringCenterId;
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    public BigDecimal getMonthlyDebits() {
        return monthlyDebits;
    }

    public void setMonthlyDebits(BigDecimal monthlyDebits) {
        this.monthlyDebits = monthlyDebits;
    }

    public BigDecimal getSettlementPayments() {
        return settlementPayments;
    }

    public void setSettlementPayments(BigDecimal settlementPayments) {
        this.settlementPayments = settlementPayments;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<ReferringCenterLedgerEntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(List<ReferringCenterLedgerEntryDTO> entries) {
        this.entries = entries;
    }
}
