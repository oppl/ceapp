import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CurriculumSubject } from './curriculum-subject.model';
import { CurriculumSubjectService } from './curriculum-subject.service';

@Injectable()
export class CurriculumSubjectPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private curriculumSubjectService: CurriculumSubjectService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.curriculumSubjectService.find(id).subscribe((curriculumSubject) => {
                this.curriculumSubjectModalRef(component, curriculumSubject);
            });
        } else {
            return this.curriculumSubjectModalRef(component, new CurriculumSubject());
        }
    }

    curriculumSubjectModalRef(component: Component, curriculumSubject: CurriculumSubject): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.curriculumSubject = curriculumSubject;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
