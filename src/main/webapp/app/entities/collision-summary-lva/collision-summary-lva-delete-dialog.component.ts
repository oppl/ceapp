import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CollisionSummaryLva } from './collision-summary-lva.model';
import { CollisionSummaryLvaPopupService } from './collision-summary-lva-popup.service';
import { CollisionSummaryLvaService } from './collision-summary-lva.service';

@Component({
    selector: 'jhi-collision-summary-lva-delete-dialog',
    templateUrl: './collision-summary-lva-delete-dialog.component.html'
})
export class CollisionSummaryLvaDeleteDialogComponent {

    collisionSummaryLva: CollisionSummaryLva;

    constructor(
        private collisionSummaryLvaService: CollisionSummaryLvaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.collisionSummaryLvaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'collisionSummaryLvaListModification',
                content: 'Deleted an collisionSummaryLva'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-collision-summary-lva-delete-popup',
    template: ''
})
export class CollisionSummaryLvaDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collisionSummaryLvaPopupService: CollisionSummaryLvaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.collisionSummaryLvaPopupService
                .open(CollisionSummaryLvaDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
