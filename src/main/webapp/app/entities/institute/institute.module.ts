import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CeappSharedModule } from '../../shared';
import {
    InstituteService,
    InstitutePopupService,
    InstituteComponent,
    InstituteDetailComponent,
    InstituteDialogComponent,
    InstitutePopupComponent,
    InstituteDeletePopupComponent,
    InstituteDeleteDialogComponent,
    instituteRoute,
    institutePopupRoute,
} from './';

const ENTITY_STATES = [
    ...instituteRoute,
    ...institutePopupRoute,
];

@NgModule({
    imports: [
        CeappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        InstituteComponent,
        InstituteDetailComponent,
        InstituteDialogComponent,
        InstituteDeleteDialogComponent,
        InstitutePopupComponent,
        InstituteDeletePopupComponent,
    ],
    entryComponents: [
        InstituteComponent,
        InstituteDialogComponent,
        InstitutePopupComponent,
        InstituteDeleteDialogComponent,
        InstituteDeletePopupComponent,
    ],
    providers: [
        InstituteService,
        InstitutePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeappInstituteModule {}
