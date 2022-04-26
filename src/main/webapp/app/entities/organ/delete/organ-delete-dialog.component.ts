import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrgan } from '../organ.model';
import { OrganService } from '../service/organ.service';

@Component({
  templateUrl: './organ-delete-dialog.component.html',
})
export class OrganDeleteDialogComponent {
  organ?: IOrgan;

  constructor(protected organService: OrganService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.organService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
