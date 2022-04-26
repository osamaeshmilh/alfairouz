import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrganComponent } from './list/organ.component';
import { OrganDetailComponent } from './detail/organ-detail.component';
import { OrganUpdateComponent } from './update/organ-update.component';
import { OrganDeleteDialogComponent } from './delete/organ-delete-dialog.component';
import { OrganRoutingModule } from './route/organ-routing.module';

@NgModule({
  imports: [SharedModule, OrganRoutingModule],
  declarations: [OrganComponent, OrganDetailComponent, OrganUpdateComponent, OrganDeleteDialogComponent],
  entryComponents: [OrganDeleteDialogComponent],
})
export class OrganModule {}
