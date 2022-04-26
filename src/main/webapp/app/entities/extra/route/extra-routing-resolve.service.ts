import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IExtra, Extra } from '../extra.model';
import { ExtraService } from '../service/extra.service';

@Injectable({ providedIn: 'root' })
export class ExtraRoutingResolveService implements Resolve<IExtra> {
  constructor(protected service: ExtraService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExtra> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((extra: HttpResponse<Extra>) => {
          if (extra.body) {
            return of(extra.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Extra());
  }
}
