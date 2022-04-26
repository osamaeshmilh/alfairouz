import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrgan, Organ } from '../organ.model';
import { OrganService } from '../service/organ.service';

@Injectable({ providedIn: 'root' })
export class OrganRoutingResolveService implements Resolve<IOrgan> {
  constructor(protected service: OrganService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrgan> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((organ: HttpResponse<Organ>) => {
          if (organ.body) {
            return of(organ.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Organ());
  }
}
