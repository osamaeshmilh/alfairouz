import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {IReferringCenter} from '../referring-center.model';
import {HttpResponse} from "@angular/common/http";
import {IDoctor} from "../../doctor/doctor.model";
import {ReferringCenterPriceService} from "../../referring-center-price/service/referring-center-price.service";
import {IReferringCenterPrice} from "../../referring-center-price/referring-center-price.model";
import {ReferringCenterService} from "../service/referring-center.service";

@Component({
  selector: 'jhi-referring-center-detail',
  templateUrl: './referring-center-detail.component.html',
})
export class ReferringCenterDetailComponent implements OnInit {
  referringCenter: IReferringCenter | null = null;
  referringCenterPrices: IReferringCenterPrice[] = [];
  isLoading = false;


  constructor(protected activatedRoute: ActivatedRoute,
              protected referringCenterService: ReferringCenterService,
              protected referringCenterPriceService: ReferringCenterPriceService) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({referringCenter}) => {
      this.referringCenter = referringCenter;
    });

    this.referringCenterPriceService
      .query({
        size: 1000,
        'referringCenterId.equals': this.referringCenter?.id,
      })
      .subscribe((res: HttpResponse<IReferringCenterPrice[]>) => {
        this.referringCenterPrices = res.body ?? [];
      });
  }

  resetReferringCenterPrices(referringCenterId: any): void {
    this.isLoading = true;
    this.referringCenterService
      .resetPrices(referringCenterId)
      .subscribe((res: HttpResponse<IReferringCenterPrice>) => {

        this.referringCenterPriceService
          .query({
            size: 1000,
            'referringCenterId.equals': this.referringCenter?.id,
          })
          .subscribe((res2: HttpResponse<IReferringCenterPrice[]>) => {
            this.referringCenterPrices = res2.body ?? [];
            this.isLoading = false;
          });
        this.isLoading = false;

      });
  }

  previousState(): void {
    window.history.back();
  }
}
