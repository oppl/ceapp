import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CollisionLevelTwo } from './collision-level-two.model';
import { CollisionLevelTwoPopupService } from './collision-level-two-popup.service';
import { CollisionLevelTwoService } from './collision-level-two.service';
import { CollisionLevelOne, CollisionLevelOneService } from '../collision-level-one';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-collision-level-two-dialog',
    templateUrl: './collision-level-two-dialog.component.html'
})
export class CollisionLevelTwoDialogComponent implements OnInit {

    collisionLevelTwo: CollisionLevelTwo;
    authorities: any[];
    isSaving: boolean;

    collisionlevelones: CollisionLevelOne[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private collisionLevelTwoService: CollisionLevelTwoService,
        private collisionLevelOneService: CollisionLevelOneService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.collisionLevelOneService.query()
            .subscribe((res: ResponseWrapper) => { this.collisionlevelones = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.collisionLevelTwo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.collisionLevelTwoService.update(this.collisionLevelTwo));
        } else {
            this.subscribeToSaveResponse(
                this.collisionLevelTwoService.create(this.collisionLevelTwo));
        }
    }

    private subscribeToSaveResponse(result: Observable<CollisionLevelTwo>) {
        result.subscribe((res: CollisionLevelTwo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CollisionLevelTwo) {
        this.eventManager.broadcast({ name: 'collisionLevelTwoListModification', content: 'OK'});
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

    trackCollisionLevelOneById(index: number, item: CollisionLevelOne) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-collision-level-two-popup',
    template: ''
})
export class CollisionLevelTwoPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collisionLevelTwoPopupService: CollisionLevelTwoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.collisionLevelTwoPopupService
                    .open(CollisionLevelTwoDialogComponent, params['id']);
            } else {
                this.modalRef = this.collisionLevelTwoPopupService
                    .open(CollisionLevelTwoDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
