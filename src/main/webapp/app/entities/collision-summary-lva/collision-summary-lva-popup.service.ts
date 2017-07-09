import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CollisionSummaryLva } from './collision-summary-lva.model';
import { CollisionSummaryLvaService } from './collision-summary-lva.service';

@Injectable()
export class CollisionSummaryLvaPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private collisionSummaryLvaService: CollisionSummaryLvaService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.collisionSummaryLvaService.find(id).subscribe((collisionSummaryLva) => {
                this.collisionSummaryLvaModalRef(component, collisionSummaryLva);
            });
        } else {
            return this.collisionSummaryLvaModalRef(component, new CollisionSummaryLva());
        }
    }

    collisionSummaryLvaModalRef(component: Component, collisionSummaryLva: CollisionSummaryLva): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.collisionSummaryLva = collisionSummaryLva;
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
