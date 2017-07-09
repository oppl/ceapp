import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CurriculumSubject } from './curriculum-subject.model';
import { CurriculumSubjectPopupService } from './curriculum-subject-popup.service';
import { CurriculumSubjectService } from './curriculum-subject.service';
import { Lva, LvaService } from '../lva';
import { Curriculum, CurriculumService } from '../curriculum';
import { Subject, SubjectService } from '../subject';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-curriculum-subject-dialog',
    templateUrl: './curriculum-subject-dialog.component.html'
})
export class CurriculumSubjectDialogComponent implements OnInit {

    curriculumSubject: CurriculumSubject;
    authorities: any[];
    isSaving: boolean;

    lvas: Lva[];

    curricula: Curriculum[];

    subjects: Subject[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private curriculumSubjectService: CurriculumSubjectService,
        private lvaService: LvaService,
        private curriculumService: CurriculumService,
        private subjectService: SubjectService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.lvaService.query()
            .subscribe((res: ResponseWrapper) => { this.lvas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.curriculumService.query()
            .subscribe((res: ResponseWrapper) => { this.curricula = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.subjectService.query()
            .subscribe((res: ResponseWrapper) => { this.subjects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.curriculumSubject.id !== undefined) {
            this.subscribeToSaveResponse(
                this.curriculumSubjectService.update(this.curriculumSubject));
        } else {
            this.subscribeToSaveResponse(
                this.curriculumSubjectService.create(this.curriculumSubject));
        }
    }

    private subscribeToSaveResponse(result: Observable<CurriculumSubject>) {
        result.subscribe((res: CurriculumSubject) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CurriculumSubject) {
        this.eventManager.broadcast({ name: 'curriculumSubjectListModification', content: 'OK'});
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

    trackLvaById(index: number, item: Lva) {
        return item.id;
    }

    trackCurriculumById(index: number, item: Curriculum) {
        return item.id;
    }

    trackSubjectById(index: number, item: Subject) {
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
    selector: 'jhi-curriculum-subject-popup',
    template: ''
})
export class CurriculumSubjectPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private curriculumSubjectPopupService: CurriculumSubjectPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.curriculumSubjectPopupService
                    .open(CurriculumSubjectDialogComponent, params['id']);
            } else {
                this.modalRef = this.curriculumSubjectPopupService
                    .open(CurriculumSubjectDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
