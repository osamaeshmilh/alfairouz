import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpecimen } from '../specimen.model';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { SpecimenService } from '../service/specimen.service';
import { SpecimenDeleteDialogComponent } from '../delete/specimen-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';
import {IOrgan} from "../../organ/organ.model";
import {map} from "rxjs/operators";
import {OrganService} from "../../organ/service/organ.service";

@Component({
  selector: 'jhi-specimen',
  templateUrl: './specimen.component.html',
  styleUrls: ['./specimen.component.css']
})
export class SpecimenComponent implements OnInit {
  specimen?: ISpecimen[];
  organs?: IOrgan[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 0;
  currentSearch: any = '';
  organId: any = '';

  constructor(
    protected organService: OrganService,
    protected specimenService: SpecimenService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: DataUtils,
    protected router: Router,
    protected modalService: NgbModal
  ) {
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 0;

    this.specimenService
      .query({
        'labRefNo.contains': this.currentSearch,
        'labQr.contains': this.currentSearch,
        'fileNo.contains': this.currentSearch,
        'notes.contains': this.currentSearch,
        'patientNameAr.contains': this.currentSearch,
        'organId.equals': this.organId,
        'isOr': true,
        page: pageToLoad,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<ISpecimen[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
        },
        error: () => {
          this.isLoading = false;
          this.onError();
        },
      });
  }

  ngOnInit(): void {
    this.organService
      .query({
        size: 1000,
        sort: ['name', 'asc'],
      }).subscribe((res: HttpResponse<IOrgan[]>) => (this.organs = res.body ?? []));

    this.handleNavigation();
  }

  filterByOrgan(minister: any): void {
    this.organId = minister;
    this.loadPage(0);
  }

  trackId(_index: number, item: ISpecimen): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(specimen: ISpecimen): void {
    const modalRef = this.modalService.open(SpecimenDeleteDialogComponent, {size: 'lg', backdrop: 'static'});
    modalRef.componentInstance.specimen = specimen;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }

  search(currentSearch: any): void {
    this.currentSearch = currentSearch;
    this.specimenService
      .query({
        //todo add organ list filter
        'labRefNo.contains': this.currentSearch,
        'labQr.contains': this.currentSearch,
        'fileNo.contains': this.currentSearch,
        'notes.contains': this.currentSearch,
        'patientNameAr.contains': this.currentSearch,
        'organId.equals': this.organId,
        'isOr': true,
        page: 0,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<ISpecimen[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, 0, true);
        },
        error: () => {
          this.isLoading = false;
          this.onError();
        },
      });
  }

  getXslx(): void {
    window.open('/api/public/specimen/xlsx/criteria/', '_blank');
  }

  getPdf(id: any): void {
    const url = '/api/public/specimen/report/' + String(id);
    window.open(url, '_blank');
  }

  getPdfColored(id: any): void {
    const url = '/api/public/specimen/report-colored/' + String(id);
    window.open(url, '_blank');
  }

  getDocx(id: any): void {
    const url = '/api/public/specimen/report/docx/' + String(id);
    window.open(url, '_blank');
  }

  getInvoice(id: any): void {
    const url = '/api/public/specimen/invoice/' + String(id);
    window.open(url, '_blank');
  }

  getDataPdf(id: any): void {
    const url = '/api/public/specimen/data-report/' + String(id);
    window.open(url, '_blank');
  }

  getStickerPdf(id: any): void {
    const url = '/api/public/specimen/sticker/' + String(id);
    window.open(url, '_blank');
  }

  openFileFromUrl(fileUrl: any): void {
    window.open(`/api/public/file/download/${String(fileUrl)}#zoom=85&scrollbar=0&toolbar=0&navpanes=0`, '_blank');
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      const pageNumber = +(page ?? 0);
      const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === ASC;
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    });
  }

  protected onSuccess(data: ISpecimen[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/specimen'], {
        queryParams: {
          'labRefNo.contains': this.currentSearch,
          'labQr.contains': this.currentSearch,
          'fileNo.contains': this.currentSearch,
          'notes.contains': this.currentSearch,
          'patientNameAr.contains': this.currentSearch,
          'organId.equals': this.organId,
          'isOr': true,
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
        },
      });
    }
    this.specimen = data ?? [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 0;
  }

}
