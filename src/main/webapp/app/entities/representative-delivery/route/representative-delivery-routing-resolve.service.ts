import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRepresentativeDelivery, RepresentativeDelivery } from '../representative-delivery.model';
import { RepresentativeDeliveryService } from '../service/representative-delivery.service';

@Injectable({ providedIn: 'root' })
export class RepresentativeDeliveryRoutingResolveService implements Resolve<IRepresentativeDelivery> {
  constructor(protected service: RepresentativeDeliveryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRepresentativeDelivery> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((representativeDelivery: HttpResponse<RepresentativeDelivery>) => {
          if (representativeDelivery.body) {
            return of(representativeDelivery.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RepresentativeDelivery());
  }
}
