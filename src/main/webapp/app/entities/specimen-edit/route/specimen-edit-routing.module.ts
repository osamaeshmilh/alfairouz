import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {UserRouteAccessService} from 'app/core/auth/user-route-access.service';
import {SpecimenEditComponent} from '../list/specimen-edit.component';
import {SpecimenEditDetailComponent} from '../detail/specimen-edit-detail.component';
import {SpecimenEditUpdateComponent} from '../update/specimen-edit-update.component';
import {SpecimenEditRoutingResolveService} from './specimen-edit-routing-resolve.service';

const specimenEditRoute: Routes = [
  {
    path: '',
    component: SpecimenEditComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SpecimenEditDetailComponent,
    resolve: {
      specimenEdit: SpecimenEditRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SpecimenEditUpdateComponent,
    resolve: {
      specimenEdit: SpecimenEditRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SpecimenEditUpdateComponent,
    resolve: {
      specimenEdit: SpecimenEditRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(specimenEditRoute)],
  exports: [RouterModule],
})
export class SpecimenEditRoutingModule {
}
