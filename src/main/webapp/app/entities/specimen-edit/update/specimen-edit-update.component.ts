import {Component, OnInit} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {FormBuilder} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {Observable} from 'rxjs';
import {finalize} from 'rxjs/operators';

import {ISpecimenEdit, SpecimenEdit} from '../specimen-edit.model';
import {SpecimenEditService} from '../service/specimen-edit.service';
import {SpecimenStatus} from 'app/entities/enumerations/specimen-status.model';

@Component({
  selector: 'jhi-specimen-edit-update',
  templateUrl: './specimen-edit-update.component.html',
})
export class SpecimenEditUpdateComponent implements OnInit {
  isSaving = false;
  specimenStatusValues = Object.keys(SpecimenStatus);

  editForm = this.fb.group({
    id: [],
    specimenId: [],
    labRefNo: [],
    specimenStatusFrom: [],
    specimenStatusTo: [],
    userType: [],
  });

  constructor(protected specimenEditService: SpecimenEditService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({specimenEdit}) => {
      this.updateForm(specimenEdit);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const specimenEdit = this.createFromForm();
    if (specimenEdit.id !== undefined) {
      this.subscribeToSaveResponse(this.specimenEditService.update(specimenEdit));
    } else {
      this.subscribeToSaveResponse(this.specimenEditService.create(specimenEdit));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecimenEdit>>): void {
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

  protected updateForm(specimenEdit: ISpecimenEdit): void {
    this.editForm.patchValue({
      id: specimenEdit.id,
      specimenId: specimenEdit.specimenId,
      labRefNo: specimenEdit.labRefNo,
      specimenStatusFrom: specimenEdit.specimenStatusFrom,
      specimenStatusTo: specimenEdit.specimenStatusTo,
      userType: specimenEdit.userType,
    });
  }

  protected createFromForm(): ISpecimenEdit {
    return {
      ...new SpecimenEdit(),
      id: this.editForm.get(['id'])!.value,
      specimenId: this.editForm.get(['specimenId'])!.value,
      labRefNo: this.editForm.get(['labRefNo'])!.value,
      specimenStatusFrom: this.editForm.get(['specimenStatusFrom'])!.value,
      specimenStatusTo: this.editForm.get(['specimenStatusTo'])!.value,
      userType: this.editForm.get(['userType'])!.value,
    };
  }
}
