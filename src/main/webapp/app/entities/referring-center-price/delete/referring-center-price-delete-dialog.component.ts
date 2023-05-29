import {Component} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

import {IReferringCenterPrice} from '../referring-center-price.model';
import {ReferringCenterPriceService} from '../service/referring-center-price.service';

@Component({
  templateUrl: './referring-center-price-delete-dialog.component.html',
})
export class ReferringCenterPriceDeleteDialogComponent {
  referringCenterPrice?: IReferringCenterPrice;

  constructor(protected referringCenterPriceService: ReferringCenterPriceService, protected activeModal: NgbActiveModal) {
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.referringCenterPriceService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
