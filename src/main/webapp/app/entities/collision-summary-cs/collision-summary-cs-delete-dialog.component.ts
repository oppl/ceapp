import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CollisionSummaryCS } from './collision-summary-cs.model';
import { CollisionSummaryCSPopupService } from './collision-summary-cs-popup.service';
import { CollisionSummaryCSService } from './collision-summary-cs.service';

@Component({
    selector: 'jhi-collision-summary-cs-delete-dialog',
    templateUrl: './collision-summary-cs-delete-dialog.component.html'
})
export class CollisionSummaryCSDeleteDialogComponent {

    collisionSummaryCS: CollisionSummaryCS;

    constructor(
        private collisionSummaryCSService: CollisionSummaryCSService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.collisionSummaryCSService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'collisionSummaryCSListModification',
                content: 'Deleted an collisionSummaryCS'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-collision-summary-cs-delete-popup',
    template: ''
})
export class CollisionSummaryCSDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collisionSummaryCSPopupService: CollisionSummaryCSPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.collisionSummaryCSPopupService
                .open(CollisionSummaryCSDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
