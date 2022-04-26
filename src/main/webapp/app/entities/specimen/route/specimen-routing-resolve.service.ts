import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISpecimen, Specimen } from '../specimen.model';
import { SpecimenService } from '../service/specimen.service';

@Injectable({ providedIn: 'root' })
export class SpecimenRoutingResolveService implements Resolve<ISpecimen> {
  constructor(protected service: SpecimenService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISpecimen> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((specimen: HttpResponse<Specimen>) => {
          if (specimen.body) {
            return of(specimen.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Specimen());
  }
}
