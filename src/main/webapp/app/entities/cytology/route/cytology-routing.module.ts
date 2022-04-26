import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CytologyComponent } from '../list/cytology.component';
import { CytologyDetailComponent } from '../detail/cytology-detail.component';
import { CytologyUpdateComponent } from '../update/cytology-update.component';
import { CytologyRoutingResolveService } from './cytology-routing-resolve.service';

const cytologyRoute: Routes = [
  {
    path: '',
    component: CytologyComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CytologyDetailComponent,
    resolve: {
      cytology: CytologyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CytologyUpdateComponent,
    resolve: {
      cytology: CytologyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CytologyUpdateComponent,
    resolve: {
      cytology: CytologyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cytologyRoute)],
  exports: [RouterModule],
})
export class CytologyRoutingModule {}
