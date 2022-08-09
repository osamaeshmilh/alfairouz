import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SpecimenComponent } from '../list/specimen.component';
import { SpecimenDetailComponent } from '../detail/specimen-detail.component';
import { SpecimenUpdateComponent } from '../update/specimen-update.component';
import {SpecimenRoutingResolveService} from './specimen-routing-resolve.service';
import {SpecimenQueryComponent} from "../query/specimen-query.component";

const specimenRoute: Routes = [
  {
    path: '',
    component: SpecimenComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SpecimenDetailComponent,
    resolve: {
      specimen: SpecimenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SpecimenUpdateComponent,
    resolve: {
      specimen: SpecimenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SpecimenUpdateComponent,
    resolve: {
      specimen: SpecimenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'query',
    component: SpecimenQueryComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(specimenRoute)],
  exports: [RouterModule],
})
export class SpecimenRoutingModule {}
