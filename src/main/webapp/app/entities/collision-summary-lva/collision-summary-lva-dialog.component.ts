import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CollisionSummaryLva } from './collision-summary-lva.model';
import { CollisionSummaryLvaPopupService } from './collision-summary-lva-popup.service';
import { CollisionSummaryLvaService } from './collision-summary-lva.service';
import { CollisionSummaryCS, CollisionSummaryCSService } from '../collision-summary-cs';
import { Lva, LvaService } from '../lva';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-collision-summary-lva-dialog',
    templateUrl: './collision-summary-lva-dialog.component.html'
})
export class CollisionSummaryLvaDialogComponent implements OnInit {

    collisionSummaryLva: CollisionSummaryLva;
    authorities: any[];
    isSaving: boolean;

    collisionsummarycs: CollisionSummaryCS[];

    lvas: Lva[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private collisionSummaryLvaService: CollisionSummaryLvaService,
        private collisionSummaryCSService: CollisionSummaryCSService,
        private lvaService: LvaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.collisionSummaryCSService.query()
            .subscribe((res: ResponseWrapper) => { this.collisionsummarycs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.lvaService.query()
            .subscribe((res: ResponseWrapper) => { this.lvas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.collisionSummaryLva.id !== undefined) {
            this.subscribeToSaveResponse(
                this.collisionSummaryLvaService.update(this.collisionSummaryLva));
        } else {
            this.subscribeToSaveResponse(
                this.collisionSummaryLvaService.create(this.collisionSummaryLva));
        }
    }

    private subscribeToSaveResponse(result: Observable<CollisionSummaryLva>) {
        result.subscribe((res: CollisionSummaryLva) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CollisionSummaryLva) {
        this.eventManager.broadcast({ name: 'collisionSummaryLvaListModification', content: 'OK'});
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

    trackCollisionSummaryCSById(index: number, item: CollisionSummaryCS) {
        return item.id;
    }

    trackLvaById(index: number, item: Lva) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-collision-summary-lva-popup',
    template: ''
})
export class CollisionSummaryLvaPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collisionSummaryLvaPopupService: CollisionSummaryLvaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.collisionSummaryLvaPopupService
                    .open(CollisionSummaryLvaDialogComponent, params['id']);
            } else {
                this.modalRef = this.collisionSummaryLvaPopupService
                    .open(CollisionSummaryLvaDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
