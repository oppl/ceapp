import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CollisionLevelFive } from './collision-level-five.model';
import { CollisionLevelFiveService } from './collision-level-five.service';

@Injectable()
export class CollisionLevelFivePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private collisionLevelFiveService: CollisionLevelFiveService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.collisionLevelFiveService.find(id).subscribe((collisionLevelFive) => {
                this.collisionLevelFiveModalRef(component, collisionLevelFive);
            });
        } else {
            return this.collisionLevelFiveModalRef(component, new CollisionLevelFive());
        }
    }

    collisionLevelFiveModalRef(component: Component, collisionLevelFive: CollisionLevelFive): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.collisionLevelFive = collisionLevelFive;
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
