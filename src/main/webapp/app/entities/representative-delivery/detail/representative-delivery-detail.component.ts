import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRepresentativeDelivery } from '../representative-delivery.model';

@Component({
  selector: 'jhi-representative-delivery-detail',
  templateUrl: './representative-delivery-detail.component.html',
})
export class RepresentativeDeliveryDetailComponent implements OnInit {
  representativeDelivery: IRepresentativeDelivery | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ representativeDelivery }) => {
      this.representativeDelivery = representativeDelivery;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
