import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CeappSharedModule } from '../../shared';
import {
    CollisionLevelTwoService,
    CollisionLevelTwoPopupService,
    CollisionLevelTwoComponent,
    CollisionLevelTwoDetailComponent,
    CollisionLevelTwoDialogComponent,
    CollisionLevelTwoPopupComponent,
    CollisionLevelTwoDeletePopupComponent,
    CollisionLevelTwoDeleteDialogComponent,
    collisionLevelTwoRoute,
    collisionLevelTwoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...collisionLevelTwoRoute,
    ...collisionLevelTwoPopupRoute,
];

@NgModule({
    imports: [
        CeappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CollisionLevelTwoComponent,
        CollisionLevelTwoDetailComponent,
        CollisionLevelTwoDialogComponent,
        CollisionLevelTwoDeleteDialogComponent,
        CollisionLevelTwoPopupComponent,
        CollisionLevelTwoDeletePopupComponent,
    ],
    entryComponents: [
        CollisionLevelTwoComponent,
        CollisionLevelTwoDialogComponent,
        CollisionLevelTwoPopupComponent,
        CollisionLevelTwoDeleteDialogComponent,
        CollisionLevelTwoDeletePopupComponent,
    ],
    providers: [
        CollisionLevelTwoService,
        CollisionLevelTwoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeappCollisionLevelTwoModule {}
