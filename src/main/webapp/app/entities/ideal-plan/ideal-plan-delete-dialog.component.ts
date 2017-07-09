import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IdealPlan } from './ideal-plan.model';
import { IdealPlanPopupService } from './ideal-plan-popup.service';
import { IdealPlanService } from './ideal-plan.service';

@Component({
    selector: 'jhi-ideal-plan-delete-dialog',
    templateUrl: './ideal-plan-delete-dialog.component.html'
})
export class IdealPlanDeleteDialogComponent {

    idealPlan: IdealPlan;

    constructor(
        private idealPlanService: IdealPlanService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.idealPlanService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'idealPlanListModification',
                content: 'Deleted an idealPlan'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ideal-plan-delete-popup',
    template: ''
})
export class IdealPlanDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private idealPlanPopupService: IdealPlanPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.idealPlanPopupService
                .open(IdealPlanDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
