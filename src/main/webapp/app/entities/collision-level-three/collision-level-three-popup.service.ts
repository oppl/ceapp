import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CollisionLevelThree } from './collision-level-three.model';
import { CollisionLevelThreeService } from './collision-level-three.service';

@Injectable()
export class CollisionLevelThreePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private collisionLevelThreeService: CollisionLevelThreeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.collisionLevelThreeService.find(id).subscribe((collisionLevelThree) => {
                this.collisionLevelThreeModalRef(component, collisionLevelThree);
            });
        } else {
            return this.collisionLevelThreeModalRef(component, new CollisionLevelThree());
        }
    }

    collisionLevelThreeModalRef(component: Component, collisionLevelThree: CollisionLevelThree): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.collisionLevelThree = collisionLevelThree;
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
