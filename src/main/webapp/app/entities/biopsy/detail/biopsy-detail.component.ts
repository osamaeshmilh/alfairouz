import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBiopsy } from '../biopsy.model';

@Component({
  selector: 'jhi-biopsy-detail',
  templateUrl: './biopsy-detail.component.html',
})
export class BiopsyDetailComponent implements OnInit {
  biopsy: IBiopsy | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ biopsy }) => {
      this.biopsy = biopsy;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
