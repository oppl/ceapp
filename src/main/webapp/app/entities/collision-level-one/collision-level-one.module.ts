import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CeappSharedModule } from '../../shared';

import {DataTableModule, ScheduleModule, SharedModule} from 'primeng/primeng';

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
import {CeappCollisionLevelTwoModule} from '../collision-level-two/collision-level-two.module';
import {CeappCollisionLevelThreeModule} from '../collision-level-three/collision-level-three.module';
import {CeappCollisionLevelFourModule} from '../collision-level-four/collision-level-four.module';
import {CeappCollisionLevelFiveModule} from '../collision-level-five/collision-level-five.module';

const ENTITY_STATES = [
    ...collisionLevelOneRoute,
    ...collisionLevelOnePopupRoute,
];

@NgModule({
    imports: [
        CeappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTableModule,
        ScheduleModule,
        SharedModule,
        CeappCollisionLevelTwoModule,
        CeappCollisionLevelThreeModule,
        CeappCollisionLevelFourModule,
        CeappCollisionLevelFiveModule
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
