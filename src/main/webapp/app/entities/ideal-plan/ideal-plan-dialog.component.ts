import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IdealPlan } from './ideal-plan.model';
import { IdealPlanPopupService } from './ideal-plan-popup.service';
import { IdealPlanService } from './ideal-plan.service';
import { Curriculum, CurriculumService } from '../curriculum';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ideal-plan-dialog',
    templateUrl: './ideal-plan-dialog.component.html'
})
export class IdealPlanDialogComponent implements OnInit {

    idealPlan: IdealPlan;
    authorities: any[];
    isSaving: boolean;

    curricula: Curriculum[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private idealPlanService: IdealPlanService,
        private curriculumService: CurriculumService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.curriculumService.query()
            .subscribe((res: ResponseWrapper) => { this.curricula = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.idealPlan.id !== undefined) {
            this.subscribeToSaveResponse(
                this.idealPlanService.update(this.idealPlan));
        } else {
            this.subscribeToSaveResponse(
                this.idealPlanService.create(this.idealPlan));
        }
    }

    private subscribeToSaveResponse(result: Observable<IdealPlan>) {
        result.subscribe((res: IdealPlan) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: IdealPlan) {
        this.eventManager.broadcast({ name: 'idealPlanListModification', content: 'OK'});
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

    trackCurriculumById(index: number, item: Curriculum) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ideal-plan-popup',
    template: ''
})
export class IdealPlanPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private idealPlanPopupService: IdealPlanPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.idealPlanPopupService
                    .open(IdealPlanDialogComponent, params['id']);
            } else {
                this.modalRef = this.idealPlanPopupService
                    .open(IdealPlanDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
