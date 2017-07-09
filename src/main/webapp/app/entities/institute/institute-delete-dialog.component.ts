import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Institute } from './institute.model';
import { InstitutePopupService } from './institute-popup.service';
import { InstituteService } from './institute.service';

@Component({
    selector: 'jhi-institute-delete-dialog',
    templateUrl: './institute-delete-dialog.component.html'
})
export class InstituteDeleteDialogComponent {

    institute: Institute;

    constructor(
        private instituteService: InstituteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.instituteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'instituteListModification',
                content: 'Deleted an institute'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-institute-delete-popup',
    template: ''
})
export class InstituteDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private institutePopupService: InstitutePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.institutePopupService
                .open(InstituteDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
