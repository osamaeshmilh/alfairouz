import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SpecimenComponent } from './list/specimen.component';
import { SpecimenDetailComponent } from './detail/specimen-detail.component';
import { SpecimenUpdateComponent } from './update/specimen-update.component';
import { SpecimenDeleteDialogComponent } from './delete/specimen-delete-dialog.component';
import { SpecimenRoutingModule } from './route/specimen-routing.module';

@NgModule({
  imports: [SharedModule, SpecimenRoutingModule],
  declarations: [SpecimenComponent, SpecimenDetailComponent, SpecimenUpdateComponent, SpecimenDeleteDialogComponent],
  entryComponents: [SpecimenDeleteDialogComponent],
})
export class SpecimenModule {}
