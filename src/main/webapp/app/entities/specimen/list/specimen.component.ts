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

@Component({
  selector: 'jhi-specimen',
  templateUrl: './specimen.component.html',
})
export class SpecimenComponent implements OnInit {
  specimen?: ISpecimen[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 0;
  currentSearch: any = '';

  constructor(
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
    this.handleNavigation();
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
        'labRefNo.contains': this.currentSearch,
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
