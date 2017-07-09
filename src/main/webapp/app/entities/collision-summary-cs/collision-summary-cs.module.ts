import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CeappSharedModule } from '../../shared';
import {
    CollisionSummaryCSService,
    CollisionSummaryCSPopupService,
    CollisionSummaryCSComponent,
    CollisionSummaryCSDetailComponent,
    CollisionSummaryCSDialogComponent,
    CollisionSummaryCSPopupComponent,
    CollisionSummaryCSDeletePopupComponent,
    CollisionSummaryCSDeleteDialogComponent,
    collisionSummaryCSRoute,
    collisionSummaryCSPopupRoute,
} from './';

const ENTITY_STATES = [
    ...collisionSummaryCSRoute,
    ...collisionSummaryCSPopupRoute,
];

@NgModule({
    imports: [
        CeappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CollisionSummaryCSComponent,
        CollisionSummaryCSDetailComponent,
        CollisionSummaryCSDialogComponent,
        CollisionSummaryCSDeleteDialogComponent,
        CollisionSummaryCSPopupComponent,
        CollisionSummaryCSDeletePopupComponent,
    ],
    entryComponents: [
        CollisionSummaryCSComponent,
        CollisionSummaryCSDialogComponent,
        CollisionSummaryCSPopupComponent,
        CollisionSummaryCSDeleteDialogComponent,
        CollisionSummaryCSDeletePopupComponent,
    ],
    providers: [
        CollisionSummaryCSService,
        CollisionSummaryCSPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeappCollisionSummaryCSModule {}
