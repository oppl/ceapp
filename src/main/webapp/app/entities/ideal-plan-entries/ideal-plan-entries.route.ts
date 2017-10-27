import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { IdealPlanEntriesComponent } from './ideal-plan-entries.component';
import { IdealPlanEntriesDetailComponent } from './ideal-plan-entries-detail.component';
import { IdealPlanEntriesPopupComponent } from './ideal-plan-entries-dialog.component';
import { IdealPlanEntriesDeletePopupComponent } from './ideal-plan-entries-delete-dialog.component';

import { Principal } from '../../shared';

export const idealPlanEntriesRoute: Routes = [
    {
        path: 'ideal-plan-entries',
        component: IdealPlanEntriesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.idealPlanEntries.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ideal-plan-entries/:id',
        component: IdealPlanEntriesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.idealPlanEntries.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const idealPlanEntriesPopupRoute: Routes = [
    {
        path: 'ideal-plan-entries-new',
        component: IdealPlanEntriesPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ceappApp.idealPlanEntries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ideal-plan-entries/:id/edit',
        component: IdealPlanEntriesPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ceappApp.idealPlanEntries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ideal-plan-entries/:id/delete',
        component: IdealPlanEntriesDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ceappApp.idealPlanEntries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
