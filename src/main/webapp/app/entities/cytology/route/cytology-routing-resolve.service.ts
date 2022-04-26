import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICytology, Cytology } from '../cytology.model';
import { CytologyService } from '../service/cytology.service';

@Injectable({ providedIn: 'root' })
export class CytologyRoutingResolveService implements Resolve<ICytology> {
  constructor(protected service: CytologyService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICytology> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cytology: HttpResponse<Cytology>) => {
          if (cytology.body) {
            return of(cytology.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Cytology());
  }
}
