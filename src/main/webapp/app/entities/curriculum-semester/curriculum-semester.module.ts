import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CeappSharedModule } from '../../shared';
import {
    CurriculumSemesterService,
    CurriculumSemesterPopupService,
    CurriculumSemesterComponent,
    CurriculumSemesterDetailComponent,
    CurriculumSemesterDialogComponent,
    CurriculumSemesterPopupComponent,
    CurriculumSemesterDeletePopupComponent,
    CurriculumSemesterDeleteDialogComponent,
    curriculumSemesterRoute,
    curriculumSemesterPopupRoute,
} from './';
import {CurriculumSemesterUpdateComponent} from "./curriculum-semester-update.component";

const ENTITY_STATES = [
    ...curriculumSemesterRoute,
    ...curriculumSemesterPopupRoute,
];

@NgModule({
    imports: [
        CeappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CurriculumSemesterComponent,
        CurriculumSemesterDetailComponent,
        CurriculumSemesterDialogComponent,
        CurriculumSemesterDeleteDialogComponent,
        CurriculumSemesterPopupComponent,
        CurriculumSemesterDeletePopupComponent,
        CurriculumSemesterUpdateComponent,
    ],
    entryComponents: [
        CurriculumSemesterComponent,
        CurriculumSemesterDialogComponent,
        CurriculumSemesterPopupComponent,
        CurriculumSemesterDeleteDialogComponent,
        CurriculumSemesterDeletePopupComponent,
    ],
    providers: [
        CurriculumSemesterService,
        CurriculumSemesterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeappCurriculumSemesterModule {}
