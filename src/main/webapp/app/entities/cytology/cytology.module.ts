import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CytologyComponent } from './list/cytology.component';
import { CytologyDetailComponent } from './detail/cytology-detail.component';
import { CytologyUpdateComponent } from './update/cytology-update.component';
import { CytologyDeleteDialogComponent } from './delete/cytology-delete-dialog.component';
import { CytologyRoutingModule } from './route/cytology-routing.module';

@NgModule({
  imports: [SharedModule, CytologyRoutingModule],
  declarations: [CytologyComponent, CytologyDetailComponent, CytologyUpdateComponent, CytologyDeleteDialogComponent],
  entryComponents: [CytologyDeleteDialogComponent],
})
export class CytologyModule {}
