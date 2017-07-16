import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CeappSharedModule } from '../../shared';
import {
    CollisionLevelFourService,
    CollisionLevelFourPopupService,
    CollisionLevelFourComponent,
    CollisionLevelFourDetailComponent,
    CollisionLevelFourDialogComponent,
    CollisionLevelFourPopupComponent,
    CollisionLevelFourDeletePopupComponent,
    CollisionLevelFourDeleteDialogComponent,
    collisionLevelFourRoute,
    collisionLevelFourPopupRoute,
} from './';

const ENTITY_STATES = [
    ...collisionLevelFourRoute,
    ...collisionLevelFourPopupRoute,
];

@NgModule({
    imports: [
        CeappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CollisionLevelFourComponent,
        CollisionLevelFourDetailComponent,
        CollisionLevelFourDialogComponent,
        CollisionLevelFourDeleteDialogComponent,
        CollisionLevelFourPopupComponent,
        CollisionLevelFourDeletePopupComponent,
    ],
    entryComponents: [
        CollisionLevelFourComponent,
        CollisionLevelFourDialogComponent,
        CollisionLevelFourPopupComponent,
        CollisionLevelFourDeleteDialogComponent,
        CollisionLevelFourDeletePopupComponent,
    ],
    providers: [
        CollisionLevelFourService,
        CollisionLevelFourPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeappCollisionLevelFourModule {}
