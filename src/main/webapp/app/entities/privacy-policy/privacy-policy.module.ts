import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {PrivacyPolicyComponent} from './privacy-policy.component';
import {SharedModule} from 'app/shared/shared.module';
import {privacyPolicyRoute} from "./privacy-policy.route";

const ENTITY_STATES = [...privacyPolicyRoute];

@NgModule({
  imports: [SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PrivacyPolicyComponent],
  entryComponents: [PrivacyPolicyComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class PrivacyPolicyModule {
}
