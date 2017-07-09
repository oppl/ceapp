import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CollisionSummaryCS } from './collision-summary-cs.model';
import { CollisionSummaryCSService } from './collision-summary-cs.service';

@Injectable()
export class CollisionSummaryCSPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private collisionSummaryCSService: CollisionSummaryCSService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.collisionSummaryCSService.find(id).subscribe((collisionSummaryCS) => {
                this.collisionSummaryCSModalRef(component, collisionSummaryCS);
            });
        } else {
            return this.collisionSummaryCSModalRef(component, new CollisionSummaryCS());
        }
    }

    collisionSummaryCSModalRef(component: Component, collisionSummaryCS: CollisionSummaryCS): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.collisionSummaryCS = collisionSummaryCS;
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
