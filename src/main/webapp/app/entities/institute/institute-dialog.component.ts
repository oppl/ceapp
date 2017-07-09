import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Institute } from './institute.model';
import { InstitutePopupService } from './institute-popup.service';
import { InstituteService } from './institute.service';
import { Curriculum, CurriculumService } from '../curriculum';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-institute-dialog',
    templateUrl: './institute-dialog.component.html'
})
export class InstituteDialogComponent implements OnInit {

    institute: Institute;
    authorities: any[];
    isSaving: boolean;

    curricula: Curriculum[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private instituteService: InstituteService,
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
        if (this.institute.id !== undefined) {
            this.subscribeToSaveResponse(
                this.instituteService.update(this.institute));
        } else {
            this.subscribeToSaveResponse(
                this.instituteService.create(this.institute));
        }
    }

    private subscribeToSaveResponse(result: Observable<Institute>) {
        result.subscribe((res: Institute) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Institute) {
        this.eventManager.broadcast({ name: 'instituteListModification', content: 'OK'});
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
    selector: 'jhi-institute-popup',
    template: ''
})
export class InstitutePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private institutePopupService: InstitutePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.institutePopupService
                    .open(InstituteDialogComponent, params['id']);
            } else {
                this.modalRef = this.institutePopupService
                    .open(InstituteDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
