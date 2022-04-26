import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PapSmearSaleComponent } from './list/pap-smear-sale.component';
import { PapSmearSaleDetailComponent } from './detail/pap-smear-sale-detail.component';
import { PapSmearSaleUpdateComponent } from './update/pap-smear-sale-update.component';
import { PapSmearSaleDeleteDialogComponent } from './delete/pap-smear-sale-delete-dialog.component';
import { PapSmearSaleRoutingModule } from './route/pap-smear-sale-routing.module';

@NgModule({
  imports: [SharedModule, PapSmearSaleRoutingModule],
  declarations: [PapSmearSaleComponent, PapSmearSaleDetailComponent, PapSmearSaleUpdateComponent, PapSmearSaleDeleteDialogComponent],
  entryComponents: [PapSmearSaleDeleteDialogComponent],
})
export class PapSmearSaleModule {}
