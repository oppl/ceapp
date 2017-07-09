import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Curriculum } from './curriculum.model';
import { CurriculumPopupService } from './curriculum-popup.service';
import { CurriculumService } from './curriculum.service';

@Component({
    selector: 'jhi-curriculum-delete-dialog',
    templateUrl: './curriculum-delete-dialog.component.html'
})
export class CurriculumDeleteDialogComponent {

    curriculum: Curriculum;

    constructor(
        private curriculumService: CurriculumService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.curriculumService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'curriculumListModification',
                content: 'Deleted an curriculum'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-curriculum-delete-popup',
    template: ''
})
export class CurriculumDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private curriculumPopupService: CurriculumPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.curriculumPopupService
                .open(CurriculumDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
