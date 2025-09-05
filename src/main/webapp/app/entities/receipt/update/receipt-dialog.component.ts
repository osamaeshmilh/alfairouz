// receipt-dialog.component.ts
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { IReceipt, Receipt } from '../../receipt/receipt.model';
import { ReceiptService } from '../../receipt/service/receipt.service';
import dayjs from 'dayjs/esm';

@Component({
  selector: 'jhi-receipt-dialog',
  template: `
    <h2 mat-dialog-title>{{ data.receipt ? 'تعديل واصل مالي' : 'إضافة واصل مالي جديد' }}</h2>

    <div mat-dialog-content>
      <div class="mb-3 p-3 bg-light rounded">
        <div class="row text-center">
          <div class="col-4">
            <strong>سعر العينة</strong><br>
            <span class="badge bg-primary">{{ data.specimen?.price ?? 0 }}</span>
          </div>
          <div class="col-4">
            <strong>إجمالي المدفوع</strong><br>
            <span class="badge bg-success">{{ totalReceiptsPaid }}</span>
          </div>
          <div class="col-4">
            <strong>الرصيد المتبقي</strong><br>
            <span class="badge" [class]="remainingBalance > 0 ? 'bg-warning' : 'bg-secondary'">
          {{ remainingBalance }}
        </span>
          </div>
        </div>
      </div>

      <form [formGroup]="receiptForm" class="receipt-form">
        <div class="mb-3">
          <mat-checkbox formControlName="isRefund" (change)="onRefundToggle()">
            واصل استرداد (Refund Receipt)
          </mat-checkbox>
        </div>

        <mat-form-field appearance="outline" class="w-100">
          <mat-label>التاريخ</mat-label>
          <input matInput [matDatepicker]="picker" formControlName="dateAt">
          <mat-datepicker-toggle matSuffix [for]="picker"></mat-datepicker-toggle>
          <mat-datepicker #picker></mat-datepicker>
        </mat-form-field>

        <mat-form-field appearance="outline" class="w-100">
          <mat-label>التفاصيل</mat-label>
          <input matInput formControlName="details">
        </mat-form-field>

        <mat-form-field appearance="outline" class="w-100">
          <mat-label>طريقة الدفع</mat-label>
          <mat-select formControlName="paymentMethod">
            <mat-option value="CASH">نقدي</mat-option>
            <mat-option value="ATM">بطاقة</mat-option>
            <mat-option value="CREDIT">اجل</mat-option>
          </mat-select>
        </mat-form-field>

        <mat-form-field appearance="outline" class="w-100">
          <mat-label>{{ getAmountLabel() }}</mat-label>
          <input matInput
                 type="number"
                 formControlName="paid"
                 [max]="getMaxAmount()"
                 min="0.01"
                 step="0.01">
          <mat-hint>الحد الأقصى: {{ getMaxAmount() }}</mat-hint>
          <mat-error *ngIf="receiptForm.get('paid')?.hasError('max')">
            {{ receiptForm.get('isRefund')?.value ?
            'لا يمكن استرداد أكثر من ' + maxRefundAmount :
            'لا يمكن أن يتجاوز الرصيد المتبقي ' + remainingBalance }}
          </mat-error>
          <mat-error *ngIf="receiptForm.get('paid')?.hasError('min')">
            المبلغ يجب أن يكون أكبر من صفر
          </mat-error>
        </mat-form-field>
      </form>
    </div>

    <div mat-dialog-actions align="end">
      <button mat-button (click)="onCancel()">إلغاء</button>
      <button mat-raised-button
              color="primary"
              (click)="onSave()"
              [disabled]="receiptForm.invalid ?? isSaving">
        {{ isSaving ? 'جاري الحفظ...' : 'حفظ' }}
      </button>
    </div>
  `,
  styles: [`
    .receipt-form {
      min-width: 500px;
    }

    .w-100 {
      width: 100%;
      margin-bottom: 16px;
    }
  `]
})
export class ReceiptDialogComponent implements OnInit {
  isSaving = false;
  totalReceiptsPaid = 0;
  remainingBalance = 0;
  maxRefundAmount = 0;

  receiptForm = this.fb.group({
    dateAt: [dayjs(), Validators.required],
    details: [''],
    paymentMethod: ['CASH', Validators.required],
    paid: [0, [Validators.required, Validators.min(0)]],
    isRefund: [false]
  });

