import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CeappSharedModule } from '../../shared';
import {
    CollisionSummaryLvaService,
    CollisionSummaryLvaPopupService,
    CollisionSummaryLvaComponent,
    CollisionSummaryLvaDetailComponent,
    CollisionSummaryLvaDialogComponent,
    CollisionSummaryLvaPopupComponent,
    CollisionSummaryLvaDeletePopupComponent,
    CollisionSummaryLvaDeleteDialogComponent,
    collisionSummaryLvaRoute,
    collisionSummaryLvaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...collisionSummaryLvaRoute,
    ...collisionSummaryLvaPopupRoute,
];

@NgModule({
    imports: [
        CeappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    exports: [
        CollisionSummaryLvaComponent
    ],
    declarations: [
        CollisionSummaryLvaComponent,
        CollisionSummaryLvaDetailComponent,
        CollisionSummaryLvaDialogComponent,
        CollisionSummaryLvaDeleteDialogComponent,
        CollisionSummaryLvaPopupComponent,
        CollisionSummaryLvaDeletePopupComponent,
    ],
    entryComponents: [
        CollisionSummaryLvaComponent,
        CollisionSummaryLvaDialogComponent,
        CollisionSummaryLvaPopupComponent,
        CollisionSummaryLvaDeleteDialogComponent,
        CollisionSummaryLvaDeletePopupComponent,
    ],
    providers: [
        CollisionSummaryLvaService,
        CollisionSummaryLvaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeappCollisionSummaryLvaModule {}
