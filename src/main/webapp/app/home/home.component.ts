import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';

import {AccountService} from 'app/core/auth/account.service';
import {Account} from 'app/core/auth/account.model';
import {SpecimenStatus} from "../entities/enumerations/specimen-status.model";
import {SpecimenService} from "../entities/specimen/service/specimen.service";
import {HttpResponse} from "@angular/common/http";
import {ISpecimen} from "../entities/specimen/specimen.model";
import swal from "sweetalert2";

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  specimen: any;
  isLoading: any;
  currentSearch: any;

  statusOrder: SpecimenStatus[] = [
    SpecimenStatus.RECEIVED,
    SpecimenStatus.GROSSING,
    SpecimenStatus.PROCESSING,
    SpecimenStatus.DIAGNOSING,
    SpecimenStatus.TYPING,
    SpecimenStatus.REVISION,
    SpecimenStatus.READY,
  ];

  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService,
              protected specimenService: SpecimenService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }


  previousState(): void {
    window.history.back();
  }

  clear(): void {
    this.currentSearch = '';
    this.specimen = null;
  }

  search(): void {
    this.specimen = null;
    this.isLoading = true;
    this.specimenService.findPublicByQr(this.currentSearch).subscribe(
      (response: HttpResponse<ISpecimen>) => {
        this.specimen = response.body;
        this.currentSearch = '';
        this.isLoading = false;
      },
      () => {
        swal
          .fire({
            icon: 'error',
            title: 'خطأ !',
            text: 'رقم تسجيل غير صحيح!',
            confirmButtonText: 'حسنا',
          })
          .then(() => {
            this.currentSearch = '';
            this.isLoading = false;
          });
      }
    );
  }

  isCompleted(status: any): boolean {
    const currentStatusIndex = this.statusOrder.indexOf(this.specimen.specimenStatus);
    const statusIndex = this.statusOrder.indexOf(status);
    return statusIndex <= currentStatusIndex;
  }

  getPdf(id: any): void {
    const url = '/api/public/specimen/report-colored/' + String(id);
    window.open(url, '_blank');
  }
}
