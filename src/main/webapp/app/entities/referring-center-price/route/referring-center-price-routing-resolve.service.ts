import {Injectable} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {Resolve, ActivatedRouteSnapshot, Router} from '@angular/router';
import {Observable, of, EMPTY} from 'rxjs';
import {mergeMap} from 'rxjs/operators';

import {IReferringCenterPrice, ReferringCenterPrice} from '../referring-center-price.model';
import {ReferringCenterPriceService} from '../service/referring-center-price.service';

@Injectable({providedIn: 'root'})
export class ReferringCenterPriceRoutingResolveService implements Resolve<IReferringCenterPrice> {
  constructor(protected service: ReferringCenterPriceService, protected router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<IReferringCenterPrice> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((referringCenterPrice: HttpResponse<ReferringCenterPrice>) => {
          if (referringCenterPrice.body) {
            return of(referringCenterPrice.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ReferringCenterPrice());
  }
}
