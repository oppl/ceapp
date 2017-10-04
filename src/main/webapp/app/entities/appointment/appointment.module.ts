import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import 'jquery';
import 'moment';
import 'fullcalendar';
import { DataTableModule, ScheduleModule, SharedModule} from 'primeng/primeng';

import { CeappSharedModule } from '../../shared';
import {
    AppointmentService,
    AppointmentPopupService,
    AppointmentComponent,
    AppointmentDetailComponent,
    AppointmentDialogComponent,
    AppointmentPopupComponent,
    AppointmentDeletePopupComponent,
    AppointmentDeleteDialogComponent,
    appointmentRoute,
    appointmentPopupRoute,
} from './';

import {idealPlanPopupRoute, idealPlanRoute} from '../ideal-plan/ideal-plan.route';

const ENTITY_STATES = [
    ...appointmentRoute,
    ...appointmentPopupRoute,
    ...idealPlanRoute,
    ...idealPlanPopupRoute,
];

@NgModule({
    imports: [
        CeappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTableModule,
        SharedModule,
        ScheduleModule
    ],
    declarations: [
        AppointmentComponent,
        AppointmentDetailComponent,
        AppointmentDialogComponent,
        AppointmentDeleteDialogComponent,
        AppointmentPopupComponent,
        AppointmentDeletePopupComponent,
    ],
    entryComponents: [
        AppointmentComponent,
        AppointmentDialogComponent,
        AppointmentPopupComponent,
        AppointmentDeleteDialogComponent,
        AppointmentDeletePopupComponent,
    ],
    providers: [
        AppointmentService,
        AppointmentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeappAppointmentModule {}
