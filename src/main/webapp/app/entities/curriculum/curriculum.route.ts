import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CurriculumComponent } from './curriculum.component';
import { CurriculumDetailComponent } from './curriculum-detail.component';
import { CurriculumPopupComponent } from './curriculum-dialog.component';
import { CurriculumDeletePopupComponent } from './curriculum-delete-dialog.component';

import { Principal } from '../../shared';

export const curriculumRoute: Routes = [
    {
        path: 'curriculum',
        component: CurriculumComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.curriculum.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'curriculum/:id',
        component: CurriculumDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.curriculum.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const curriculumPopupRoute: Routes = [
    {
        path: 'curriculum-new',
        component: CurriculumPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.curriculum.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'curriculum/:id/edit',
        component: CurriculumPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.curriculum.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'curriculum/:id/delete',
        component: CurriculumDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.curriculum.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
