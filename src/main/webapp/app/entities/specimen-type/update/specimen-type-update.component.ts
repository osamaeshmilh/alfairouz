import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISpecimenType, SpecimenType } from '../specimen-type.model';
import { SpecimenTypeService } from '../service/specimen-type.service';

@Component({
  selector: 'jhi-specimen-type-update',
  templateUrl: './specimen-type-update.component.html',
})
export class SpecimenTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    category: [],
    price: [],
  });

  constructor(protected specimenTypeService: SpecimenTypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ specimenType }) => {
      this.updateForm(specimenType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const specimenType = this.createFromForm();
    if (specimenType.id !== undefined) {
      this.subscribeToSaveResponse(this.specimenTypeService.update(specimenType));
    } else {
      this.subscribeToSaveResponse(this.specimenTypeService.create(specimenType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecimenType>>): void {
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

  protected updateForm(specimenType: ISpecimenType): void {
    this.editForm.patchValue({
      id: specimenType.id,
      name: specimenType.name,
      category: specimenType.category,
      price: specimenType.price,
    });
  }

  protected createFromForm(): ISpecimenType {
    return {
      ...new SpecimenType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      category: this.editForm.get(['category'])!.value,
      price: this.editForm.get(['price'])!.value,
    };
  }
}
