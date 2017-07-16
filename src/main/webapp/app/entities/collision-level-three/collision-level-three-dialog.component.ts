import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CollisionLevelThree } from './collision-level-three.model';
import { CollisionLevelThreePopupService } from './collision-level-three-popup.service';
import { CollisionLevelThreeService } from './collision-level-three.service';
import { CollisionLevelTwo, CollisionLevelTwoService } from '../collision-level-two';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-collision-level-three-dialog',
    templateUrl: './collision-level-three-dialog.component.html'
})
export class CollisionLevelThreeDialogComponent implements OnInit {

    collisionLevelThree: CollisionLevelThree;
    authorities: any[];
    isSaving: boolean;

    collisionleveltwos: CollisionLevelTwo[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private collisionLevelThreeService: CollisionLevelThreeService,
        private collisionLevelTwoService: CollisionLevelTwoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.collisionLevelTwoService.query()
            .subscribe((res: ResponseWrapper) => { this.collisionleveltwos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.collisionLevelThree.id !== undefined) {
            this.subscribeToSaveResponse(
                this.collisionLevelThreeService.update(this.collisionLevelThree));
        } else {
            this.subscribeToSaveResponse(
                this.collisionLevelThreeService.create(this.collisionLevelThree));
        }
    }

    private subscribeToSaveResponse(result: Observable<CollisionLevelThree>) {
        result.subscribe((res: CollisionLevelThree) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CollisionLevelThree) {
        this.eventManager.broadcast({ name: 'collisionLevelThreeListModification', content: 'OK'});
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

    trackCollisionLevelTwoById(index: number, item: CollisionLevelTwo) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-collision-level-three-popup',
    template: ''
})
export class CollisionLevelThreePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collisionLevelThreePopupService: CollisionLevelThreePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.collisionLevelThreePopupService
                    .open(CollisionLevelThreeDialogComponent, params['id']);
            } else {
                this.modalRef = this.collisionLevelThreePopupService
                    .open(CollisionLevelThreeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
