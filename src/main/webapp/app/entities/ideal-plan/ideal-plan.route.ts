import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { IdealPlanComponent } from './ideal-plan.component';
import { IdealPlanDetailComponent } from './ideal-plan-detail.component';
import { IdealPlanPopupComponent } from './ideal-plan-dialog.component';
import { IdealPlanDeletePopupComponent } from './ideal-plan-delete-dialog.component';

import { Principal } from '../../shared';

export const idealPlanRoute: Routes = [
    {
        path: 'ideal-plan',
        component: IdealPlanComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.idealPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ideal-plan/:id',
        component: IdealPlanDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.idealPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const idealPlanPopupRoute: Routes = [
    {
        path: 'ideal-plan-new',
        component: IdealPlanPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.idealPlan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ideal-plan/:id/edit',
        component: IdealPlanPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.idealPlan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ideal-plan/:id/delete',
        component: IdealPlanDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.idealPlan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
