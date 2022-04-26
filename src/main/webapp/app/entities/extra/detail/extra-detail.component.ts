import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExtra } from '../extra.model';

@Component({
  selector: 'jhi-extra-detail',
  templateUrl: './extra-detail.component.html',
})
export class ExtraDetailComponent implements OnInit {
  extra: IExtra | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ extra }) => {
      this.extra = extra;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
