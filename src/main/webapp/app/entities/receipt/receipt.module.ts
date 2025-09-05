import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReceiptComponent } from './list/receipt.component';
import { ReceiptDetailComponent } from './detail/receipt-detail.component';
import { ReceiptUpdateComponent } from './update/receipt-update.component';
import { ReceiptDeleteDialogComponent } from './delete/receipt-delete-dialog.component';
import { ReceiptRoutingModule } from './route/receipt-routing.module';
import {ReceiptDialogComponent} from "./update/receipt-dialog.component";

@NgModule({
  imports: [SharedModule, ReceiptRoutingModule],
  declarations: [ReceiptComponent, ReceiptDetailComponent, ReceiptUpdateComponent, ReceiptDeleteDialogComponent, ReceiptDialogComponent],
  entryComponents: [ReceiptDeleteDialogComponent, ReceiptDialogComponent],
})
export class ReceiptModule {}
