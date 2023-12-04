import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {debounceTime, distinctUntilChanged, Observable, Subject, switchMap} from 'rxjs';
import {finalize, map} from 'rxjs/operators';

import { IBlockWithdraw, BlockWithdraw } from '../block-withdraw.model';
import { BlockWithdrawService } from '../service/block-withdraw.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ISpecimen } from 'app/entities/specimen/specimen.model';
import { SpecimenService } from 'app/entities/specimen/service/specimen.service';
import {WithdrawType} from 'app/entities/enumerations/withdraw-type.model';
import {IPatient} from "../../patient/patient.model";

@Component({
  selector: 'jhi-block-withdraw-update',
  templateUrl: './block-withdraw-update.component.html',
})
export class BlockWithdrawUpdateComponent implements OnInit {
  isSaving = false;
  withdrawTypeValues = Object.keys(WithdrawType);

  specimenSharedCollection: ISpecimen[] = [];

  editForm = this.fb.group({
    id: [],
    personName: [],
    personId: [],
    quantity: [],
    withdrawDate: [],
    withdrawType: [],
    pdfFile: [],
    pdfFileContentType: [],
    pdfFileUrl: [],
    specimen: [],
  });

  private searchTerms = new Subject<string>();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected blockWithdrawService: BlockWithdrawService,
    protected specimenService: SpecimenService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ blockWithdraw }) => {
      this.updateForm(blockWithdraw);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('alfairouzApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const blockWithdraw = this.createFromForm();
    if (blockWithdraw.id !== undefined) {
      this.subscribeToSaveResponse(this.blockWithdrawService.update(blockWithdraw));
    } else {
      this.subscribeToSaveResponse(this.blockWithdrawService.create(blockWithdraw));
    }
  }

  trackSpecimenById(_index: number, item: ISpecimen): number {
    return item.id!;
  }


  onSpecimenSearch(term: string): void {
    this.searchTerms.next(term);
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBlockWithdraw>>): void {
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

  protected updateForm(blockWithdraw: IBlockWithdraw): void {
    this.editForm.patchValue({
      id: blockWithdraw.id,
      personName: blockWithdraw.personName,
      personId: blockWithdraw.personId,
      quantity: blockWithdraw.quantity,
      withdrawDate: blockWithdraw.withdrawDate,
      withdrawType: blockWithdraw.withdrawType,
      pdfFile: blockWithdraw.pdfFile,
      pdfFileContentType: blockWithdraw.pdfFileContentType,
      pdfFileUrl: blockWithdraw.pdfFileUrl,
      specimen: blockWithdraw.specimen,
    });

    this.specimenSharedCollection = this.specimenService.addSpecimenToCollectionIfMissing(
      this.specimenSharedCollection,
      blockWithdraw.specimen
    );
  }

  protected loadRelationshipsOptions(): void {
    this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => this.specimenService.query({
        "labRefNo.contains": term,
        sort: ['labRefNo', 'asc'],
        size: 1000
      }))
    ).subscribe((res: HttpResponse<ISpecimen[]>) => {
      this.specimenSharedCollection = res.body ?? [];
    });

    this.specimenService
      .query()
      .pipe(map((res: HttpResponse<ISpecimen[]>) => res.body ?? []))
      .pipe(
        map((specimen: ISpecimen[]) =>
          this.specimenService.addSpecimenToCollectionIfMissing(specimen, this.editForm.get('specimen')!.value)
        )
      )
      .subscribe((specimen: ISpecimen[]) => (this.specimenSharedCollection = specimen));
  }

  protected createFromForm(): IBlockWithdraw {
    return {
      ...new BlockWithdraw(),
      id: this.editForm.get(['id'])!.value,
      personName: this.editForm.get(['personName'])!.value,
      personId: this.editForm.get(['personId'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      withdrawDate: this.editForm.get(['withdrawDate'])!.value,
      withdrawType: this.editForm.get(['withdrawType'])!.value,
      pdfFileContentType: this.editForm.get(['pdfFileContentType'])!.value,
      pdfFile: this.editForm.get(['pdfFile'])!.value,
      pdfFileUrl: this.editForm.get(['pdfFileUrl'])!.value,
      specimen: this.editForm.get(['specimen'])!.value,
    };
  }

}
