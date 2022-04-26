import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IExtra } from '../extra.model';
import { ExtraService } from '../service/extra.service';

@Component({
  templateUrl: './extra-delete-dialog.component.html',
})
export class ExtraDeleteDialogComponent {
  extra?: IExtra;

  constructor(protected extraService: ExtraService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.extraService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
