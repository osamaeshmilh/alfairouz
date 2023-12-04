import {Injectable} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {Resolve, ActivatedRouteSnapshot, Router} from '@angular/router';
import {Observable, of, EMPTY} from 'rxjs';
import {mergeMap} from 'rxjs/operators';

import {ISpecimenEdit, SpecimenEdit} from '../specimen-edit.model';
import {SpecimenEditService} from '../service/specimen-edit.service';

@Injectable({providedIn: 'root'})
export class SpecimenEditRoutingResolveService implements Resolve<ISpecimenEdit> {
    constructor(protected service: SpecimenEditService, protected router: Router) {
    }

    resolve(route: ActivatedRouteSnapshot): Observable<ISpecimenEdit> | Observable<never> {
        const id = route.params['id'];
        if (id) {
            return this.service.find(id).pipe(
                mergeMap((specimenEdit: HttpResponse<SpecimenEdit>) => {
                    if (specimenEdit.body) {
                        return of(specimenEdit.body);
                    } else {
                        this.router.navigate(['404']);
                        return EMPTY;
                    }
                })
            );
        }
        return of(new SpecimenEdit());
    }
}
