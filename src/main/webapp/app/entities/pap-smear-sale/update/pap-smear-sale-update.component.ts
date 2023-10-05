import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPapSmearSale, PapSmearSale } from '../pap-smear-sale.model';
import { PapSmearSaleService } from '../service/pap-smear-sale.service';
import { IReferringCenter } from 'app/entities/referring-center/referring-center.model';
import { ReferringCenterService } from 'app/entities/referring-center/service/referring-center.service';
import { PaymentType } from 'app/entities/enumerations/payment-type.model';

@Component({
  selector: 'jhi-pap-smear-sale-update',
  templateUrl: './pap-smear-sale-update.component.html',
})
export class PapSmearSaleUpdateComponent implements OnInit {
  isSaving = false;
  paymentTypeValues = Object.keys(PaymentType);

  referringCentersSharedCollection: IReferringCenter[] = [];

  editForm = this.fb.group({
    id: [],
    dateAt: [],
    details: [],
    paymentType: [],
    quantity: [],
    total: [],
    referringCenter: [],
  });

  constructor(
    protected papSmearSaleService: PapSmearSaleService,
    protected referringCenterService: ReferringCenterService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ papSmearSale }) => {
      this.updateForm(papSmearSale);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const papSmearSale = this.createFromForm();
    if (papSmearSale.id !== undefined) {
      this.subscribeToSaveResponse(this.papSmearSaleService.update(papSmearSale));
    } else {
      this.subscribeToSaveResponse(this.papSmearSaleService.create(papSmearSale));
    }
  }

  trackReferringCenterById(_index: number, item: IReferringCenter): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPapSmearSale>>): void {
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

  protected updateForm(papSmearSale: IPapSmearSale): void {
    this.editForm.patchValue({
      id: papSmearSale.id,
      dateAt: papSmearSale.dateAt,
      details: papSmearSale.details,
      paymentType: papSmearSale.paymentType,
      quantity: papSmearSale.quantity,
      total: papSmearSale.total,
      referringCenter: papSmearSale.referringCenter,
    });

    this.referringCentersSharedCollection = this.referringCenterService.addReferringCenterToCollectionIfMissing(
      this.referringCentersSharedCollection,
      papSmearSale.referringCenter
    );
  }

  protected loadRelationshipsOptions(): void {
    this.referringCenterService
      .query({
        size: 300,
        sort: ['nameAr', 'asc']
      })
      .pipe(map((res: HttpResponse<IReferringCenter[]>) => res.body ?? []))
      .pipe(
        map((referringCenters: IReferringCenter[]) =>
          this.referringCenterService.addReferringCenterToCollectionIfMissing(referringCenters, this.editForm.get('referringCenter')!.value)
        )
      )
      .subscribe((referringCenters: IReferringCenter[]) => (this.referringCentersSharedCollection = referringCenters));
  }

  protected createFromForm(): IPapSmearSale {
    return {
      ...new PapSmearSale(),
      id: this.editForm.get(['id'])!.value,
      dateAt: this.editForm.get(['dateAt'])!.value,
      details: this.editForm.get(['details'])!.value,
      paymentType: this.editForm.get(['paymentType'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      total: this.editForm.get(['total'])!.value,
      referringCenter: this.editForm.get(['referringCenter'])!.value,
    };
  }
}
