import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Curriculum } from './curriculum.model';
import { CurriculumPopupService } from './curriculum-popup.service';
import { CurriculumService } from './curriculum.service';
import { Institute, InstituteService } from '../institute';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-curriculum-dialog',
    templateUrl: './curriculum-dialog.component.html'
})
export class CurriculumDialogComponent implements OnInit {

    curriculum: Curriculum;
    authorities: any[];
    isSaving: boolean;

    institutes: Institute[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private curriculumService: CurriculumService,
        private instituteService: InstituteService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.instituteService.query()
            .subscribe((res: ResponseWrapper) => { this.institutes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.curriculum.id !== undefined) {
            this.subscribeToSaveResponse(
                this.curriculumService.update(this.curriculum));
        } else {
            this.subscribeToSaveResponse(
                this.curriculumService.create(this.curriculum));
        }
    }

    private subscribeToSaveResponse(result: Observable<Curriculum>) {
        result.subscribe((res: Curriculum) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Curriculum) {
        this.eventManager.broadcast({ name: 'curriculumListModification', content: 'OK'});
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

    trackInstituteById(index: number, item: Institute) {
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
    selector: 'jhi-curriculum-popup',
    template: ''
})
export class CurriculumPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private curriculumPopupService: CurriculumPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.curriculumPopupService
                    .open(CurriculumDialogComponent, params['id']);
            } else {
                this.modalRef = this.curriculumPopupService
                    .open(CurriculumDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
