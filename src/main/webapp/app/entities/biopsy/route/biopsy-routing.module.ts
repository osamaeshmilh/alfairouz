import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BiopsyComponent } from '../list/biopsy.component';
import { BiopsyDetailComponent } from '../detail/biopsy-detail.component';
import { BiopsyUpdateComponent } from '../update/biopsy-update.component';
import { BiopsyRoutingResolveService } from './biopsy-routing-resolve.service';

const biopsyRoute: Routes = [
  {
    path: '',
    component: BiopsyComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BiopsyDetailComponent,
    resolve: {
      biopsy: BiopsyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BiopsyUpdateComponent,
    resolve: {
      biopsy: BiopsyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BiopsyUpdateComponent,
    resolve: {
      biopsy: BiopsyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(biopsyRoute)],
  exports: [RouterModule],
})
export class BiopsyRoutingModule {}
