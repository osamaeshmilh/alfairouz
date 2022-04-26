import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBiopsy } from '../biopsy.model';
import { BiopsyService } from '../service/biopsy.service';

@Component({
  templateUrl: './biopsy-delete-dialog.component.html',
})
export class BiopsyDeleteDialogComponent {
  biopsy?: IBiopsy;

  constructor(protected biopsyService: BiopsyService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.biopsyService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
