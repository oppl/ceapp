import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CollisionLevelThree } from './collision-level-three.model';
import { CollisionLevelThreePopupService } from './collision-level-three-popup.service';
import { CollisionLevelThreeService } from './collision-level-three.service';

@Component({
    selector: 'jhi-collision-level-three-delete-dialog',
    templateUrl: './collision-level-three-delete-dialog.component.html'
})
export class CollisionLevelThreeDeleteDialogComponent {

    collisionLevelThree: CollisionLevelThree;

    constructor(
        private collisionLevelThreeService: CollisionLevelThreeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.collisionLevelThreeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'collisionLevelThreeListModification',
                content: 'Deleted an collisionLevelThree'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-collision-level-three-delete-popup',
    template: ''
})
export class CollisionLevelThreeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collisionLevelThreePopupService: CollisionLevelThreePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.collisionLevelThreePopupService
                .open(CollisionLevelThreeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
