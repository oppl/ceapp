import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CollisionLevelOne } from './collision-level-one.model';
import { CollisionLevelOneService } from './collision-level-one.service';

@Injectable()
export class CollisionLevelOnePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private collisionLevelOneService: CollisionLevelOneService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.collisionLevelOneService.find(id).subscribe((collisionLevelOne) => {
                this.collisionLevelOneModalRef(component, collisionLevelOne);
            });
        } else {
            return this.collisionLevelOneModalRef(component, new CollisionLevelOne());
        }
    }

    collisionLevelOneModalRef(component: Component, collisionLevelOne: CollisionLevelOne): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.collisionLevelOne = collisionLevelOne;
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
