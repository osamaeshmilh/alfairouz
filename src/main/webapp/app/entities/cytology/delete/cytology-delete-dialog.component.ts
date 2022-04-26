import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICytology } from '../cytology.model';
import { CytologyService } from '../service/cytology.service';

@Component({
  templateUrl: './cytology-delete-dialog.component.html',
})
export class CytologyDeleteDialogComponent {
  cytology?: ICytology;

  constructor(protected cytologyService: CytologyService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cytologyService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
