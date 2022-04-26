import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPatient, Patient } from '../patient.model';
import { PatientService } from '../service/patient.service';
import { Gender } from 'app/entities/enumerations/gender.model';

@Component({
  selector: 'jhi-patient-update',
  templateUrl: './patient-update.component.html',
})
export class PatientUpdateComponent implements OnInit {
  isSaving = false;
  genderValues = Object.keys(Gender);

  editForm = this.fb.group({
    id: [],
    name: [],
    nameAr: [],
    birthDate: [],
    gender: [],
    mobileNumber: [],
    nationality: [],
    motherName: [],
    address: [],
    notes: [],
  });

  constructor(protected patientService: PatientService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ patient }) => {
      this.updateForm(patient);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const patient = this.createFromForm();
    if (patient.id !== undefined) {
      this.subscribeToSaveResponse(this.patientService.update(patient));
    } else {
      this.subscribeToSaveResponse(this.patientService.create(patient));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatient>>): void {
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

  protected updateForm(patient: IPatient): void {
    this.editForm.patchValue({
      id: patient.id,
      name: patient.name,
      nameAr: patient.nameAr,
      birthDate: patient.birthDate,
      gender: patient.gender,
      mobileNumber: patient.mobileNumber,
      nationality: patient.nationality,
      motherName: patient.motherName,
      address: patient.address,
      notes: patient.notes,
    });
  }

  protected createFromForm(): IPatient {
    return {
      ...new Patient(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      nameAr: this.editForm.get(['nameAr'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      mobileNumber: this.editForm.get(['mobileNumber'])!.value,
      nationality: this.editForm.get(['nationality'])!.value,
      motherName: this.editForm.get(['motherName'])!.value,
      address: this.editForm.get(['address'])!.value,
      notes: this.editForm.get(['notes'])!.value,
    };
  }
}
