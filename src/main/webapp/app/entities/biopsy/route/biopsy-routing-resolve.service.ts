import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBiopsy, Biopsy } from '../biopsy.model';
import { BiopsyService } from '../service/biopsy.service';

@Injectable({ providedIn: 'root' })
export class BiopsyRoutingResolveService implements Resolve<IBiopsy> {
  constructor(protected service: BiopsyService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBiopsy> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((biopsy: HttpResponse<Biopsy>) => {
          if (biopsy.body) {
            return of(biopsy.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Biopsy());
  }
}
