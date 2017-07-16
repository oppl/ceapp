import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CeappSharedModule } from '../../shared';
import {
    CollisionLevelThreeService,
    CollisionLevelThreePopupService,
    CollisionLevelThreeComponent,
    CollisionLevelThreeDetailComponent,
    CollisionLevelThreeDialogComponent,
    CollisionLevelThreePopupComponent,
    CollisionLevelThreeDeletePopupComponent,
    CollisionLevelThreeDeleteDialogComponent,
    collisionLevelThreeRoute,
    collisionLevelThreePopupRoute,
} from './';

const ENTITY_STATES = [
    ...collisionLevelThreeRoute,
    ...collisionLevelThreePopupRoute,
];

@NgModule({
    imports: [
        CeappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CollisionLevelThreeComponent,
        CollisionLevelThreeDetailComponent,
        CollisionLevelThreeDialogComponent,
        CollisionLevelThreeDeleteDialogComponent,
        CollisionLevelThreePopupComponent,
        CollisionLevelThreeDeletePopupComponent,
    ],
    entryComponents: [
        CollisionLevelThreeComponent,
        CollisionLevelThreeDialogComponent,
        CollisionLevelThreePopupComponent,
        CollisionLevelThreeDeleteDialogComponent,
        CollisionLevelThreeDeletePopupComponent,
    ],
    providers: [
        CollisionLevelThreeService,
        CollisionLevelThreePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeappCollisionLevelThreeModule {}
