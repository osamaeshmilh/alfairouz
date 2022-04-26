import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICytology } from '../cytology.model';

@Component({
  selector: 'jhi-cytology-detail',
  templateUrl: './cytology-detail.component.html',
})
export class CytologyDetailComponent implements OnInit {
  cytology: ICytology | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cytology }) => {
      this.cytology = cytology;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
