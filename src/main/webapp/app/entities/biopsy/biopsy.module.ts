import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BiopsyComponent } from './list/biopsy.component';
import { BiopsyDetailComponent } from './detail/biopsy-detail.component';
import { BiopsyUpdateComponent } from './update/biopsy-update.component';
import { BiopsyDeleteDialogComponent } from './delete/biopsy-delete-dialog.component';
import { BiopsyRoutingModule } from './route/biopsy-routing.module';

@NgModule({
  imports: [SharedModule, BiopsyRoutingModule],
  declarations: [BiopsyComponent, BiopsyDetailComponent, BiopsyUpdateComponent, BiopsyDeleteDialogComponent],
  entryComponents: [BiopsyDeleteDialogComponent],
})
export class BiopsyModule {}
