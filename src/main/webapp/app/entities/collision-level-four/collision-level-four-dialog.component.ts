import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CollisionLevelFour } from './collision-level-four.model';
import { CollisionLevelFourPopupService } from './collision-level-four-popup.service';
import { CollisionLevelFourService } from './collision-level-four.service';
import { CollisionLevelThree, CollisionLevelThreeService } from '../collision-level-three';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-collision-level-four-dialog',
    templateUrl: './collision-level-four-dialog.component.html'
})
export class CollisionLevelFourDialogComponent implements OnInit {

    collisionLevelFour: CollisionLevelFour;
    authorities: any[];
    isSaving: boolean;

    collisionlevelthrees: CollisionLevelThree[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private collisionLevelFourService: CollisionLevelFourService,
        private collisionLevelThreeService: CollisionLevelThreeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.collisionLevelThreeService.query()
            .subscribe((res: ResponseWrapper) => { this.collisionlevelthrees = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.collisionLevelFour.id !== undefined) {
            this.subscribeToSaveResponse(
                this.collisionLevelFourService.update(this.collisionLevelFour));
        } else {
            this.subscribeToSaveResponse(
                this.collisionLevelFourService.create(this.collisionLevelFour));
        }
    }

    private subscribeToSaveResponse(result: Observable<CollisionLevelFour>) {
        result.subscribe((res: CollisionLevelFour) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CollisionLevelFour) {
        this.eventManager.broadcast({ name: 'collisionLevelFourListModification', content: 'OK'});
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

    trackCollisionLevelThreeById(index: number, item: CollisionLevelThree) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-collision-level-four-popup',
    template: ''
})
export class CollisionLevelFourPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collisionLevelFourPopupService: CollisionLevelFourPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.collisionLevelFourPopupService
                    .open(CollisionLevelFourDialogComponent, params['id']);
            } else {
                this.modalRef = this.collisionLevelFourPopupService
                    .open(CollisionLevelFourDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
