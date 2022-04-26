import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RepresentativeDeliveryComponent } from '../list/representative-delivery.component';
import { RepresentativeDeliveryDetailComponent } from '../detail/representative-delivery-detail.component';
import { RepresentativeDeliveryUpdateComponent } from '../update/representative-delivery-update.component';
import { RepresentativeDeliveryRoutingResolveService } from './representative-delivery-routing-resolve.service';

const representativeDeliveryRoute: Routes = [
  {
    path: '',
    component: RepresentativeDeliveryComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RepresentativeDeliveryDetailComponent,
    resolve: {
      representativeDelivery: RepresentativeDeliveryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RepresentativeDeliveryUpdateComponent,
    resolve: {
      representativeDelivery: RepresentativeDeliveryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RepresentativeDeliveryUpdateComponent,
    resolve: {
      representativeDelivery: RepresentativeDeliveryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(representativeDeliveryRoute)],
  exports: [RouterModule],
})
export class RepresentativeDeliveryRoutingModule {}
