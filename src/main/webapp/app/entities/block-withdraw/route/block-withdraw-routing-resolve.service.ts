import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBlockWithdraw, BlockWithdraw } from '../block-withdraw.model';
import { BlockWithdrawService } from '../service/block-withdraw.service';

@Injectable({ providedIn: 'root' })
export class BlockWithdrawRoutingResolveService implements Resolve<IBlockWithdraw> {
  constructor(protected service: BlockWithdrawService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBlockWithdraw> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((blockWithdraw: HttpResponse<BlockWithdraw>) => {
          if (blockWithdraw.body) {
            return of(blockWithdraw.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BlockWithdraw());
  }
}
