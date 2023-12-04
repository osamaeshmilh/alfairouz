import {NgModule} from '@angular/core';
import {SharedModule} from 'app/shared/shared.module';
import {SpecimenEditComponent} from './list/specimen-edit.component';
import {SpecimenEditDetailComponent} from './detail/specimen-edit-detail.component';
import {SpecimenEditUpdateComponent} from './update/specimen-edit-update.component';
import {SpecimenEditDeleteDialogComponent} from './delete/specimen-edit-delete-dialog.component';
import {SpecimenEditRoutingModule} from './route/specimen-edit-routing.module';

@NgModule({
  imports: [SharedModule, SpecimenEditRoutingModule],
  declarations: [SpecimenEditComponent, SpecimenEditDetailComponent, SpecimenEditUpdateComponent, SpecimenEditDeleteDialogComponent],
  entryComponents: [SpecimenEditDeleteDialogComponent],
})
export class SpecimenEditModule {
}
