import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PapSmearSaleComponent } from '../list/pap-smear-sale.component';
import { PapSmearSaleDetailComponent } from '../detail/pap-smear-sale-detail.component';
import { PapSmearSaleUpdateComponent } from '../update/pap-smear-sale-update.component';
import { PapSmearSaleRoutingResolveService } from './pap-smear-sale-routing-resolve.service';

const papSmearSaleRoute: Routes = [
  {
    path: '',
    component: PapSmearSaleComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PapSmearSaleDetailComponent,
    resolve: {
      papSmearSale: PapSmearSaleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PapSmearSaleUpdateComponent,
    resolve: {
      papSmearSale: PapSmearSaleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PapSmearSaleUpdateComponent,
    resolve: {
      papSmearSale: PapSmearSaleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(papSmearSaleRoute)],
  exports: [RouterModule],
})
export class PapSmearSaleRoutingModule {}
