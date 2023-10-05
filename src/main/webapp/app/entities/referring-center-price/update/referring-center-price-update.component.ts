import {Component, OnInit} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {FormBuilder} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {Observable} from 'rxjs';
import {finalize, map} from 'rxjs/operators';

import {IReferringCenterPrice, ReferringCenterPrice} from '../referring-center-price.model';
import {ReferringCenterPriceService} from '../service/referring-center-price.service';
import {ISpecimenType} from 'app/entities/specimen-type/specimen-type.model';
import {SpecimenTypeService} from 'app/entities/specimen-type/service/specimen-type.service';
import {ISize} from 'app/entities/size/size.model';
import {SizeService} from 'app/entities/size/service/size.service';
import {IReferringCenter} from 'app/entities/referring-center/referring-center.model';
import {ReferringCenterService} from 'app/entities/referring-center/service/referring-center.service';
import {ContractType} from 'app/entities/enumerations/contract-type.model';

@Component({
  selector: 'jhi-referring-center-price-update',
  templateUrl: './referring-center-price-update.component.html',
})
export class ReferringCenterPriceUpdateComponent implements OnInit {
  isSaving = false;
  contractTypeValues = Object.keys(ContractType);

  specimenTypesSharedCollection: ISpecimenType[] = [];
  sizesSharedCollection: ISize[] = [];
  referringCentersSharedCollection: IReferringCenter[] = [];

  editForm = this.fb.group({
    id: [],
    pricingType: [],
    price: [],
    specimenType: [],
    size: [],
    referringCenter: [],
  });

  constructor(
    protected referringCenterPriceService: ReferringCenterPriceService,
    protected specimenTypeService: SpecimenTypeService,
    protected sizeService: SizeService,
    protected referringCenterService: ReferringCenterService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({referringCenterPrice}) => {
      this.updateForm(referringCenterPrice);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const referringCenterPrice = this.createFromForm();
    if (referringCenterPrice.id !== undefined) {
      this.subscribeToSaveResponse(this.referringCenterPriceService.update(referringCenterPrice));
    } else {
      this.subscribeToSaveResponse(this.referringCenterPriceService.create(referringCenterPrice));
    }
  }

  trackSpecimenTypeById(index: number, item: ISpecimenType): number {
    return item.id!;
  }

  trackSizeById(index: number, item: ISize): number {
    return item.id!;
  }

  trackReferringCenterById(index: number, item: IReferringCenter): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReferringCenterPrice>>): void {
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

  protected updateForm(referringCenterPrice: IReferringCenterPrice): void {
    this.editForm.patchValue({
      id: referringCenterPrice.id,
      pricingType: referringCenterPrice.pricingType,
      price: referringCenterPrice.price,
      specimenType: referringCenterPrice.specimenType,
      size: referringCenterPrice.size,
      referringCenter: referringCenterPrice.referringCenter,
    });

    this.specimenTypesSharedCollection = this.specimenTypeService.addSpecimenTypeToCollectionIfMissing(
      this.specimenTypesSharedCollection,
      referringCenterPrice.specimenType
    );
    this.sizesSharedCollection = this.sizeService.addSizeToCollectionIfMissing(this.sizesSharedCollection, referringCenterPrice.size);
    this.referringCentersSharedCollection = this.referringCenterService.addReferringCenterToCollectionIfMissing(
      this.referringCentersSharedCollection,
      referringCenterPrice.referringCenter
    );
  }

  protected loadRelationshipsOptions(): void {
    this.specimenTypeService
      .query()
      .pipe(map((res: HttpResponse<ISpecimenType[]>) => res.body ?? []))
      .pipe(
        map((specimenTypes: ISpecimenType[]) =>
          this.specimenTypeService.addSpecimenTypeToCollectionIfMissing(specimenTypes, this.editForm.get('specimenType')!.value)
        )
      )
      .subscribe((specimenTypes: ISpecimenType[]) => (this.specimenTypesSharedCollection = specimenTypes));

    this.sizeService
      .query()
      .pipe(map((res: HttpResponse<ISize[]>) => res.body ?? []))
      .pipe(map((sizes: ISize[]) => this.sizeService.addSizeToCollectionIfMissing(sizes, this.editForm.get('size')!.value)))
      .subscribe((sizes: ISize[]) => (this.sizesSharedCollection = sizes));

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

  protected createFromForm(): IReferringCenterPrice {
    return {
      ...new ReferringCenterPrice(),
      id: this.editForm.get(['id'])!.value,
      pricingType: this.editForm.get(['pricingType'])!.value,
      price: this.editForm.get(['price'])!.value,
      specimenType: this.editForm.get(['specimenType'])!.value,
      size: this.editForm.get(['size'])!.value,
      referringCenter: this.editForm.get(['referringCenter'])!.value,
    };
  }
}
