import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Curriculum } from './curriculum.model';
import { CurriculumService } from './curriculum.service';

@Injectable()
export class CurriculumPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private curriculumService: CurriculumService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.curriculumService.find(id).subscribe((curriculum) => {
                this.curriculumModalRef(component, curriculum);
            });
        } else {
            return this.curriculumModalRef(component, new Curriculum());
        }
    }

    curriculumModalRef(component: Component, curriculum: Curriculum): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.curriculum = curriculum;
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
