import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Lva } from './lva.model';
import { LvaPopupService } from './lva-popup.service';
import { LvaService } from './lva.service';

@Component({
    selector: 'jhi-lva-delete-dialog',
    templateUrl: './lva-delete-dialog.component.html'
})
export class LvaDeleteDialogComponent {

    lva: Lva;

    constructor(
        private lvaService: LvaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lvaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lvaListModification',
                content: 'Deleted an lva'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lva-delete-popup',
    template: ''
})
export class LvaDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lvaPopupService: LvaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.lvaPopupService
                .open(LvaDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
