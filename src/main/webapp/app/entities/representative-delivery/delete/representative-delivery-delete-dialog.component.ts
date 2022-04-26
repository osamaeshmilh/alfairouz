import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRepresentativeDelivery } from '../representative-delivery.model';
import { RepresentativeDeliveryService } from '../service/representative-delivery.service';

@Component({
  templateUrl: './representative-delivery-delete-dialog.component.html',
})
export class RepresentativeDeliveryDeleteDialogComponent {
  representativeDelivery?: IRepresentativeDelivery;

  constructor(protected representativeDeliveryService: RepresentativeDeliveryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.representativeDeliveryService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
