import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BlockWithdrawComponent } from '../list/block-withdraw.component';
import { BlockWithdrawDetailComponent } from '../detail/block-withdraw-detail.component';
import { BlockWithdrawUpdateComponent } from '../update/block-withdraw-update.component';
import { BlockWithdrawRoutingResolveService } from './block-withdraw-routing-resolve.service';

const blockWithdrawRoute: Routes = [
  {
    path: '',
    component: BlockWithdrawComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BlockWithdrawDetailComponent,
    resolve: {
      blockWithdraw: BlockWithdrawRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BlockWithdrawUpdateComponent,
    resolve: {
      blockWithdraw: BlockWithdrawRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BlockWithdrawUpdateComponent,
    resolve: {
      blockWithdraw: BlockWithdrawRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(blockWithdrawRoute)],
  exports: [RouterModule],
})
export class BlockWithdrawRoutingModule {}
