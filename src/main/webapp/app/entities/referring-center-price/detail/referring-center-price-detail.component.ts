import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {IReferringCenterPrice} from '../referring-center-price.model';

@Component({
  selector: 'jhi-referring-center-price-detail',
  templateUrl: './referring-center-price-detail.component.html',
})
export class ReferringCenterPriceDetailComponent implements OnInit {
  referringCenterPrice: IReferringCenterPrice | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({referringCenterPrice}) => {
      this.referringCenterPrice = referringCenterPrice;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
