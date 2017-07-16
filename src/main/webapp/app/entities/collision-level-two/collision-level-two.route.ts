import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CollisionLevelTwoComponent } from './collision-level-two.component';
import { CollisionLevelTwoDetailComponent } from './collision-level-two-detail.component';
import { CollisionLevelTwoPopupComponent } from './collision-level-two-dialog.component';
import { CollisionLevelTwoDeletePopupComponent } from './collision-level-two-delete-dialog.component';

import { Principal } from '../../shared';

export const collisionLevelTwoRoute: Routes = [
    {
        path: 'collision-level-two',
        component: CollisionLevelTwoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelTwo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'collision-level-two/:id',
        component: CollisionLevelTwoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelTwo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const collisionLevelTwoPopupRoute: Routes = [
    {
        path: 'collision-level-two-new',
        component: CollisionLevelTwoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelTwo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collision-level-two/:id/edit',
        component: CollisionLevelTwoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelTwo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collision-level-two/:id/delete',
        component: CollisionLevelTwoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionLevelTwo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
