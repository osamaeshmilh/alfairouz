import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReferringCenter, ReferringCenter } from '../referring-center.model';
import { ReferringCenterService } from '../service/referring-center.service';

@Injectable({ providedIn: 'root' })
export class ReferringCenterRoutingResolveService implements Resolve<IReferringCenter> {
  constructor(protected service: ReferringCenterService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReferringCenter> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((referringCenter: HttpResponse<ReferringCenter>) => {
          if (referringCenter.body) {
            return of(referringCenter.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ReferringCenter());
  }
}
