import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RepresentativeDeliveryComponent } from './list/representative-delivery.component';
import { RepresentativeDeliveryDetailComponent } from './detail/representative-delivery-detail.component';
import { RepresentativeDeliveryUpdateComponent } from './update/representative-delivery-update.component';
import { RepresentativeDeliveryDeleteDialogComponent } from './delete/representative-delivery-delete-dialog.component';
import { RepresentativeDeliveryRoutingModule } from './route/representative-delivery-routing.module';

@NgModule({
  imports: [SharedModule, RepresentativeDeliveryRoutingModule],
  declarations: [
    RepresentativeDeliveryComponent,
    RepresentativeDeliveryDetailComponent,
    RepresentativeDeliveryUpdateComponent,
    RepresentativeDeliveryDeleteDialogComponent,
  ],
  entryComponents: [RepresentativeDeliveryDeleteDialogComponent],
})
export class RepresentativeDeliveryModule {}
