import {Component} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

import {ISpecimenEdit} from '../specimen-edit.model';
import {SpecimenEditService} from '../service/specimen-edit.service';

@Component({
  templateUrl: './specimen-edit-delete-dialog.component.html',
})
export class SpecimenEditDeleteDialogComponent {
  specimenEdit?: ISpecimenEdit;

  constructor(protected specimenEditService: SpecimenEditService, protected activeModal: NgbActiveModal) {
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.specimenEditService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
