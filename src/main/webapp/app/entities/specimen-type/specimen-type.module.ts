import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SpecimenTypeComponent } from './list/specimen-type.component';
import { SpecimenTypeDetailComponent } from './detail/specimen-type-detail.component';
import { SpecimenTypeUpdateComponent } from './update/specimen-type-update.component';
import { SpecimenTypeDeleteDialogComponent } from './delete/specimen-type-delete-dialog.component';
import { SpecimenTypeRoutingModule } from './route/specimen-type-routing.module';

@NgModule({
  imports: [SharedModule, SpecimenTypeRoutingModule],
  declarations: [SpecimenTypeComponent, SpecimenTypeDetailComponent, SpecimenTypeUpdateComponent, SpecimenTypeDeleteDialogComponent],
  entryComponents: [SpecimenTypeDeleteDialogComponent],
})
export class SpecimenTypeModule {}
