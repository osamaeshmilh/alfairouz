import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDoctor, Doctor } from '../doctor.model';
import { DoctorService } from '../service/doctor.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { DoctorType } from 'app/entities/enumerations/doctor-type.model';

@Component({
  selector: 'jhi-doctor-update',
  templateUrl: './doctor-update.component.html',
})
export class DoctorUpdateComponent implements OnInit {
  isSaving = false;
  doctorTypeValues = Object.keys(DoctorType);

  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    nameAr: [],
    description: [],
    mobileNo: [],
    email: [],
    onlineReport: [],
    emailReport: [],
    percentage: [],
    doctorType: [],
    internalUser: [],
    newPassword: []
  });

  constructor(
    protected doctorService: DoctorService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ doctor }) => {
      this.updateForm(doctor);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const doctor = this.createFromForm();
    if (doctor.id !== undefined) {
      this.subscribeToSaveResponse(this.doctorService.update(doctor));
    } else {
      this.subscribeToSaveResponse(this.doctorService.create(doctor));
    }
  }

  trackUserById(_index: number, item: IUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoctor>>): void {
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

  protected updateForm(doctor: IDoctor): void {
    this.editForm.patchValue({
      id: doctor.id,
      name: doctor.name,
      nameAr: doctor.nameAr,
      description: doctor.description,
      mobileNo: doctor.mobileNo,
      email: doctor.email,
      onlineReport: doctor.onlineReport,
      emailReport: doctor.emailReport,
      percentage: doctor.percentage,
      doctorType: doctor.doctorType,
      internalUser: doctor.internalUser,
      newPassword: doctor.newPassword
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, doctor.internalUser);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('internalUser')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): IDoctor {
    return {
      ...new Doctor(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      nameAr: this.editForm.get(['nameAr'])!.value,
      description: this.editForm.get(['description'])!.value,
      mobileNo: this.editForm.get(['mobileNo'])!.value,
      email: this.editForm.get(['email'])!.value,
      onlineReport: this.editForm.get(['onlineReport'])!.value,
      emailReport: this.editForm.get(['emailReport'])!.value,
      percentage: this.editForm.get(['percentage'])!.value,
      doctorType: this.editForm.get(['doctorType'])!.value,
      internalUser: this.editForm.get(['internalUser'])!.value,
      newPassword: this.editForm.get(['newPassword'])!.value,
    };
  }
}
