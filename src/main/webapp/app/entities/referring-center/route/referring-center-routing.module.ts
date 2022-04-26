import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReferringCenterComponent } from '../list/referring-center.component';
import { ReferringCenterDetailComponent } from '../detail/referring-center-detail.component';
import { ReferringCenterUpdateComponent } from '../update/referring-center-update.component';
import { ReferringCenterRoutingResolveService } from './referring-center-routing-resolve.service';

const referringCenterRoute: Routes = [
  {
    path: '',
    component: ReferringCenterComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReferringCenterDetailComponent,
    resolve: {
      referringCenter: ReferringCenterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReferringCenterUpdateComponent,
    resolve: {
      referringCenter: ReferringCenterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReferringCenterUpdateComponent,
    resolve: {
      referringCenter: ReferringCenterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(referringCenterRoute)],
  exports: [RouterModule],
})
export class ReferringCenterRoutingModule {}
