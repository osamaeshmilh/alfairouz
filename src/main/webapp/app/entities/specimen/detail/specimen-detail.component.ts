import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {ISpecimen} from '../specimen.model';
import {DataUtils} from 'app/core/util/data-util.service';
import {IReferringCenterPrice} from "../../referring-center-price/referring-center-price.model";
import {ISpecimenEdit} from "../../specimen-edit/specimen-edit.model";
import {HttpResponse} from "@angular/common/http";
import {SpecimenEditService} from "../../specimen-edit/service/specimen-edit.service";

@Component({
  selector: 'jhi-specimen-detail',
  templateUrl: './specimen-detail.component.html',
})
export class SpecimenDetailComponent implements OnInit {
  specimen: ISpecimen | null = null;
  specimenEdits: ISpecimenEdit[] = [];

  constructor(protected dataUtils: DataUtils,
              protected specimenEditService: SpecimenEditService,
              protected activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({specimen}) => {
      this.specimen = specimen;
    });

    this.specimenEditService
      .query({
        size: 1000,
        'specimenId.equals': this.specimen?.id,
      })
      .subscribe((res: HttpResponse<ISpecimenEdit[]>) => {
        this.specimenEdits = res.body ?? [];
      });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }

  getLabQr(): any {
    return this.specimen?.labQr;
  }

  getReciept(specimenId: any): void {
    const url = '/api/public/specimen/invoice/' + String(specimenId);
    window.open(url, '_blank');
  }

  getNormalReport(specimenId: any): void {
    const url = '/api/public/specimen/report/' + String(specimenId);
    window.open(url, '_blank');
  }

  getReport(specimenId: any): void {
    const url = '/api/public/specimen/report/' + String(specimenId);
    window.open(url, '_blank');
  }

}
