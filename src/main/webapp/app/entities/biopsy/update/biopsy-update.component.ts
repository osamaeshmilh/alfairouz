import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IBiopsy, Biopsy } from '../biopsy.model';
import { BiopsyService } from '../service/biopsy.service';

@Component({
  selector: 'jhi-biopsy-update',
  templateUrl: './biopsy-update.component.html',
})
export class BiopsyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected biopsyService: BiopsyService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ biopsy }) => {
      this.updateForm(biopsy);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const biopsy = this.createFromForm();
    if (biopsy.id !== undefined) {
      this.subscribeToSaveResponse(this.biopsyService.update(biopsy));
    } else {
      this.subscribeToSaveResponse(this.biopsyService.create(biopsy));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBiopsy>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(biopsy: IBiopsy): void {
    this.editForm.patchValue({
      id: biopsy.id,
      name: biopsy.name,
    });
  }

  protected createFromForm(): IBiopsy {
    return {
      ...new Biopsy(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }
}
