import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ExtraComponent } from './list/extra.component';
import { ExtraDetailComponent } from './detail/extra-detail.component';
import { ExtraUpdateComponent } from './update/extra-update.component';
import { ExtraDeleteDialogComponent } from './delete/extra-delete-dialog.component';
import { ExtraRoutingModule } from './route/extra-routing.module';

@NgModule({
  imports: [SharedModule, ExtraRoutingModule],
  declarations: [ExtraComponent, ExtraDetailComponent, ExtraUpdateComponent, ExtraDeleteDialogComponent],
  entryComponents: [ExtraDeleteDialogComponent],
})
export class ExtraModule {}
