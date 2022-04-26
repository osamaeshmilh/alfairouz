import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IReceipt, Receipt } from '../receipt.model';
import { ReceiptService } from '../service/receipt.service';
import { ISpecimen } from 'app/entities/specimen/specimen.model';
import { SpecimenService } from 'app/entities/specimen/service/specimen.service';
import { IPatient } from 'app/entities/patient/patient.model';
import { PatientService } from 'app/entities/patient/service/patient.service';

@Component({
  selector: 'jhi-receipt-update',
  templateUrl: './receipt-update.component.html',
})
export class ReceiptUpdateComponent implements OnInit {
  isSaving = false;

  specimenSharedCollection: ISpecimen[] = [];
  patientsSharedCollection: IPatient[] = [];

  editForm = this.fb.group({
    id: [],
    dateAt: [],
    details: [],
    paymentMethod: [],
    price: [],
    paid: [],
    notPaid: [],
    specimen: [],
    patient: [],
  });

  constructor(
    protected receiptService: ReceiptService,
    protected specimenService: SpecimenService,
    protected patientService: PatientService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ receipt }) => {
      this.updateForm(receipt);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const receipt = this.createFromForm();
    if (receipt.id !== undefined) {
      this.subscribeToSaveResponse(this.receiptService.update(receipt));
    } else {
      this.subscribeToSaveResponse(this.receiptService.create(receipt));
    }
  }

  trackSpecimenById(_index: number, item: ISpecimen): number {
    return item.id!;
  }

  trackPatientById(_index: number, item: IPatient): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReceipt>>): void {
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

  protected updateForm(receipt: IReceipt): void {
    this.editForm.patchValue({
      id: receipt.id,
      dateAt: receipt.dateAt,
      details: receipt.details,
      paymentMethod: receipt.paymentMethod,
      price: receipt.price,
      paid: receipt.paid,
      notPaid: receipt.notPaid,
      specimen: receipt.specimen,
      patient: receipt.patient,
    });

    this.specimenSharedCollection = this.specimenService.addSpecimenToCollectionIfMissing(this.specimenSharedCollection, receipt.specimen);
    this.patientsSharedCollection = this.patientService.addPatientToCollectionIfMissing(this.patientsSharedCollection, receipt.patient);
  }

  protected loadRelationshipsOptions(): void {
    this.specimenService
      .query()
      .pipe(map((res: HttpResponse<ISpecimen[]>) => res.body ?? []))
      .pipe(
        map((specimen: ISpecimen[]) =>
          this.specimenService.addSpecimenToCollectionIfMissing(specimen, this.editForm.get('specimen')!.value)
        )
      )
      .subscribe((specimen: ISpecimen[]) => (this.specimenSharedCollection = specimen));

    this.patientService
      .query()
      .pipe(map((res: HttpResponse<IPatient[]>) => res.body ?? []))
      .pipe(
        map((patients: IPatient[]) => this.patientService.addPatientToCollectionIfMissing(patients, this.editForm.get('patient')!.value))
      )
      .subscribe((patients: IPatient[]) => (this.patientsSharedCollection = patients));
  }

  protected createFromForm(): IReceipt {
    return {
      ...new Receipt(),
      id: this.editForm.get(['id'])!.value,
      dateAt: this.editForm.get(['dateAt'])!.value,
      details: this.editForm.get(['details'])!.value,
      paymentMethod: this.editForm.get(['paymentMethod'])!.value,
      price: this.editForm.get(['price'])!.value,
      paid: this.editForm.get(['paid'])!.value,
      notPaid: this.editForm.get(['notPaid'])!.value,
      specimen: this.editForm.get(['specimen'])!.value,
      patient: this.editForm.get(['patient'])!.value,
    };
  }
}
