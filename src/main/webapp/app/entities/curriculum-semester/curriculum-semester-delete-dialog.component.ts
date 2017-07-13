import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CurriculumSemester } from './curriculum-semester.model';
import { CurriculumSemesterPopupService } from './curriculum-semester-popup.service';
import { CurriculumSemesterService } from './curriculum-semester.service';

@Component({
    selector: 'jhi-curriculum-semester-delete-dialog',
    templateUrl: './curriculum-semester-delete-dialog.component.html'
})
export class CurriculumSemesterDeleteDialogComponent {

    curriculumSemester: CurriculumSemester;

    constructor(
        private curriculumSemesterService: CurriculumSemesterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.curriculumSemesterService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'curriculumSemesterListModification',
                content: 'Deleted an curriculumSemester'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-curriculum-semester-delete-popup',
    template: ''
})
export class CurriculumSemesterDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private curriculumSemesterPopupService: CurriculumSemesterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.curriculumSemesterPopupService
                .open(CurriculumSemesterDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
