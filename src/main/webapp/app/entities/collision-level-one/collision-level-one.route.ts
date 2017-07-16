import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CollisionLevelOneComponent } from './collision-level-one.component';
import { CollisionLevelOneDetailComponent } from './collision-level-one-detail.component';
import { CollisionLevelOnePopupComponent } from './collision-level-one-dialog.component';
import { CollisionLevelOneDeletePopupComponent } from './collision-level-one-delete-dialog.component';

import { Principal } from '../../shared';

export const collisionLevelOneRoute: Routes = [
    {
        path: 'collision-level-one',
        component: CollisionLevelOneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelOne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'collision-level-one/:id',
        component: CollisionLevelOneDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelOne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const collisionLevelOnePopupRoute: Routes = [
    {
        path: 'collision-level-one-new',
        component: CollisionLevelOnePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelOne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collision-level-one/:id/edit',
        component: CollisionLevelOnePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelOne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collision-level-one/:id/delete',
        component: CollisionLevelOneDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelOne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
