import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReferringCenter } from '../referring-center.model';

@Component({
  selector: 'jhi-referring-center-detail',
  templateUrl: './referring-center-detail.component.html',
})
export class ReferringCenterDetailComponent implements OnInit {
  referringCenter: IReferringCenter | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ referringCenter }) => {
      this.referringCenter = referringCenter;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
