import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPapSmearSale } from '../pap-smear-sale.model';

@Component({
  selector: 'jhi-pap-smear-sale-detail',
  templateUrl: './pap-smear-sale-detail.component.html',
})
export class PapSmearSaleDetailComponent implements OnInit {
  papSmearSale: IPapSmearSale | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ papSmearSale }) => {
      this.papSmearSale = papSmearSale;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
