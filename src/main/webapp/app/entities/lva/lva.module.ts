import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CeappSharedModule } from '../../shared';
import {
    LvaService,
    LvaPopupService,
    LvaComponent,
    LvaDetailComponent,
    LvaDialogComponent,
    LvaPopupComponent,
    LvaDeletePopupComponent,
    LvaDeleteDialogComponent,
    lvaRoute,
    lvaPopupRoute,
} from './';
import {CeappAppointmentModule} from '../appointment/appointment.module';

const ENTITY_STATES = [
    ...lvaRoute,
    ...lvaPopupRoute,
];

@NgModule({
    imports: [
        CeappSharedModule,
        CeappAppointmentModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LvaComponent,
        LvaDetailComponent,
        LvaDialogComponent,
        LvaDeleteDialogComponent,
        LvaPopupComponent,
        LvaDeletePopupComponent,
    ],
    entryComponents: [
        LvaComponent,
        LvaDialogComponent,
        LvaPopupComponent,
        LvaDeleteDialogComponent,
        LvaDeletePopupComponent,
    ],
    providers: [
        LvaService,
        LvaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeappLvaModule {}
