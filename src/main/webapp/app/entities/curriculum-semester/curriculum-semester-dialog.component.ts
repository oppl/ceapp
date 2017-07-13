import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CurriculumSemester } from './curriculum-semester.model';
import { CurriculumSemesterPopupService } from './curriculum-semester-popup.service';
import { CurriculumSemesterService } from './curriculum-semester.service';
import { Curriculum, CurriculumService } from '../curriculum';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-curriculum-semester-dialog',
    templateUrl: './curriculum-semester-dialog.component.html'
})
export class CurriculumSemesterDialogComponent implements OnInit {

    curriculumSemester: CurriculumSemester;
    authorities: any[];
    isSaving: boolean;

    curricula: Curriculum[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private curriculumSemesterService: CurriculumSemesterService,
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
        if (this.curriculumSemester.id !== undefined) {
            this.subscribeToSaveResponse(
                this.curriculumSemesterService.update(this.curriculumSemester));
        } else {
            this.subscribeToSaveResponse(
                this.curriculumSemesterService.create(this.curriculumSemester));
        }
    }

    private subscribeToSaveResponse(result: Observable<CurriculumSemester>) {
        result.subscribe((res: CurriculumSemester) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CurriculumSemester) {
        this.eventManager.broadcast({ name: 'curriculumSemesterListModification', content: 'OK'});
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
    selector: 'jhi-curriculum-semester-popup',
    template: ''
})
export class CurriculumSemesterPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private curriculumSemesterPopupService: CurriculumSemesterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.curriculumSemesterPopupService
                    .open(CurriculumSemesterDialogComponent, params['id']);
            } else {
                this.modalRef = this.curriculumSemesterPopupService
                    .open(CurriculumSemesterDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
