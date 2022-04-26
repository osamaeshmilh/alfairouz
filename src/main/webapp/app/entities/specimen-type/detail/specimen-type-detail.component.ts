import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpecimenType } from '../specimen-type.model';

@Component({
  selector: 'jhi-specimen-type-detail',
  templateUrl: './specimen-type-detail.component.html',
})
export class SpecimenTypeDetailComponent implements OnInit {
  specimenType: ISpecimenType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ specimenType }) => {
      this.specimenType = specimenType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
