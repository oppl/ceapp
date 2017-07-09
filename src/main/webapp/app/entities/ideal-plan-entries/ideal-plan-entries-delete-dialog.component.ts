import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IdealPlanEntries } from './ideal-plan-entries.model';
import { IdealPlanEntriesPopupService } from './ideal-plan-entries-popup.service';
import { IdealPlanEntriesService } from './ideal-plan-entries.service';

@Component({
    selector: 'jhi-ideal-plan-entries-delete-dialog',
    templateUrl: './ideal-plan-entries-delete-dialog.component.html'
})
export class IdealPlanEntriesDeleteDialogComponent {

    idealPlanEntries: IdealPlanEntries;

    constructor(
        private idealPlanEntriesService: IdealPlanEntriesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.idealPlanEntriesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'idealPlanEntriesListModification',
                content: 'Deleted an idealPlanEntries'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ideal-plan-entries-delete-popup',
    template: ''
})
export class IdealPlanEntriesDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private idealPlanEntriesPopupService: IdealPlanEntriesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.idealPlanEntriesPopupService
                .open(IdealPlanEntriesDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
