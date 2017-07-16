import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CollisionLevelOne } from './collision-level-one.model';
import { CollisionLevelOnePopupService } from './collision-level-one-popup.service';
import { CollisionLevelOneService } from './collision-level-one.service';

@Component({
    selector: 'jhi-collision-level-one-delete-dialog',
    templateUrl: './collision-level-one-delete-dialog.component.html'
})
export class CollisionLevelOneDeleteDialogComponent {

    collisionLevelOne: CollisionLevelOne;

    constructor(
        private collisionLevelOneService: CollisionLevelOneService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.collisionLevelOneService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'collisionLevelOneListModification',
                content: 'Deleted an collisionLevelOne'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-collision-level-one-delete-popup',
    template: ''
})
export class CollisionLevelOneDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collisionLevelOnePopupService: CollisionLevelOnePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.collisionLevelOnePopupService
                .open(CollisionLevelOneDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
