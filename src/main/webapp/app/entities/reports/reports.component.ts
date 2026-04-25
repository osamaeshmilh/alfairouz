import {Component, OnInit} from '@angular/core';
import {formatDate} from '@angular/common';
import {IReferringCenter} from "../referring-center/referring-center.model";
import {IDoctor} from "../doctor/doctor.model";
import {ReferringCenterService} from "../referring-center/service/referring-center.service";
import {HttpResponse} from "@angular/common/http";
import {DoctorService} from "../doctor/service/doctor.service";
import {ReportsService} from "./reports.service";
import {AccountService} from "app/core/auth/account.service";

@Component({
  selector: 'jhi-reports',
  templateUrl: './reports.component.html',
})
export class ReportsComponent implements OnInit {
  isLoading: any;
  from: any;
  to: any;
  reportType: any;
  format = 'yyyy-MM-dd';
  locale = 'en-US';

  referringCenters: IReferringCenter[] = [];
  grossingDoctors: IDoctor[] = [];
  referringDoctors: IDoctor[] = [];
  pathologistDoctors: IDoctor[] = [];
  selectedId = "";

  constructor(
    protected reportsService: ReportsService,
    protected referringCenterService: ReferringCenterService,
    protected doctorService: DoctorService,
    protected accountService: AccountService
  ) {
  }

  ngOnInit(): void {

    this.doctorService
      .query({
        'doctorType.equals': 'GROSSING',
        size: 1000,
        sort: ['nameAr', 'asc']
      })
      .subscribe((res: HttpResponse<IDoctor[]>) => {
        this.grossingDoctors = res.body ?? [];
      });

    this.doctorService
      .query({
        'doctorType.equals': 'REFERRING',
        size: 1000,
        sort: ['nameAr', 'asc']
      })
      .subscribe((res: HttpResponse<IDoctor[]>) => {
        this.referringDoctors = res.body ?? [];
      });

    this.doctorService
      .query({
        'doctorType.equals': 'PATHOLOGIST',
        size: 1000,
        sort: ['nameAr', 'asc']
      })
      .subscribe((res: HttpResponse<IDoctor[]>) => {
        this.pathologistDoctors = res.body ?? [];
      });

    this.referringCenterService.query({
      size: 1000,
      sort: ['nameAr', 'asc']
    }).subscribe((res: HttpResponse<IReferringCenter[]>) => (this.referringCenters = res.body ?? []));

  }


  getReport(): void {
    const formattedFromDate = formatDate(this.from, this.format, this.locale);
    const formattedToDate = formatDate(this.to, this.format, this.locale);

    if (this.accountService.hasAnyAuthority('ROLE_RECEPTION') && !this.accountService.hasAnyAuthority('ROLE_ADMIN')) {
      const fromParts = formattedFromDate.split('-');
      const fromDate = new Date(Number(fromParts[0]), Number(fromParts[1]) - 1, Number(fromParts[2]));

      const toParts = formattedToDate.split('-');
      const toDate = new Date(Number(toParts[0]), Number(toParts[1]) - 1, Number(toParts[2]));

      const today = new Date();
      today.setHours(0, 0, 0, 0);

      const daysFromToday = (today.getTime() - fromDate.getTime()) / (1000 * 3600 * 24);
      const durationDays = (toDate.getTime() - fromDate.getTime()) / (1000 * 3600 * 24);

      if (daysFromToday > 7 || durationDays > 7) {
        alert('عذراً، يسمح لموظف الاستقبال استخراج تقارير بحد أقصى 7 أيام ولا تتجاوز 7 أيام للوراء.');
        return;
      }
    }
    
    if (this.reportType === 'ALL_SPECIMEN') {
      this.reportsService.downloadFileWithToken(this.from, this.to);
      // window.open(`/api/public/specimen/xlsx/criteria/?receivingDate.greaterThanOrEqual=${formattedFromDate}&receivingDate.lessThanOrEqual=${formattedToDate}`, '_blank');
    } else if (this.reportType === 'SPECIMEN_BY_PAYMENT_DATE') {
      window.open(`/api/public/specimen/xlsx/by-payment-date/?fromDate=${formattedFromDate}&toDate=${formattedToDate}`, '_blank');
    } else if (this.reportType === 'CASH_SPECIMEN') {
      window.open(`/api/public/specimen/xlsx/criteria/?referringCenterId.equals=${this.selectedId}&paymentType.equals=CASH&receivingDate.greaterThanOrEqual=${formattedFromDate}&receivingDate.lessThanOrEqual=${formattedToDate}`, '_blank');
    } else if (this.reportType === 'MONTHLY_SPECIMEN') {
      window.open(`/api/public/specimen/xlsx/criteria/?referringCenterId.equals=${this.selectedId}&paymentType.equals=MONTHLY&receivingDate.greaterThanOrEqual=${formattedFromDate}&receivingDate.lessThanOrEqual=${formattedToDate}`, '_blank');
    } else if (this.reportType === 'SPECIMEN_BY_GROSSING_DOCTOR') {
      window.open(`/api/public/specimen/xlsx/criteria/?grossingDoctorId.equals=${this.selectedId}&receivingDate.greaterThanOrEqual=${formattedFromDate}&receivingDate.lessThanOrEqual=${formattedToDate}`, '_blank');
    } else if (this.reportType === 'SPECIMEN_BY_PATHOLOGIST_DOCTOR') {
      window.open(`/api/public/specimen/xlsx/criteria/?pathologistDoctorId.equals=${this.selectedId}&receivingDate.greaterThanOrEqual=${formattedFromDate}&receivingDate.lessThanOrEqual=${formattedToDate}`, '_blank');
    } else if (this.reportType === 'SPECIMEN_BY_PATHOLOGIST_TWO_DOCTOR') {
      window.open(`/api/public/specimen/xlsx/criteria/?pathologistDoctorTwoId.equals=${this.selectedId}&receivingDate.greaterThanOrEqual=${formattedFromDate}&receivingDate.lessThanOrEqual=${formattedToDate}`, '_blank');
    } else if (this.reportType === 'SPECIMEN_BY_REFERRING_DOCTOR') {
      window.open(`/api/public/specimen/xlsx/criteria/?referringDoctorId.equals=${this.selectedId}&receivingDate.greaterThanOrEqual=${formattedFromDate}&receivingDate.lessThanOrEqual=${formattedToDate}`, '_blank');
    } else if (this.reportType === 'SPECIMEN_BY_REFERRING_CENTER') {
      window.open(`/api/public/specimen/xlsx/criteria/?referringCenterId.equals=${this.selectedId}&receivingDate.greaterThanOrEqual=${formattedFromDate}&receivingDate.lessThanOrEqual=${formattedToDate}`, '_blank');
    } else if (this.reportType === 'REPRESENTATIVE_DELIVERY') {
      window.open(`/api/public/representative-deliveries/xlsx/criteria/?dateAt.greaterThanOrEqual=${formattedFromDate}&dateAt.lessThanOrEqual=${formattedToDate}`, '_blank');
    } else if (this.reportType === 'PAP_SMEAR_SALES') {
      window.open(`/api/public/pap-smear-sales/xlsx/criteria/?dateAt.greaterThanOrEqual=${formattedFromDate}&dateAt.lessThanOrEqual=${formattedToDate}`, '_blank');
    } else if (this.reportType === 'EXPENSES') {
      window.open(`/api/public/expenses/xlsx/criteria/?dateAt.greaterThanOrEqual=${formattedFromDate}&dateAt.lessThanOrEqual=${formattedToDate}`, '_blank');
    }

    // eslint-disable-next-line no-console
    console.log(this.from);
    // eslint-disable-next-line no-console
    console.log(this.to);
    // eslint-disable-next-line no-console
    console.log(this.selectedId);
    // eslint-disable-next-line no-console
    console.log(this.reportType);
  }
}
