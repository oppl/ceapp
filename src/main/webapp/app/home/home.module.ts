import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CeappSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import {CeappAppointmentModule} from '../entities/appointment/appointment.module';

@NgModule({
    imports: [
        CeappSharedModule,
        CeappAppointmentModule,
        RouterModule.forRoot([ HOME_ROUTE ], { useHash: true })
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeappHomeModule {}
