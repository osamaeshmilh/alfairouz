import {RouterModule, Routes} from '@angular/router';
import {ReportsComponent} from './reports.component';
import {UserRouteAccessService} from 'app/core/auth/user-route-access.service';
import {NgModule} from '@angular/core';

const reportsRoute: Routes = [
  {
    path: '',
    component: ReportsComponent,
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(reportsRoute)],
  exports: [RouterModule],
})
export class ReportsRoutingModule {
}
