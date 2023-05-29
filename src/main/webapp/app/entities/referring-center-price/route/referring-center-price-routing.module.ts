import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {UserRouteAccessService} from 'app/core/auth/user-route-access.service';
import {ReferringCenterPriceComponent} from '../list/referring-center-price.component';
import {ReferringCenterPriceDetailComponent} from '../detail/referring-center-price-detail.component';
import {ReferringCenterPriceUpdateComponent} from '../update/referring-center-price-update.component';
import {ReferringCenterPriceRoutingResolveService} from './referring-center-price-routing-resolve.service';

const referringCenterPriceRoute: Routes = [
  {
    path: '',
    component: ReferringCenterPriceComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReferringCenterPriceDetailComponent,
    resolve: {
      referringCenterPrice: ReferringCenterPriceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReferringCenterPriceUpdateComponent,
    resolve: {
      referringCenterPrice: ReferringCenterPriceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReferringCenterPriceUpdateComponent,
    resolve: {
      referringCenterPrice: ReferringCenterPriceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(referringCenterPriceRoute)],
  exports: [RouterModule],
})
export class ReferringCenterPriceRoutingModule {
}
