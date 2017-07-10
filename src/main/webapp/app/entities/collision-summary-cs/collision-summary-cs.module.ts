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
import {CeappCollisionSummaryLvaModule} from '../collision-summary-lva/collision-summary-lva.module';

const ENTITY_STATES = [
    ...collisionSummaryCSRoute,
    ...collisionSummaryCSPopupRoute,
];

@NgModule({
    imports: [
        CeappSharedModule,
        CeappCollisionSummaryLvaModule,
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
