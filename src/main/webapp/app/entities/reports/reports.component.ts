import {Component, OnInit} from '@angular/core';
import {formatDate} from '@angular/common';
import {IReferringCenter} from "../referring-center/referring-center.model";
import {IDoctor} from "../doctor/doctor.model";
import {ReferringCenterService} from "../referring-center/service/referring-center.service";
import {HttpResponse} from "@angular/common/http";
import {DoctorService} from "../doctor/service/doctor.service";

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
    protected referringCenterService: ReferringCenterService,
    protected doctorService: DoctorService,
  ) {
  }

  ngOnInit(): void {

    this.doctorService
      .query({
        'doctorType.equals': 'GROSSING',
      })
      .subscribe((res: HttpResponse<IDoctor[]>) => {
        this.grossingDoctors = res.body ?? [];
      });

    this.doctorService
      .query({
        'doctorType.equals': 'REFERRING',
      })
      .subscribe((res: HttpResponse<IDoctor[]>) => {
        this.referringDoctors = res.body ?? [];
      });

    this.doctorService
      .query({
        'doctorType.equals': 'PATHOLOGIST',
      })
      .subscribe((res: HttpResponse<IDoctor[]>) => {
        this.pathologistDoctors = res.body ?? [];
      });

    this.referringCenterService.query().subscribe((res: HttpResponse<IReferringCenter[]>) => (this.referringCenters = res.body ?? []));

  }


  getReport(): void {
    const formattedFromDate = formatDate(this.from, this.format, this.locale);
    const formattedToDate = formatDate(this.to, this.format, this.locale);
    if (this.reportType === 'ALL_SPECIMEN') {
      window.open(`/api/public/specimen/xlsx/criteria/?receivingDate.greaterThanOrEqual=${formattedFromDate}&receivingDate.lessThanOrEqual=${formattedToDate}`, '_blank');
    } else if (this.reportType === 'SPECIMEN_BY_GROSSING_DOCTOR') {
      window.open(`/api/public/specimen/xlsx/criteria/?grossingDoctorId.equals=${this.selectedId}&receivingDate.greaterThanOrEqual=${formattedFromDate}&receivingDate.lessThanOrEqual=${formattedToDate}`, '_blank');
    } else if (this.reportType === 'SPECIMEN_BY_PATHOLOGIST_DOCTOR') {
      window.open(`/api/public/specimen/xlsx/criteria/?pathologistDoctorId.equals=${this.selectedId}&receivingDate.greaterThanOrEqual=${formattedFromDate}&receivingDate.lessThanOrEqual=${formattedToDate}`, '_blank');
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