  constructor(
    private fb: FormBuilder,
    private receiptService: ReceiptService,
    private dialogRef: MatDialogRef<ReceiptDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      specimen: any,
      receipt?: IReceipt,
      existingReceipts?: IReceipt[]
    }
  ) {}

  ngOnInit(): void {
    this.calculateTotalReceiptsPaid();
    this.calculateRemainingBalance();
    this.calculateMaxRefundAmount();
    this.setupValidation();

    if (this.data.receipt) {
      this.receiptForm.patchValue({
        dateAt: this.data.receipt.dateAt,
        details: this.data.receipt.details,
        paymentMethod: this.data.receipt.paymentMethod,
        paid: Math.abs(this.data.receipt.paid ?? 0), // Always show positive value
        isRefund: (this.data.receipt.paid ?? 0) < 0 // Determine if it's a refund
      });
    } else {
      // Default to remaining balance for new receipts
      this.receiptForm.patchValue({
        paid: this.remainingBalance > 0 ? this.remainingBalance : 0
      });
    }
  }

  calculateTotalReceiptsPaid(): void {
    this.totalReceiptsPaid = this.data.existingReceipts?.reduce((total, receipt) => {
      // Exclude current receipt being edited from the total
      if (this.data.receipt && receipt.id === this.data.receipt.id) {
        return total;
      }
      return total + (receipt.paid ?? 0);
    }, 0) ?? 0;
  }

  calculateRemainingBalance(): void {
    const specimenPrice = this.data.specimen?.price ?? 0;
    this.remainingBalance = specimenPrice - this.totalReceiptsPaid;

    // If editing existing receipt, add back its paid amount to available balance
    if (this.data.receipt) {
      this.remainingBalance += Math.abs(this.data.receipt.paid ?? 0);
    }

    // Ensure remaining balance is not negative
    this.remainingBalance = Math.max(0, this.remainingBalance);
  }

  calculateMaxRefundAmount(): void {
    // Can only refund up to what has been paid (excluding current receipt if editing)
    this.maxRefundAmount = Math.abs(this.totalReceiptsPaid);
    if (this.data.receipt?.paid && this.data.receipt.paid > 0) {
      this.maxRefundAmount += this.data.receipt.paid;
    }
  }

  setupValidation(): void {
    const isRefund = this.receiptForm.get('isRefund')?.value;
    const maxAmount = isRefund ? this.maxRefundAmount : this.remainingBalance;

    this.receiptForm.get('paid')?.setValidators([
      Validators.required,
      Validators.min(0.01), // Minimum amount
      Validators.max(maxAmount)
    ]);

    this.receiptForm.get('paid')?.updateValueAndValidity();
  }

  onRefundToggle(): void {
    const isRefund = this.receiptForm.get('isRefund')?.value;

    if (isRefund) {
      this.receiptForm.patchValue({
        details: 'استرداد مبلغ',
        paid: 0
      });
    } else {
      this.receiptForm.patchValue({
        details: '',
        paid: this.remainingBalance > 0 ? this.remainingBalance : 0
      });
    }

    this.setupValidation();
  }

  getMaxAmount(): number {
    const isRefund = this.receiptForm.get('isRefund')?.value;
    return isRefund ? this.maxRefundAmount : this.remainingBalance;
  }

  getAmountLabel(): string {
    const isRefund = this.receiptForm.get('isRefund')?.value;
    return isRefund ? 'مبلغ الاسترداد' : 'المبلغ المدفوع';
  }

  onSave(): void {
    if (this.receiptForm.valid) {
      this.isSaving = true;
      const formValue = this.receiptForm.value;
      const isRefund = formValue.isRefund;
      const paidAmount = Math.abs(formValue.paid ?? 0);

      const receipt: IReceipt = {
        ...new Receipt(),
        dateAt: formValue.dateAt ,
        details: formValue.details,
        paymentMethod: formValue.paymentMethod,
        // Set amounts: positive for payments, negative for refunds
        price: isRefund ? -paidAmount : paidAmount,
        paid: isRefund ? -paidAmount : paidAmount,
        notPaid: 0, // Not used in this simplified approach
        id: this.data.receipt?.id,
        specimen: this.data.specimen
      };

      const operation = receipt.id ?
        this.receiptService.update(receipt) :
        this.receiptService.create(receipt);

      operation.subscribe({
        next: () => {
          this.dialogRef.close(true);
        },
        error: () => {
          this.isSaving = false;
        }
      });
    }
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }
}
