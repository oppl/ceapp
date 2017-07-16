import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CollisionLevelOne } from './collision-level-one.model';
import { CollisionLevelOnePopupService } from './collision-level-one-popup.service';
import { CollisionLevelOneService } from './collision-level-one.service';
import { CurriculumSubject, CurriculumSubjectService } from '../curriculum-subject';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-collision-level-one-dialog',
    templateUrl: './collision-level-one-dialog.component.html'
})
export class CollisionLevelOneDialogComponent implements OnInit {

    collisionLevelOne: CollisionLevelOne;
    authorities: any[];
    isSaving: boolean;

    curriculumsubjects: CurriculumSubject[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private collisionLevelOneService: CollisionLevelOneService,
        private curriculumSubjectService: CurriculumSubjectService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.curriculumSubjectService.query()
            .subscribe((res: ResponseWrapper) => { this.curriculumsubjects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.collisionLevelOne.id !== undefined) {
            this.subscribeToSaveResponse(
                this.collisionLevelOneService.update(this.collisionLevelOne));
        } else {
            this.subscribeToSaveResponse(
                this.collisionLevelOneService.create(this.collisionLevelOne));
        }
    }

    private subscribeToSaveResponse(result: Observable<CollisionLevelOne>) {
        result.subscribe((res: CollisionLevelOne) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CollisionLevelOne) {
        this.eventManager.broadcast({ name: 'collisionLevelOneListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-collision-level-one-popup',
    template: ''
})
export class CollisionLevelOnePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collisionLevelOnePopupService: CollisionLevelOnePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.collisionLevelOnePopupService
                    .open(CollisionLevelOneDialogComponent, params['id']);
            } else {
                this.modalRef = this.collisionLevelOnePopupService
                    .open(CollisionLevelOneDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
