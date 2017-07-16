import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CollisionLevelFour } from './collision-level-four.model';
import { CollisionLevelFourPopupService } from './collision-level-four-popup.service';
import { CollisionLevelFourService } from './collision-level-four.service';

@Component({
    selector: 'jhi-collision-level-four-delete-dialog',
    templateUrl: './collision-level-four-delete-dialog.component.html'
})
export class CollisionLevelFourDeleteDialogComponent {

    collisionLevelFour: CollisionLevelFour;

    constructor(
        private collisionLevelFourService: CollisionLevelFourService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.collisionLevelFourService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'collisionLevelFourListModification',
                content: 'Deleted an collisionLevelFour'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-collision-level-four-delete-popup',
    template: ''
})
export class CollisionLevelFourDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collisionLevelFourPopupService: CollisionLevelFourPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.collisionLevelFourPopupService
                .open(CollisionLevelFourDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
