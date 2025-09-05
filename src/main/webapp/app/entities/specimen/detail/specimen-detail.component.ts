import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {ISpecimen} from '../specimen.model';
import {DataUtils} from 'app/core/util/data-util.service';
import {IReferringCenterPrice} from "../../referring-center-price/referring-center-price.model";
import {ISpecimenEdit} from "../../specimen-edit/specimen-edit.model";
import {HttpResponse} from "@angular/common/http";
import {SpecimenEditService} from "../../specimen-edit/service/specimen-edit.service";
import { IReceipt } from "../../receipt/receipt.model";
import { ReceiptService } from "../../receipt/service/receipt.service";
import {MatDialog} from "@angular/material/dialog";
import {ReceiptDialogComponent} from "../../receipt/update/receipt-dialog.component";

@Component({
  selector: 'jhi-specimen-detail',
  templateUrl: './specimen-detail.component.html',
  styleUrls: ['./specimen-detail.component.scss']
})
export class SpecimenDetailComponent implements OnInit {
  specimen: ISpecimen | null = null;
  specimenEdits: ISpecimenEdit[] = [];
  receipts: IReceipt[] = [];

  constructor(protected dataUtils: DataUtils,
              protected specimenEditService: SpecimenEditService,
              protected receiptService: ReceiptService,
              protected dialog: MatDialog,
  protected activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ specimen }) => {
      this.specimen = specimen;
      this.loadReceipts();
    });

    this.specimenEditService
      .query({
        size: 1000,
        'specimenId.equals': this.specimen?.id,
      })
      .subscribe((res: HttpResponse<ISpecimenEdit[]>) => {
        this.specimenEdits = res.body ?? [];
      });
  }

  loadReceipts(): void {
    this.receiptService
      .query({
        size: 1000,
        'specimenId.equals': this.specimen?.id,
      })
      .subscribe((res: HttpResponse<IReceipt[]>) => {
        this.receipts = res.body ?? [];
      });
  }

  openReceiptDialog(): void {
    const dialogRef = this.dialog.open(ReceiptDialogComponent, {
      width: '600px',
      data: {
        specimen: this.specimen,
        existingReceipts: this.receipts
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadReceipts();
      }
    });
  }

  editReceipt(receipt: IReceipt): void {
    const dialogRef = this.dialog.open(ReceiptDialogComponent, {
      width: '600px',
      data: {
        specimen: this.specimen,
        receipt,
        existingReceipts: this.receipts
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadReceipts();
      }
    });
  }

  deleteReceipt(receiptId: any): void {
    if (confirm('هل أنت متأكد من حذف هذا الواصل؟')) {
      this.receiptService.delete(receiptId).subscribe(() => {
        this.loadReceipts();
      });
    }
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }

  getLabQr(): any {
    return this.specimen?.labQr;
  }

  getReciept(specimenId: any): void {
    const url = '/api/public/specimen/invoice/' + String(specimenId);
    window.open(url, '_blank');
  }

  getNormalReport(specimenId: any): void {
    const url = '/api/public/specimen/report/' + String(specimenId);
    window.open(url, '_blank');
  }

  getReport(specimenId: any): void {
    const url = '/api/public/specimen/report/' + String(specimenId);
    window.open(url, '_blank');
  }


  openFileFromUrl(fileUrl: any): void {
    window.open('/api/public/file/download/' + String(fileUrl) + '#zoom=85&scrollbar=0&toolbar=0&navpanes=0', '_blank');
  }
}
