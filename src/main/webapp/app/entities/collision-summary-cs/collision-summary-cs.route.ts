import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CollisionSummaryCSComponent } from './collision-summary-cs.component';
import { CollisionSummaryCSDetailComponent } from './collision-summary-cs-detail.component';
import { CollisionSummaryCSPopupComponent } from './collision-summary-cs-dialog.component';
import { CollisionSummaryCSDeletePopupComponent } from './collision-summary-cs-delete-dialog.component';

import { Principal } from '../../shared';

export const collisionSummaryCSRoute: Routes = [
    {
        path: 'collision-summary-cs',
        component: CollisionSummaryCSComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionSummaryCS.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'collision-summary-cs/:id',
        component: CollisionSummaryCSDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionSummaryCS.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const collisionSummaryCSPopupRoute: Routes = [
    {
        path: 'collision-summary-cs-new',
        component: CollisionSummaryCSPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionSummaryCS.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collision-summary-cs/:id/edit',
        component: CollisionSummaryCSPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionSummaryCS.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collision-summary-cs/:id/delete',
        component: CollisionSummaryCSDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.collisionSummaryCS.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
