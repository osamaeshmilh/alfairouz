import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBlockWithdraw } from '../block-withdraw.model';
import { BlockWithdrawService } from '../service/block-withdraw.service';

@Component({
  templateUrl: './block-withdraw-delete-dialog.component.html',
})
export class BlockWithdrawDeleteDialogComponent {
  blockWithdraw?: IBlockWithdraw;

  constructor(protected blockWithdrawService: BlockWithdrawService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.blockWithdrawService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
