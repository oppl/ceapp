import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CurriculumSemester } from './curriculum-semester.model';
import { CurriculumSemesterService } from './curriculum-semester.service';

@Injectable()
export class CurriculumSemesterPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private curriculumSemesterService: CurriculumSemesterService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.curriculumSemesterService.find(id).subscribe((curriculumSemester) => {
                this.curriculumSemesterModalRef(component, curriculumSemester);
            });
        } else {
            return this.curriculumSemesterModalRef(component, new CurriculumSemester());
        }
    }

    curriculumSemesterModalRef(component: Component, curriculumSemester: CurriculumSemester): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.curriculumSemester = curriculumSemester;
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
