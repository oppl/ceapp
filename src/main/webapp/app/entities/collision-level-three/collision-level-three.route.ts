import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CollisionLevelThreeComponent } from './collision-level-three.component';
import { CollisionLevelThreeDetailComponent } from './collision-level-three-detail.component';
import { CollisionLevelThreePopupComponent } from './collision-level-three-dialog.component';
import { CollisionLevelThreeDeletePopupComponent } from './collision-level-three-delete-dialog.component';

import { Principal } from '../../shared';

export const collisionLevelThreeRoute: Routes = [
    {
        path: 'collision-level-three',
        component: CollisionLevelThreeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelThree.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'collision-level-three/:id',
        component: CollisionLevelThreeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelThree.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const collisionLevelThreePopupRoute: Routes = [
    {
        path: 'collision-level-three-new',
        component: CollisionLevelThreePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelThree.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collision-level-three/:id/edit',
        component: CollisionLevelThreePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelThree.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collision-level-three/:id/delete',
        component: CollisionLevelThreeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelThree.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
