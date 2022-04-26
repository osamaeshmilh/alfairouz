import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPapSmearSale } from '../pap-smear-sale.model';
import { PapSmearSaleService } from '../service/pap-smear-sale.service';

@Component({
  templateUrl: './pap-smear-sale-delete-dialog.component.html',
})
export class PapSmearSaleDeleteDialogComponent {
  papSmearSale?: IPapSmearSale;

  constructor(protected papSmearSaleService: PapSmearSaleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.papSmearSaleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
