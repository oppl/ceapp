import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CollisionLevelFourComponent } from './collision-level-four.component';
import { CollisionLevelFourDetailComponent } from './collision-level-four-detail.component';
import { CollisionLevelFourPopupComponent } from './collision-level-four-dialog.component';
import { CollisionLevelFourDeletePopupComponent } from './collision-level-four-delete-dialog.component';

import { Principal } from '../../shared';

export const collisionLevelFourRoute: Routes = [
    {
        path: 'collision-level-four',
        component: CollisionLevelFourComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelFour.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'collision-level-four/:id',
        component: CollisionLevelFourDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelFour.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const collisionLevelFourPopupRoute: Routes = [
    {
        path: 'collision-level-four-new',
        component: CollisionLevelFourPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelFour.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collision-level-four/:id/edit',
        component: CollisionLevelFourPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelFour.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collision-level-four/:id/delete',
        component: CollisionLevelFourDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelFour.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
