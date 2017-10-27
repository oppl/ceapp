import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CurriculumSubjectComponent } from './curriculum-subject.component';
import { CurriculumSubjectDetailComponent } from './curriculum-subject-detail.component';
import { CurriculumSubjectPopupComponent } from './curriculum-subject-dialog.component';
import { CurriculumSubjectDeletePopupComponent } from './curriculum-subject-delete-dialog.component';

import { Principal } from '../../shared';

export const curriculumSubjectRoute: Routes = [
    {
        path: 'curriculum-subject',
        component: CurriculumSubjectComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.curriculumSubject.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'curriculum-subject/:id',
        component: CurriculumSubjectDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ceappApp.curriculumSubject.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const curriculumSubjectPopupRoute: Routes = [
    {
        path: 'curriculum-subject-new',
        component: CurriculumSubjectPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ceappApp.curriculumSubject.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'curriculum-subject/:id/edit',
        component: CurriculumSubjectPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ceappApp.curriculumSubject.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'curriculum-subject/:id/delete',
        component: CurriculumSubjectDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ceappApp.curriculumSubject.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
