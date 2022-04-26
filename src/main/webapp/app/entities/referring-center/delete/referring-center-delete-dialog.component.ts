import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IReferringCenter } from '../referring-center.model';
import { ReferringCenterService } from '../service/referring-center.service';

@Component({
  templateUrl: './referring-center-delete-dialog.component.html',
})
export class ReferringCenterDeleteDialogComponent {
  referringCenter?: IReferringCenter;

  constructor(protected referringCenterService: ReferringCenterService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.referringCenterService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
