import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CurriculumSubject } from './curriculum-subject.model';
import { CurriculumSubjectPopupService } from './curriculum-subject-popup.service';
import { CurriculumSubjectService } from './curriculum-subject.service';

@Component({
    selector: 'jhi-curriculum-subject-delete-dialog',
    templateUrl: './curriculum-subject-delete-dialog.component.html'
})
export class CurriculumSubjectDeleteDialogComponent {

    curriculumSubject: CurriculumSubject;

    constructor(
        private curriculumSubjectService: CurriculumSubjectService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.curriculumSubjectService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'curriculumSubjectListModification',
                content: 'Deleted an curriculumSubject'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-curriculum-subject-delete-popup',
    template: ''
})
export class CurriculumSubjectDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private curriculumSubjectPopupService: CurriculumSubjectPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.curriculumSubjectPopupService
                .open(CurriculumSubjectDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
