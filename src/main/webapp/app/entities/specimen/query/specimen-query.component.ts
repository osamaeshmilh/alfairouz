import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {HttpResponse} from '@angular/common/http';
import swal from 'sweetalert2';
import {SpecimenService} from "../service/specimen.service";
import {ISpecimen} from "../specimen.model";

@Component({
  selector: 'jhi-specimen-query',
  templateUrl: './specimen-query.component.html',
})
export class SpecimenQueryComponent {
  specimen: any;
  isLoading: any;
  currentSearch: any;

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
    this.specimenService.findPublic(this.currentSearch).subscribe(
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
}
