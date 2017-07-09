import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CollisionSummaryCS } from './collision-summary-cs.model';
import { CollisionSummaryCSPopupService } from './collision-summary-cs-popup.service';
import { CollisionSummaryCSService } from './collision-summary-cs.service';
import { CurriculumSubject, CurriculumSubjectService } from '../curriculum-subject';
import { IdealPlan, IdealPlanService } from '../ideal-plan';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-collision-summary-cs-dialog',
    templateUrl: './collision-summary-cs-dialog.component.html'
})
export class CollisionSummaryCSDialogComponent implements OnInit {

    collisionSummaryCS: CollisionSummaryCS;
    authorities: any[];
    isSaving: boolean;

    curriculumsubjects: CurriculumSubject[];

    idealplans: IdealPlan[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private collisionSummaryCSService: CollisionSummaryCSService,
        private curriculumSubjectService: CurriculumSubjectService,
        private idealPlanService: IdealPlanService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.curriculumSubjectService.query()
            .subscribe((res: ResponseWrapper) => { this.curriculumsubjects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.idealPlanService.query()
            .subscribe((res: ResponseWrapper) => { this.idealplans = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.collisionSummaryCS.id !== undefined) {
            this.subscribeToSaveResponse(
                this.collisionSummaryCSService.update(this.collisionSummaryCS));
        } else {
            this.subscribeToSaveResponse(
                this.collisionSummaryCSService.create(this.collisionSummaryCS));
        }
    }

    private subscribeToSaveResponse(result: Observable<CollisionSummaryCS>) {
        result.subscribe((res: CollisionSummaryCS) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CollisionSummaryCS) {
        this.eventManager.broadcast({ name: 'collisionSummaryCSListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackCurriculumSubjectById(index: number, item: CurriculumSubject) {
        return item.id;
    }

    trackIdealPlanById(index: number, item: IdealPlan) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-collision-summary-cs-popup',
    template: ''
})
export class CollisionSummaryCSPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collisionSummaryCSPopupService: CollisionSummaryCSPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.collisionSummaryCSPopupService
                    .open(CollisionSummaryCSDialogComponent, params['id']);
            } else {
                this.modalRef = this.collisionSummaryCSPopupService
                    .open(CollisionSummaryCSDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
