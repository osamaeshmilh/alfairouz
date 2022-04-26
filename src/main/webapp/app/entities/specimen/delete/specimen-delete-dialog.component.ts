import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpecimen } from '../specimen.model';
import { SpecimenService } from '../service/specimen.service';

@Component({
  templateUrl: './specimen-delete-dialog.component.html',
})
export class SpecimenDeleteDialogComponent {
  specimen?: ISpecimen;

  constructor(protected specimenService: SpecimenService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.specimenService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
