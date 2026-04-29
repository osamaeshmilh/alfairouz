export type ReferringCenterLedgerEntryType = 'OPENING_BALANCE' | 'MONTHLY_SPECIMEN_DEBIT' | 'SETTLEMENT_PAYMENT';

export interface IReferringCenterLedgerEntry {
  id?: number | null;
  source?: string | null;
  entryType?: ReferringCenterLedgerEntryType | null;
  entryDate?: string | null;
  amount?: number | null;
  debit?: number | null;
  credit?: number | null;
  runningBalance?: number | null;
  description?: string | null;
  paymentMethod?: string | null;
  paymentReference?: string | null;
  notes?: string | null;
  proofFile?: string | null;
  proofFileContentType?: string | null;
  proofFileUrl?: string | null;
  referringCenterId?: number | null;
  specimenId?: number | null;
  labRefNo?: string | null;
}

export interface IReferringCenterLedgerSummary {
  referringCenterId?: number | null;
  openingBalance?: number | null;
  monthlyDebits?: number | null;
  settlementPayments?: number | null;
  balance?: number | null;
  entries?: IReferringCenterLedgerEntry[];
}
