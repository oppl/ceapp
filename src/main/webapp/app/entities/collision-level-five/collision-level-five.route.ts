import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CollisionLevelFiveComponent } from './collision-level-five.component';
import { CollisionLevelFiveDetailComponent } from './collision-level-five-detail.component';
import { CollisionLevelFivePopupComponent } from './collision-level-five-dialog.component';
import { CollisionLevelFiveDeletePopupComponent } from './collision-level-five-delete-dialog.component';

import { Principal } from '../../shared';

export const collisionLevelFiveRoute: Routes = [
    {
        path: 'collision-level-five',
        component: CollisionLevelFiveComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelFive.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'collision-level-five/:id',
        component: CollisionLevelFiveDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelFive.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const collisionLevelFivePopupRoute: Routes = [
    {
        path: 'collision-level-five-new',
        component: CollisionLevelFivePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelFive.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collision-level-five/:id/edit',
        component: CollisionLevelFivePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelFive.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collision-level-five/:id/delete',
        component: CollisionLevelFiveDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelFive.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
