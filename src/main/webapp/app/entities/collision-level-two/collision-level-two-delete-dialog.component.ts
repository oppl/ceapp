import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CollisionLevelTwo } from './collision-level-two.model';
import { CollisionLevelTwoPopupService } from './collision-level-two-popup.service';
import { CollisionLevelTwoService } from './collision-level-two.service';

@Component({
    selector: 'jhi-collision-level-two-delete-dialog',
    templateUrl: './collision-level-two-delete-dialog.component.html'
})
export class CollisionLevelTwoDeleteDialogComponent {

    collisionLevelTwo: CollisionLevelTwo;

    constructor(
        private collisionLevelTwoService: CollisionLevelTwoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.collisionLevelTwoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'collisionLevelTwoListModification',
                content: 'Deleted an collisionLevelTwo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-collision-level-two-delete-popup',
    template: ''
})
export class CollisionLevelTwoDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collisionLevelTwoPopupService: CollisionLevelTwoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.collisionLevelTwoPopupService
                .open(CollisionLevelTwoDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
