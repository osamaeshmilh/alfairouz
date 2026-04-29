import { Component, Inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IReferringCenterLedgerEntry, ReferringCenterLedgerEntryType } from '../referring-center-ledger.model';

interface LedgerDialogData {
  mode: Exclude<ReferringCenterLedgerEntryType, 'MONTHLY_SPECIMEN_DEBIT'>;
  currentBalance?: number | null;
}

@Component({
  selector: 'jhi-referring-center-ledger-dialog',
  template: `
    <h2 mat-dialog-title>{{ isSettlement ? 'تسوية الحساب' : 'رصيد افتتاحي (مديونية)' }}</h2>

    <div mat-dialog-content>
      <div class="mb-3 p-3 bg-light rounded" *ngIf="isSettlement">
        <strong>المديونية الحالية</strong>
        <span class="badge bg-warning ms-2">{{ (data.currentBalance ?? 0) | number: '1.2-2' }}</span>
      </div>

      <form [formGroup]="ledgerForm" class="ledger-form">
        <mat-form-field appearance="outline" class="w-100">
          <mat-label>التاريخ</mat-label>
          <input matInput type="date" formControlName="entryDate" />
        </mat-form-field>

        <mat-form-field appearance="outline" class="w-100">
          <mat-label>{{ isSettlement ? 'مبلغ التسوية' : 'الرصيد الافتتاحي' }}</mat-label>
          <input matInput type="number" formControlName="amount" min="0.01" step="0.01" />
          <mat-error *ngIf="ledgerForm.get('amount')?.hasError('required')">المبلغ مطلوب</mat-error>
          <mat-error *ngIf="ledgerForm.get('amount')?.hasError('min')">المبلغ يجب أن يكون أكبر من صفر</mat-error>
        </mat-form-field>

        <ng-container *ngIf="isSettlement">
          <mat-form-field appearance="outline" class="w-100">
            <mat-label>طريقة الدفع</mat-label>
            <mat-select formControlName="paymentMethod">
              <mat-option value="CASH">نقدي</mat-option>
              <mat-option value="ATM">بطاقة</mat-option>
              <mat-option value="BANK_TRANSFER">حوالة مصرفية</mat-option>
              <mat-option value="CHEQUE">صك</mat-option>
            </mat-select>
          </mat-form-field>

          <mat-form-field appearance="outline" class="w-100">
            <mat-label>رقم المرجع</mat-label>
            <input matInput formControlName="paymentReference" />
          </mat-form-field>

          <div class="mb-3">
            <label class="form-label">إثبات الدفع</label>
            <input class="form-control" type="file" accept="image/*,application/pdf" (change)="setFileData($event)" />
            <small class="text-muted" *ngIf="ledgerForm.get('proofFile')?.value">تم اختيار الملف</small>
          </div>
        </ng-container>

        <mat-form-field appearance="outline" class="w-100">
          <mat-label>ملاحظات</mat-label>
          <textarea matInput rows="3" formControlName="notes"></textarea>
        </mat-form-field>
      </form>
    </div>

    <div mat-dialog-actions align="end">
      <button mat-button type="button" (click)="cancel()">إلغاء</button>
      <button mat-raised-button color="primary" type="button" (click)="save()" [disabled]="ledgerForm.invalid">
        حفظ
      </button>
    </div>
  `,
  styles: [
    `
      .ledger-form {
        min-width: 500px;
      }

      .w-100 {
        width: 100%;
        margin-bottom: 12px;
      }
    `,
  ],
})
export class ReferringCenterLedgerDialogComponent {
  ledgerForm = this.fb.group({
    entryDate: [dayjs().format(DATE_FORMAT), Validators.required],
    amount: [this.data.mode === 'SETTLEMENT_PAYMENT' ? this.data.currentBalance ?? null : null, [Validators.required, Validators.min(0.01)]],
    paymentMethod: ['CASH'],
    paymentReference: [''],
    notes: [''],
    proofFile: [null],
    proofFileContentType: [null],
  });

  constructor(
    protected fb: FormBuilder,
    protected dialogRef: MatDialogRef<ReferringCenterLedgerDialogComponent, IReferringCenterLedgerEntry | undefined>,
    @Inject(MAT_DIALOG_DATA) public data: LedgerDialogData
  ) {
    if (this.isSettlement) {
      this.ledgerForm.get('paymentMethod')?.setValidators([Validators.required]);
      this.ledgerForm.get('paymentMethod')?.updateValueAndValidity();
    }
  }

  get isSettlement(): boolean {
    return this.data.mode === 'SETTLEMENT_PAYMENT';
  }

  setFileData(event: Event): void {
    const input = event.target as HTMLInputElement | null;
    const file = input?.files?.[0];
    if (!file) {
      return;
    }

    const reader = new FileReader();
    reader.onload = (): void => {
      if (typeof reader.result === 'string') {
        this.ledgerForm.patchValue({
          proofFile: reader.result.substring(reader.result.indexOf('base64,') + 'base64,'.length),
          proofFileContentType: file.type || 'application/octet-stream',
        });
      }
    };
    reader.readAsDataURL(file);
  }

  save(): void {
    if (this.ledgerForm.invalid) {
      return;
    }

    const formValue = this.ledgerForm.value;
    const entry: IReferringCenterLedgerEntry = {
      entryType: this.data.mode,
      entryDate: formValue.entryDate,
      amount: Number(formValue.amount),
      paymentMethod: formValue.paymentMethod,
      paymentReference: formValue.paymentReference,
      notes: formValue.notes,
      proofFile: formValue.proofFile,
      proofFileContentType: formValue.proofFileContentType,
    };

    this.dialogRef.close(entry);
  }

  cancel(): void {
    this.dialogRef.close(undefined);
  }
}
