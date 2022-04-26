import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReferringCenterComponent } from './list/referring-center.component';
import { ReferringCenterDetailComponent } from './detail/referring-center-detail.component';
import { ReferringCenterUpdateComponent } from './update/referring-center-update.component';
import { ReferringCenterDeleteDialogComponent } from './delete/referring-center-delete-dialog.component';
import { ReferringCenterRoutingModule } from './route/referring-center-routing.module';

@NgModule({
  imports: [SharedModule, ReferringCenterRoutingModule],
  declarations: [
    ReferringCenterComponent,
    ReferringCenterDetailComponent,
    ReferringCenterUpdateComponent,
    ReferringCenterDeleteDialogComponent,
  ],
  entryComponents: [ReferringCenterDeleteDialogComponent],
})
export class ReferringCenterModule {}
