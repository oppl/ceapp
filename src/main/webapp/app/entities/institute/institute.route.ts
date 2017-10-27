import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { InstituteComponent } from './institute.component';
import { InstituteDetailComponent } from './institute-detail.component';
import { InstitutePopupComponent } from './institute-dialog.component';
import { InstituteDeletePopupComponent } from './institute-delete-dialog.component';

import { Principal } from '../../shared';

export const instituteRoute: Routes = [
    {
        path: 'institute',
        component: InstituteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.institute.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'institute/:id',
        component: InstituteDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.institute.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const institutePopupRoute: Routes = [
    {
        path: 'institute-new',
        component: InstitutePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ceappApp.institute.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'institute/:id/edit',
        component: InstitutePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ceappApp.institute.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'institute/:id/delete',
        component: InstituteDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ceappApp.institute.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
