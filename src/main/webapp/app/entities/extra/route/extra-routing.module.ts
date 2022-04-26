import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ExtraComponent } from '../list/extra.component';
import { ExtraDetailComponent } from '../detail/extra-detail.component';
import { ExtraUpdateComponent } from '../update/extra-update.component';
import { ExtraRoutingResolveService } from './extra-routing-resolve.service';

const extraRoute: Routes = [
  {
    path: '',
    component: ExtraComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExtraDetailComponent,
    resolve: {
      extra: ExtraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExtraUpdateComponent,
    resolve: {
      extra: ExtraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExtraUpdateComponent,
    resolve: {
      extra: ExtraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(extraRoute)],
  exports: [RouterModule],
})
export class ExtraRoutingModule {}
