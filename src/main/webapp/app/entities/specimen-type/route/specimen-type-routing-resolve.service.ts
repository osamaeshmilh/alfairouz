import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISpecimenType, SpecimenType } from '../specimen-type.model';
import { SpecimenTypeService } from '../service/specimen-type.service';

@Injectable({ providedIn: 'root' })
export class SpecimenTypeRoutingResolveService implements Resolve<ISpecimenType> {
  constructor(protected service: SpecimenTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISpecimenType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((specimenType: HttpResponse<SpecimenType>) => {
          if (specimenType.body) {
            return of(specimenType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SpecimenType());
  }
}
