import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Lva } from './lva.model';
import { LvaPopupService } from './lva-popup.service';
import { LvaService } from './lva.service';
import { Subject, SubjectService } from '../subject';
import { CurriculumSubject, CurriculumSubjectService } from '../curriculum-subject';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-lva-dialog',
    templateUrl: './lva-dialog.component.html'
})
export class LvaDialogComponent implements OnInit {

    lva: Lva;
    authorities: any[];
    isSaving: boolean;

    subjects: Subject[];

    curriculumsubjects: CurriculumSubject[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private lvaService: LvaService,
        private subjectService: SubjectService,
        private curriculumSubjectService: CurriculumSubjectService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.subjectService.query()
            .subscribe((res: ResponseWrapper) => { this.subjects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.curriculumSubjectService.query()
            .subscribe((res: ResponseWrapper) => { this.curriculumsubjects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.lva.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lvaService.update(this.lva));
        } else {
            this.subscribeToSaveResponse(
                this.lvaService.create(this.lva));
        }
    }

    private subscribeToSaveResponse(result: Observable<Lva>) {
        result.subscribe((res: Lva) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Lva) {
        this.eventManager.broadcast({ name: 'lvaListModification', content: 'OK'});
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

    trackCurriculumSubjectById(index: number, item: CurriculumSubject) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-lva-popup',
    template: ''
})
export class LvaPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lvaPopupService: LvaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.lvaPopupService
                    .open(LvaDialogComponent, params['id']);
            } else {
                this.modalRef = this.lvaPopupService
                    .open(LvaDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
