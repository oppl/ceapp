import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CeappSharedModule } from '../../shared';
import {
    IdealPlanService,
    IdealPlanPopupService,
    IdealPlanComponent,
    IdealPlanDetailComponent,
    IdealPlanDialogComponent,
    IdealPlanPopupComponent,
    IdealPlanDeletePopupComponent,
    IdealPlanDeleteDialogComponent,
    idealPlanRoute,
    idealPlanPopupRoute,
} from './';

const ENTITY_STATES = [
    ...idealPlanRoute,
    ...idealPlanPopupRoute,
];

@NgModule({
    imports: [
        CeappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        IdealPlanComponent,
        IdealPlanDetailComponent,
        IdealPlanDialogComponent,
        IdealPlanDeleteDialogComponent,
        IdealPlanPopupComponent,
        IdealPlanDeletePopupComponent,
    ],
    entryComponents: [
        IdealPlanComponent,
        IdealPlanDialogComponent,
        IdealPlanPopupComponent,
        IdealPlanDeleteDialogComponent,
        IdealPlanDeletePopupComponent,
    ],
    providers: [
        IdealPlanService,
        IdealPlanPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeappIdealPlanModule {}
