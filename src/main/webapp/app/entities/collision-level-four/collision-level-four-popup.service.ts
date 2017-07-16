import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CollisionLevelFour } from './collision-level-four.model';
import { CollisionLevelFourService } from './collision-level-four.service';

@Injectable()
export class CollisionLevelFourPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private collisionLevelFourService: CollisionLevelFourService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.collisionLevelFourService.find(id).subscribe((collisionLevelFour) => {
                this.collisionLevelFourModalRef(component, collisionLevelFour);
            });
        } else {
            return this.collisionLevelFourModalRef(component, new CollisionLevelFour());
        }
    }

    collisionLevelFourModalRef(component: Component, collisionLevelFour: CollisionLevelFour): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.collisionLevelFour = collisionLevelFour;
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
