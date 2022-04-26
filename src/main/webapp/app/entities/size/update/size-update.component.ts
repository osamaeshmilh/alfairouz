import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISize, Size } from '../size.model';
import { SizeService } from '../service/size.service';

@Component({
  selector: 'jhi-size-update',
  templateUrl: './size-update.component.html',
})
export class SizeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    price: [],
  });

  constructor(protected sizeService: SizeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ size }) => {
      this.updateForm(size);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const size = this.createFromForm();
    if (size.id !== undefined) {
      this.subscribeToSaveResponse(this.sizeService.update(size));
    } else {
      this.subscribeToSaveResponse(this.sizeService.create(size));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISize>>): void {
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

  protected updateForm(size: ISize): void {
    this.editForm.patchValue({
      id: size.id,
      name: size.name,
      price: size.price,
    });
  }

  protected createFromForm(): ISize {
    return {
      ...new Size(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      price: this.editForm.get(['price'])!.value,
    };
  }
}
