import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CeappSharedModule } from '../../shared';
import {
    CollisionLevelOneService,
    CollisionLevelOnePopupService,
    CollisionLevelOneComponent,
    CollisionLevelOneDetailComponent,
    CollisionLevelOneDialogComponent,
    CollisionLevelOnePopupComponent,
    CollisionLevelOneDeletePopupComponent,
    CollisionLevelOneDeleteDialogComponent,
    collisionLevelOneRoute,
    collisionLevelOnePopupRoute,
} from './';

const ENTITY_STATES = [
    ...collisionLevelOneRoute,
    ...collisionLevelOnePopupRoute,
];

@NgModule({
    imports: [
        CeappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CollisionLevelOneComponent,
        CollisionLevelOneDetailComponent,
        CollisionLevelOneDialogComponent,
        CollisionLevelOneDeleteDialogComponent,
        CollisionLevelOnePopupComponent,
        CollisionLevelOneDeletePopupComponent,
    ],
    entryComponents: [
        CollisionLevelOneComponent,
        CollisionLevelOneDialogComponent,
        CollisionLevelOnePopupComponent,
        CollisionLevelOneDeleteDialogComponent,
        CollisionLevelOneDeletePopupComponent,
    ],
    providers: [
        CollisionLevelOneService,
        CollisionLevelOnePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CeappCollisionLevelOneModule {}
