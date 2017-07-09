import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IdealPlanEntries } from './ideal-plan-entries.model';
import { IdealPlanEntriesPopupService } from './ideal-plan-entries-popup.service';
import { IdealPlanEntriesService } from './ideal-plan-entries.service';
import { Subject, SubjectService } from '../subject';
import { IdealPlan, IdealPlanService } from '../ideal-plan';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ideal-plan-entries-dialog',
    templateUrl: './ideal-plan-entries-dialog.component.html'
})
export class IdealPlanEntriesDialogComponent implements OnInit {

    idealPlanEntries: IdealPlanEntries;
    authorities: any[];
    isSaving: boolean;

    subjects: Subject[];

    idealplans: IdealPlan[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private idealPlanEntriesService: IdealPlanEntriesService,
        private subjectService: SubjectService,
        private idealPlanService: IdealPlanService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.subjectService.query()
            .subscribe((res: ResponseWrapper) => { this.subjects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.idealPlanService.query()
            .subscribe((res: ResponseWrapper) => { this.idealplans = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.idealPlanEntries.id !== undefined) {
            this.subscribeToSaveResponse(
                this.idealPlanEntriesService.update(this.idealPlanEntries));
        } else {
            this.subscribeToSaveResponse(
                this.idealPlanEntriesService.create(this.idealPlanEntries));
        }
    }

    private subscribeToSaveResponse(result: Observable<IdealPlanEntries>) {
        result.subscribe((res: IdealPlanEntries) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: IdealPlanEntries) {
        this.eventManager.broadcast({ name: 'idealPlanEntriesListModification', content: 'OK'});
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

    trackSubjectById(index: number, item: Subject) {
        return item.id;
    }

    trackIdealPlanById(index: number, item: IdealPlan) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ideal-plan-entries-popup',
    template: ''
})
export class IdealPlanEntriesPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private idealPlanEntriesPopupService: IdealPlanEntriesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.idealPlanEntriesPopupService
                    .open(IdealPlanEntriesDialogComponent, params['id']);
            } else {
                this.modalRef = this.idealPlanEntriesPopupService
                    .open(IdealPlanEntriesDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
