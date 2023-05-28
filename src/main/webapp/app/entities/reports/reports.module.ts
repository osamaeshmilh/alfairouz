import {NgModule} from '@angular/core';

import {ReportsComponent} from './reports.component';
import {SharedModule} from 'app/shared/shared.module';
import {ReportsRoutingModule} from './reports.route';

@NgModule({
  imports: [SharedModule, ReportsRoutingModule],
  declarations: [ReportsComponent],
  entryComponents: [ReportsComponent],
})
export class ReportsModule {
}
