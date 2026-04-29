import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {IReferringCenter} from '../referring-center.model';
import {HttpResponse} from "@angular/common/http";
import {ReferringCenterPriceService} from "../../referring-center-price/service/referring-center-price.service";
import {IReferringCenterPrice} from "../../referring-center-price/referring-center-price.model";
import {ReferringCenterService} from "../service/referring-center.service";
import {
  IReferringCenterLedgerEntry,
  IReferringCenterLedgerSummary,
  ReferringCenterLedgerEntryType,
} from '../referring-center-ledger.model';
import { MatDialog } from '@angular/material/dialog';
import { ReferringCenterLedgerDialogComponent } from './referring-center-ledger-dialog.component';

@Component({
  selector: 'jhi-referring-center-detail',
  templateUrl: './referring-center-detail.component.html',
})
export class ReferringCenterDetailComponent implements OnInit {
  referringCenter: IReferringCenter | null = null;
  referringCenterPrices: IReferringCenterPrice[] = [];
  ledgerSummary: IReferringCenterLedgerSummary | null = null;
  ledgerEntries: IReferringCenterLedgerEntry[] = [];
  isLoading = false;
  isLedgerLoading = false;
  currency = 'LYD';


  constructor(protected activatedRoute: ActivatedRoute,
              protected referringCenterService: ReferringCenterService,
              protected referringCenterPriceService: ReferringCenterPriceService,
              protected dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({referringCenter}) => {
      this.referringCenter = referringCenter;
      this.loadReferringCenterPrices();
      this.loadLedger();
    });
  }

  loadReferringCenterPrices(): void {
    if (!this.referringCenter?.id) {
      return;
    }
    this.referringCenterPriceService
      .query({
        size: 1000,
        'referringCenterId.equals': this.referringCenter.id,
      })
      .subscribe((res: HttpResponse<IReferringCenterPrice[]>) => {
        this.referringCenterPrices = res.body ?? [];
      });
  }

  loadLedger(): void {
    if (!this.referringCenter?.id) {
      return;
    }
    this.isLedgerLoading = true;
    this.referringCenterService.getLedger(this.referringCenter.id).subscribe({
      next: (res: HttpResponse<IReferringCenterLedgerSummary>) => {
        this.applyLedgerSummary(res.body);
        this.isLedgerLoading = false;
      },
      error: () => {
        this.isLedgerLoading = false;
      },
    });
  }

  resetReferringCenterPrices(referringCenterId: any): void {
    this.isLoading = true;
    this.referringCenterService
      .resetPrices(referringCenterId)
      .subscribe(() => {

        this.loadReferringCenterPrices();
        this.isLoading = false;

      });
  }

  openOpeningBalanceDialog(): void {
    const dialogRef = this.dialog.open(ReferringCenterLedgerDialogComponent, {
      width: '560px',
      data: {
        mode: 'OPENING_BALANCE' as ReferringCenterLedgerEntryType,
      },
    });

    dialogRef.afterClosed().subscribe((entry?: IReferringCenterLedgerEntry) => {
      if (!entry || !this.referringCenter?.id) {
        return;
      }
      this.saveLedgerEntry(this.referringCenter.id, entry, 'OPENING_BALANCE');
    });
  }

  openSettlementDialog(): void {
    const dialogRef = this.dialog.open(ReferringCenterLedgerDialogComponent, {
      width: '560px',
      data: {
        mode: 'SETTLEMENT_PAYMENT' as ReferringCenterLedgerEntryType,
        currentBalance: this.ledgerSummary?.balance ?? 0,
      },
    });

    dialogRef.afterClosed().subscribe((entry?: IReferringCenterLedgerEntry) => {
      if (!entry || !this.referringCenter?.id) {
        return;
      }
      this.saveLedgerEntry(this.referringCenter.id, entry, 'SETTLEMENT_PAYMENT');
    });
  }

  hasDebit(): boolean {
    return (this.ledgerSummary?.balance ?? 0) > 0;
  }

  ledgerTypeLabel(type?: ReferringCenterLedgerEntryType | null): string {
    switch (type) {
      case 'OPENING_BALANCE':
        return 'رصيد افتتاحي';
      case 'MONTHLY_SPECIMEN_DEBIT':
        return 'عينة شهرية';
      case 'SETTLEMENT_PAYMENT':
        return 'تسوية';
      default:
        return '-';
    }
  }

  ledgerTypeClass(type?: ReferringCenterLedgerEntryType | null): string {
    switch (type) {
      case 'OPENING_BALANCE':
        return 'bg-secondary';
      case 'MONTHLY_SPECIMEN_DEBIT':
        return 'bg-warning text-dark';
      case 'SETTLEMENT_PAYMENT':
        return 'bg-success';
      default:
        return 'bg-light text-dark';
    }
  }

  getProofUrl(fileName: string | null | undefined): string {
    return fileName ? this.referringCenterService.getProofDownloadUrl(fileName) : '';
  }

  trackLedgerEntry(index: number, entry: IReferringCenterLedgerEntry): string {
    return `${entry.source ?? 'ROW'}-${entry.id ?? entry.specimenId ?? index}`;
  }

  previousState(): void {
    window.history.back();
  }

  private saveLedgerEntry(
    referringCenterId: number,
    entry: IReferringCenterLedgerEntry,
    entryType: Exclude<ReferringCenterLedgerEntryType, 'MONTHLY_SPECIMEN_DEBIT'>
  ): void {
    this.isLedgerLoading = true;
    const request =
      entryType === 'OPENING_BALANCE'
        ? this.referringCenterService.createOpeningBalance(referringCenterId, entry)
        : this.referringCenterService.createSettlementPayment(referringCenterId, entry);

    request.subscribe({
      next: (res: HttpResponse<IReferringCenterLedgerSummary>) => {
        this.applyLedgerSummary(res.body);
        this.isLedgerLoading = false;
      },
      error: () => {
        this.isLedgerLoading = false;
      },
    });
  }

  private applyLedgerSummary(summary: IReferringCenterLedgerSummary | null): void {
    this.ledgerSummary = summary;
    this.ledgerEntries = summary?.entries ?? [];
  }
}
