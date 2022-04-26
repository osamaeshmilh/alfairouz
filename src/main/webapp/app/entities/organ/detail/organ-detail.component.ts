import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrgan } from '../organ.model';

@Component({
  selector: 'jhi-organ-detail',
  templateUrl: './organ-detail.component.html',
})
export class OrganDetailComponent implements OnInit {
  organ: IOrgan | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organ }) => {
      this.organ = organ;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
