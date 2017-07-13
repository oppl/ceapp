import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CurriculumSemesterComponent } from './curriculum-semester.component';
import { CurriculumSemesterDetailComponent } from './curriculum-semester-detail.component';
import { CurriculumSemesterPopupComponent } from './curriculum-semester-dialog.component';
import { CurriculumSemesterDeletePopupComponent } from './curriculum-semester-delete-dialog.component';

import { Principal } from '../../shared';

export const curriculumSemesterRoute: Routes = [
    {
        path: 'curriculum-semester',
        component: CurriculumSemesterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.curriculumSemester.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'curriculum-semester/:id',
        component: CurriculumSemesterDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.curriculumSemester.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const curriculumSemesterPopupRoute: Routes = [
    {
        path: 'curriculum-semester-new',
        component: CurriculumSemesterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.curriculumSemester.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'curriculum-semester/:id/edit',
        component: CurriculumSemesterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.curriculumSemester.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'curriculum-semester/:id/delete',
        component: CurriculumSemesterDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.curriculumSemester.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
