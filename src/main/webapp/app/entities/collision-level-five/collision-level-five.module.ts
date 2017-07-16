import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CeappSharedModule } from '../../shared';
import {
    CollisionLevelFiveService,
    CollisionLevelFivePopupService,
    CollisionLevelFiveComponent,
    CollisionLevelFiveDetailComponent,
    CollisionLevelFiveDialogComponent,
    CollisionLevelFivePopupComponent,
    CollisionLevelFiveDeletePopupComponent,
    CollisionLevelFiveDeleteDialogComponent,
    collisionLevelFiveRoute,
    collisionLevelFivePopupRoute,
} from './';

const ENTITY_STATES = [
    ...collisionLevelFiveRoute,
    ...collisionLevelFivePopupRoute,
];

@NgModule({
    imports: [
        CeappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CollisionLevelFiveComponent,
        CollisionLevelFiveDetailComponent,
        CollisionLevelFiveDialogComponent,
        CollisionLevelFiveDeleteDialogComponent,
        CollisionLevelFivePopupComponent,
        CollisionLevelFiveDeletePopupComponent,
    ],
    entryComponents: [
        CollisionLevelFiveComponent,
        CollisionLevelFiveDialogComponent,
        CollisionLevelFivePopupComponent,
        CollisionLevelFiveDeleteDialogComponent,
        CollisionLevelFiveDeletePopupComponent,
    ],
    providers: [
        CollisionLevelFiveService,
        CollisionLevelFivePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeappCollisionLevelFiveModule {}
