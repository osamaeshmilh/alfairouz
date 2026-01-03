import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {HttpResponse} from '@angular/common/http';
import {SpecimenService} from "../service/specimen.service";
import {ISpecimen} from "../specimen.model";
import {SpecimenStatus} from "../../enumerations/specimen-status.model";

@Component({
  selector: 'jhi-specimen-query',
  templateUrl: './specimen-query.component.html',
})
export class SpecimenQueryComponent {
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

  constructor(protected activatedRoute: ActivatedRoute, private specimenService: SpecimenService) {
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
        alert('رقم تسجيل غير صحيح!');
        this.currentSearch = '';
        this.isLoading = false;
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
