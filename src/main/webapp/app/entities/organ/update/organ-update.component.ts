import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IOrgan, Organ } from '../organ.model';
import { OrganService } from '../service/organ.service';

@Component({
  selector: 'jhi-organ-update',
  templateUrl: './organ-update.component.html',
})
export class OrganUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected organService: OrganService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organ }) => {
      this.updateForm(organ);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organ = this.createFromForm();
    if (organ.id !== undefined) {
      this.subscribeToSaveResponse(this.organService.update(organ));
    } else {
      this.subscribeToSaveResponse(this.organService.create(organ));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrgan>>): void {
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

  protected updateForm(organ: IOrgan): void {
    this.editForm.patchValue({
      id: organ.id,
      name: organ.name,
    });
  }

  protected createFromForm(): IOrgan {
    return {
      ...new Organ(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }
}
