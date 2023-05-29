import {NgModule} from '@angular/core';
import {SharedModule} from 'app/shared/shared.module';
import {ReferringCenterPriceComponent} from './list/referring-center-price.component';
import {ReferringCenterPriceDetailComponent} from './detail/referring-center-price-detail.component';
import {ReferringCenterPriceUpdateComponent} from './update/referring-center-price-update.component';
import {ReferringCenterPriceDeleteDialogComponent} from './delete/referring-center-price-delete-dialog.component';
import {ReferringCenterPriceRoutingModule} from './route/referring-center-price-routing.module';

@NgModule({
  imports: [SharedModule, ReferringCenterPriceRoutingModule],
  declarations: [
    ReferringCenterPriceComponent,
    ReferringCenterPriceDetailComponent,
    ReferringCenterPriceUpdateComponent,
    ReferringCenterPriceDeleteDialogComponent,
  ],
  entryComponents: [ReferringCenterPriceDeleteDialogComponent],
})
export class ReferringCenterPriceModule {
}
