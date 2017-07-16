import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CollisionLevelTwo } from './collision-level-two.model';
import { CollisionLevelTwoService } from './collision-level-two.service';

@Injectable()
export class CollisionLevelTwoPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private collisionLevelTwoService: CollisionLevelTwoService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.collisionLevelTwoService.find(id).subscribe((collisionLevelTwo) => {
                this.collisionLevelTwoModalRef(component, collisionLevelTwo);
            });
        } else {
            return this.collisionLevelTwoModalRef(component, new CollisionLevelTwo());
        }
    }

    collisionLevelTwoModalRef(component: Component, collisionLevelTwo: CollisionLevelTwo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.collisionLevelTwo = collisionLevelTwo;
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
