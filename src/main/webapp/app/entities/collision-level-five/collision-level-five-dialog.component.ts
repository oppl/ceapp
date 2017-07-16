import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CollisionLevelFive } from './collision-level-five.model';
import { CollisionLevelFivePopupService } from './collision-level-five-popup.service';
import { CollisionLevelFiveService } from './collision-level-five.service';
import { CollisionLevelFour, CollisionLevelFourService } from '../collision-level-four';
import { Appointment, AppointmentService } from '../appointment';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-collision-level-five-dialog',
    templateUrl: './collision-level-five-dialog.component.html'
})
export class CollisionLevelFiveDialogComponent implements OnInit {

    collisionLevelFive: CollisionLevelFive;
    authorities: any[];
    isSaving: boolean;

    collisionlevelfours: CollisionLevelFour[];

    appointments: Appointment[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private collisionLevelFiveService: CollisionLevelFiveService,
        private collisionLevelFourService: CollisionLevelFourService,
        private appointmentService: AppointmentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.collisionLevelFourService.query()
            .subscribe((res: ResponseWrapper) => { this.collisionlevelfours = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.appointmentService.query()
            .subscribe((res: ResponseWrapper) => { this.appointments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.collisionLevelFive.id !== undefined) {
            this.subscribeToSaveResponse(
                this.collisionLevelFiveService.update(this.collisionLevelFive));
        } else {
            this.subscribeToSaveResponse(
                this.collisionLevelFiveService.create(this.collisionLevelFive));
        }
    }

    private subscribeToSaveResponse(result: Observable<CollisionLevelFive>) {
        result.subscribe((res: CollisionLevelFive) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CollisionLevelFive) {
        this.eventManager.broadcast({ name: 'collisionLevelFiveListModification', content: 'OK'});
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

    trackCollisionLevelFourById(index: number, item: CollisionLevelFour) {
        return item.id;
    }

    trackAppointmentById(index: number, item: Appointment) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-collision-level-five-popup',
    template: ''
})
export class CollisionLevelFivePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collisionLevelFivePopupService: CollisionLevelFivePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.collisionLevelFivePopupService
                    .open(CollisionLevelFiveDialogComponent, params['id']);
            } else {
                this.modalRef = this.collisionLevelFivePopupService
                    .open(CollisionLevelFiveDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
