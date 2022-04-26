import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IReferringCenter, ReferringCenter } from '../referring-center.model';
import { ReferringCenterService } from '../service/referring-center.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ContractType } from 'app/entities/enumerations/contract-type.model';

@Component({
  selector: 'jhi-referring-center-update',
  templateUrl: './referring-center-update.component.html',
})
export class ReferringCenterUpdateComponent implements OnInit {
  isSaving = false;
  contractTypeValues = Object.keys(ContractType);

  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    nameAr: [],
    mobileNumber: [],
    email: [],
    onlineReport: [],
    contractType: [],
    discount: [],
    internalUser: [],
  });

  constructor(
    protected referringCenterService: ReferringCenterService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ referringCenter }) => {
      this.updateForm(referringCenter);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const referringCenter = this.createFromForm();
    if (referringCenter.id !== undefined) {
      this.subscribeToSaveResponse(this.referringCenterService.update(referringCenter));
    } else {
      this.subscribeToSaveResponse(this.referringCenterService.create(referringCenter));
    }
  }

  trackUserById(_index: number, item: IUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReferringCenter>>): void {
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

  protected updateForm(referringCenter: IReferringCenter): void {
    this.editForm.patchValue({
      id: referringCenter.id,
      name: referringCenter.name,
      nameAr: referringCenter.nameAr,
      mobileNumber: referringCenter.mobileNumber,
      email: referringCenter.email,
      onlineReport: referringCenter.onlineReport,
      contractType: referringCenter.contractType,
      discount: referringCenter.discount,
      internalUser: referringCenter.internalUser,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, referringCenter.internalUser);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('internalUser')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): IReferringCenter {
    return {
      ...new ReferringCenter(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      nameAr: this.editForm.get(['nameAr'])!.value,
      mobileNumber: this.editForm.get(['mobileNumber'])!.value,
      email: this.editForm.get(['email'])!.value,
      onlineReport: this.editForm.get(['onlineReport'])!.value,
      contractType: this.editForm.get(['contractType'])!.value,
      discount: this.editForm.get(['discount'])!.value,
      internalUser: this.editForm.get(['internalUser'])!.value,
    };
  }
}
