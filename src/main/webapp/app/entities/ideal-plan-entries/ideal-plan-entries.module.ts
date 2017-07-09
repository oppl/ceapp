import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CeappSharedModule } from '../../shared';
import {
    IdealPlanEntriesService,
    IdealPlanEntriesPopupService,
    IdealPlanEntriesComponent,
    IdealPlanEntriesDetailComponent,
    IdealPlanEntriesDialogComponent,
    IdealPlanEntriesPopupComponent,
    IdealPlanEntriesDeletePopupComponent,
    IdealPlanEntriesDeleteDialogComponent,
    idealPlanEntriesRoute,
    idealPlanEntriesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...idealPlanEntriesRoute,
    ...idealPlanEntriesPopupRoute,
];

@NgModule({
    imports: [
        CeappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        IdealPlanEntriesComponent,
        IdealPlanEntriesDetailComponent,
        IdealPlanEntriesDialogComponent,
        IdealPlanEntriesDeleteDialogComponent,
        IdealPlanEntriesPopupComponent,
        IdealPlanEntriesDeletePopupComponent,
    ],
    entryComponents: [
        IdealPlanEntriesComponent,
        IdealPlanEntriesDialogComponent,
        IdealPlanEntriesPopupComponent,
        IdealPlanEntriesDeleteDialogComponent,
        IdealPlanEntriesDeletePopupComponent,
    ],
    providers: [
        IdealPlanEntriesService,
        IdealPlanEntriesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeappIdealPlanEntriesModule {}
