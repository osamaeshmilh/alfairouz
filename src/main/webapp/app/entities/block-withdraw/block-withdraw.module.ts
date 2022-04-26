import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BlockWithdrawComponent } from './list/block-withdraw.component';
import { BlockWithdrawDetailComponent } from './detail/block-withdraw-detail.component';
import { BlockWithdrawUpdateComponent } from './update/block-withdraw-update.component';
import { BlockWithdrawDeleteDialogComponent } from './delete/block-withdraw-delete-dialog.component';
import { BlockWithdrawRoutingModule } from './route/block-withdraw-routing.module';

@NgModule({
  imports: [SharedModule, BlockWithdrawRoutingModule],
  declarations: [BlockWithdrawComponent, BlockWithdrawDetailComponent, BlockWithdrawUpdateComponent, BlockWithdrawDeleteDialogComponent],
  entryComponents: [BlockWithdrawDeleteDialogComponent],
})
export class BlockWithdrawModule {}
