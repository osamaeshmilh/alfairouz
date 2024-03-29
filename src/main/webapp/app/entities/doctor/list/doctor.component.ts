import {Component, OnInit} from '@angular/core';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {combineLatest} from 'rxjs';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {IDoctor} from '../doctor.model';

import {ASC, DESC, ITEMS_PER_PAGE, SORT} from 'app/config/pagination.constants';
import {DoctorService} from '../service/doctor.service';
import {DoctorDeleteDialogComponent} from '../delete/doctor-delete-dialog.component';
import {DoctorType} from "../../enumerations/doctor-type.model";

@Component({
  selector: 'jhi-doctor',
  templateUrl: './doctor.component.html',
})
export class DoctorComponent implements OnInit {
  doctors?: IDoctor[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 0;

  constructor(
    protected doctorService: DoctorService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 0;

    this.doctorService
      .query({
        page: pageToLoad,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<IDoctor[]>) => {
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

  trackId(_index: number, item: IDoctor): number {
    return item.id!;
  }

  delete(doctor: IDoctor): void {
    const modalRef = this.modalService.open(DoctorDeleteDialogComponent, {size: 'lg', backdrop: 'static'});
    modalRef.componentInstance.doctor = doctor;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }

  getSpecimenXslx(doctor: IDoctor): void {
    const doctorId = String(doctor.id);
    if (doctor.doctorType === DoctorType.GROSSING) {
      window.open(`/api/public/specimen/xlsx/criteria/?grossingDoctorId.equals=${doctorId}`, '_blank');
    } else if (doctor.doctorType === DoctorType.PATHOLOGIST) {
      window.open(`/api/public/specimen/xlsx/criteria/?pathologistDoctorId.equals=${doctorId}`, '_blank');
    } else if (doctor.doctorType === DoctorType.REFERRING) {
      window.open(`/api/public/specimen/xlsx/criteria/?referringDoctorId.equals=${doctorId}`, '_blank');
    }
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

  protected onSuccess(data: IDoctor[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/doctor'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
        },
      });
    }
    this.doctors = data ?? [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 0;
  }


}
