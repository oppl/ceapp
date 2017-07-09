import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CollisionSummaryLvaComponent } from './collision-summary-lva.component';
import { CollisionSummaryLvaDetailComponent } from './collision-summary-lva-detail.component';
import { CollisionSummaryLvaPopupComponent } from './collision-summary-lva-dialog.component';
import { CollisionSummaryLvaDeletePopupComponent } from './collision-summary-lva-delete-dialog.component';

import { Principal } from '../../shared';

export const collisionSummaryLvaRoute: Routes = [
    {
        path: 'collision-summary-lva',
        component: CollisionSummaryLvaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionSummaryLva.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'collision-summary-lva/:id',
        component: CollisionSummaryLvaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionSummaryLva.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const collisionSummaryLvaPopupRoute: Routes = [
    {
        path: 'collision-summary-lva-new',
        component: CollisionSummaryLvaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionSummaryLva.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collision-summary-lva/:id/edit',
        component: CollisionSummaryLvaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionSummaryLva.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collision-summary-lva/:id/delete',
        component: CollisionSummaryLvaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionSummaryLva.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
