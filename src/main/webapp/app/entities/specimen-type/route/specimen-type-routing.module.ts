import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SpecimenTypeComponent } from '../list/specimen-type.component';
import { SpecimenTypeDetailComponent } from '../detail/specimen-type-detail.component';
import { SpecimenTypeUpdateComponent } from '../update/specimen-type-update.component';
import { SpecimenTypeRoutingResolveService } from './specimen-type-routing-resolve.service';

const specimenTypeRoute: Routes = [
  {
    path: '',
    component: SpecimenTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SpecimenTypeDetailComponent,
    resolve: {
      specimenType: SpecimenTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SpecimenTypeUpdateComponent,
    resolve: {
      specimenType: SpecimenTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SpecimenTypeUpdateComponent,
    resolve: {
      specimenType: SpecimenTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(specimenTypeRoute)],
  exports: [RouterModule],
})
export class SpecimenTypeRoutingModule {}
