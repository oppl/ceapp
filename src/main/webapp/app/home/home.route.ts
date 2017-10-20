import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { HomeComponent } from './';
import {AppointmentComponent} from '../entities/appointment/appointment.component';

export const HOME_ROUTE: Route = {
    path: '',
    component: AppointmentComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    }
};
