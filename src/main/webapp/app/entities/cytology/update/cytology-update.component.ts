import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICytology, Cytology } from '../cytology.model';
import { CytologyService } from '../service/cytology.service';

@Component({
  selector: 'jhi-cytology-update',
  templateUrl: './cytology-update.component.html',
})
export class CytologyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected cytologyService: CytologyService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cytology }) => {
      this.updateForm(cytology);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cytology = this.createFromForm();
    if (cytology.id !== undefined) {
      this.subscribeToSaveResponse(this.cytologyService.update(cytology));
    } else {
      this.subscribeToSaveResponse(this.cytologyService.create(cytology));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICytology>>): void {
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

  protected updateForm(cytology: ICytology): void {
    this.editForm.patchValue({
      id: cytology.id,
      name: cytology.name,
    });
  }

  protected createFromForm(): ICytology {
    return {
      ...new Cytology(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }
}
