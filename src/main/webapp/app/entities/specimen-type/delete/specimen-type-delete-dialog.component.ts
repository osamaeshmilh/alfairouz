import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpecimenType } from '../specimen-type.model';
import { SpecimenTypeService } from '../service/specimen-type.service';

@Component({
  templateUrl: './specimen-type-delete-dialog.component.html',
})
export class SpecimenTypeDeleteDialogComponent {
  specimenType?: ISpecimenType;

  constructor(protected specimenTypeService: SpecimenTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.specimenTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
