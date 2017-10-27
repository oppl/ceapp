import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LvaComponent } from './lva.component';
import { LvaDetailComponent } from './lva-detail.component';
import { LvaPopupComponent } from './lva-dialog.component';
import { LvaDeletePopupComponent } from './lva-delete-dialog.component';

import { Principal } from '../../shared';

export const lvaRoute: Routes = [
    {
        path: 'lva',
        component: LvaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.lva.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'lva/:id',
        component: LvaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.lva.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lvaPopupRoute: Routes = [
    {
        path: 'lva-new',
        component: LvaPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ceappApp.lva.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lva/:id/edit',
        component: LvaPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ceappApp.lva.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lva/:id/delete',
        component: LvaDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ceappApp.lva.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
