import {Routes} from '@angular/router';
import {PrivacyPolicyComponent} from './privacy-policy.component';
import {UserRouteAccessService} from 'app/core/auth/user-route-access.service';

export const privacyPolicyRoute: Routes = [
  {
    path: '',
    component: PrivacyPolicyComponent,
    data: {
      pageTitle: 'Privacy Policy',
    },
  },
];
