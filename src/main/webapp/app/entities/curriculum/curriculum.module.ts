import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CeappSharedModule } from '../../shared';
import {DataTableModule, ScheduleModule, SharedModule} from 'primeng/primeng';
import {
    CurriculumService,
    CurriculumPopupService,
    CurriculumComponent,
    CurriculumDetailComponent,
    CurriculumDialogComponent,
    CurriculumPopupComponent,
    CurriculumDeletePopupComponent,
    CurriculumDeleteDialogComponent,
    curriculumRoute,
    curriculumPopupRoute,
} from './';

const ENTITY_STATES = [
    ...curriculumRoute,
    ...curriculumPopupRoute,
];

@NgModule({
    imports: [
        CeappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTableModule,
        SharedModule,
    ],
    declarations: [
        CurriculumComponent,
        CurriculumDetailComponent,
        CurriculumDialogComponent,
        CurriculumDeleteDialogComponent,
        CurriculumPopupComponent,
        CurriculumDeletePopupComponent,
    ],
    entryComponents: [
        CurriculumComponent,
        CurriculumDialogComponent,
        CurriculumPopupComponent,
        CurriculumDeleteDialogComponent,
        CurriculumDeletePopupComponent,
    ],
    providers: [
        CurriculumService,
        CurriculumPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeappCurriculumModule {}
