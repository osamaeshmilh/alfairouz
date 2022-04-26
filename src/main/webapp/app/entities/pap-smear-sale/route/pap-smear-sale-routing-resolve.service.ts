import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPapSmearSale, PapSmearSale } from '../pap-smear-sale.model';
import { PapSmearSaleService } from '../service/pap-smear-sale.service';

@Injectable({ providedIn: 'root' })
export class PapSmearSaleRoutingResolveService implements Resolve<IPapSmearSale> {
  constructor(protected service: PapSmearSaleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPapSmearSale> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((papSmearSale: HttpResponse<PapSmearSale>) => {
          if (papSmearSale.body) {
            return of(papSmearSale.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PapSmearSale());
  }
}
