import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IExtra, Extra } from '../extra.model';
import { ExtraService } from '../service/extra.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { ExtraAction } from 'app/entities/enumerations/extra-action.model';

@Component({
  selector: 'jhi-extra-update',
  templateUrl: './extra-update.component.html',
})
export class ExtraUpdateComponent implements OnInit {
  isSaving = false;
  extraActionValues = Object.keys(ExtraAction);

  employeesSharedCollection: IEmployee[] = [];

  editForm = this.fb.group({
    id: [],
    dateAt: [],
    extraAction: [],
    details: [],
    amount: [],
    employee: [],
  });

  constructor(
    protected extraService: ExtraService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ extra }) => {
      this.updateForm(extra);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const extra = this.createFromForm();
    if (extra.id !== undefined) {
      this.subscribeToSaveResponse(this.extraService.update(extra));
    } else {
      this.subscribeToSaveResponse(this.extraService.create(extra));
    }
  }

  trackEmployeeById(_index: number, item: IEmployee): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExtra>>): void {
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

  protected updateForm(extra: IExtra): void {
    this.editForm.patchValue({
      id: extra.id,
      dateAt: extra.dateAt,
      extraAction: extra.extraAction,
      details: extra.details,
      amount: extra.amount,
      employee: extra.employee,
    });

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing(this.employeesSharedCollection, extra.employee);
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing(employees, this.editForm.get('employee')!.value)
        )
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));
  }

  protected createFromForm(): IExtra {
    return {
      ...new Extra(),
      id: this.editForm.get(['id'])!.value,
      dateAt: this.editForm.get(['dateAt'])!.value,
      extraAction: this.editForm.get(['extraAction'])!.value,
      details: this.editForm.get(['details'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }
}
