import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CollisionLevelFive } from './collision-level-five.model';
import { CollisionLevelFivePopupService } from './collision-level-five-popup.service';
import { CollisionLevelFiveService } from './collision-level-five.service';

@Component({
    selector: 'jhi-collision-level-five-delete-dialog',
    templateUrl: './collision-level-five-delete-dialog.component.html'
})
export class CollisionLevelFiveDeleteDialogComponent {

    collisionLevelFive: CollisionLevelFive;

    constructor(
        private collisionLevelFiveService: CollisionLevelFiveService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.collisionLevelFiveService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'collisionLevelFiveListModification',
                content: 'Deleted an collisionLevelFive'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-collision-level-five-delete-popup',
    template: ''
})
export class CollisionLevelFiveDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collisionLevelFivePopupService: CollisionLevelFivePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.collisionLevelFivePopupService
                .open(CollisionLevelFiveDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
