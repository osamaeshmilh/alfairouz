import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IRepresentativeDelivery, RepresentativeDelivery } from '../representative-delivery.model';
import { RepresentativeDeliveryService } from '../service/representative-delivery.service';

@Component({
  selector: 'jhi-representative-delivery-update',
  templateUrl: './representative-delivery-update.component.html',
})
export class RepresentativeDeliveryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    dateAt: [],
    details: [],
    total: [],
  });

  constructor(
    protected representativeDeliveryService: RepresentativeDeliveryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ representativeDelivery }) => {
      this.updateForm(representativeDelivery);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const representativeDelivery = this.createFromForm();
    if (representativeDelivery.id !== undefined) {
      this.subscribeToSaveResponse(this.representativeDeliveryService.update(representativeDelivery));
    } else {
      this.subscribeToSaveResponse(this.representativeDeliveryService.create(representativeDelivery));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRepresentativeDelivery>>): void {
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

  protected updateForm(representativeDelivery: IRepresentativeDelivery): void {
    this.editForm.patchValue({
      id: representativeDelivery.id,
      dateAt: representativeDelivery.dateAt,
      details: representativeDelivery.details,
      total: representativeDelivery.total,
    });
  }

  protected createFromForm(): IRepresentativeDelivery {
    return {
      ...new RepresentativeDelivery(),
      id: this.editForm.get(['id'])!.value,
      dateAt: this.editForm.get(['dateAt'])!.value,
      details: this.editForm.get(['details'])!.value,
      total: this.editForm.get(['total'])!.value,
    };
  }
}
