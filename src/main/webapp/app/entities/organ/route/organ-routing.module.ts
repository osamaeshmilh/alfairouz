import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrganComponent } from '../list/organ.component';
import { OrganDetailComponent } from '../detail/organ-detail.component';
import { OrganUpdateComponent } from '../update/organ-update.component';
import { OrganRoutingResolveService } from './organ-routing-resolve.service';

const organRoute: Routes = [
  {
    path: '',
    component: OrganComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrganDetailComponent,
    resolve: {
      organ: OrganRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrganUpdateComponent,
    resolve: {
      organ: OrganRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrganUpdateComponent,
    resolve: {
      organ: OrganRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(organRoute)],
  exports: [RouterModule],
})
export class OrganRoutingModule {}
