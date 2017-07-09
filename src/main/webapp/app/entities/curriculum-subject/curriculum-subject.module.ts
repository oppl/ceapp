import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CeappSharedModule } from '../../shared';
import {
    CurriculumSubjectService,
    CurriculumSubjectPopupService,
    CurriculumSubjectComponent,
    CurriculumSubjectDetailComponent,
    CurriculumSubjectDialogComponent,
    CurriculumSubjectPopupComponent,
    CurriculumSubjectDeletePopupComponent,
    CurriculumSubjectDeleteDialogComponent,
    curriculumSubjectRoute,
    curriculumSubjectPopupRoute,
} from './';

const ENTITY_STATES = [
    ...curriculumSubjectRoute,
    ...curriculumSubjectPopupRoute,
];

@NgModule({
    imports: [
        CeappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CurriculumSubjectComponent,
        CurriculumSubjectDetailComponent,
        CurriculumSubjectDialogComponent,
        CurriculumSubjectDeleteDialogComponent,
        CurriculumSubjectPopupComponent,
        CurriculumSubjectDeletePopupComponent,
    ],
    entryComponents: [
        CurriculumSubjectComponent,
        CurriculumSubjectDialogComponent,
        CurriculumSubjectPopupComponent,
        CurriculumSubjectDeleteDialogComponent,
        CurriculumSubjectDeletePopupComponent,
    ],
    providers: [
        CurriculumSubjectService,
        CurriculumSubjectPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeappCurriculumSubjectModule {}
