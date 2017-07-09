import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IdealPlan } from './ideal-plan.model';
import { IdealPlanService } from './ideal-plan.service';

@Injectable()
export class IdealPlanPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private idealPlanService: IdealPlanService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.idealPlanService.find(id).subscribe((idealPlan) => {
                this.idealPlanModalRef(component, idealPlan);
            });
        } else {
            return this.idealPlanModalRef(component, new IdealPlan());
        }
    }

    idealPlanModalRef(component: Component, idealPlan: IdealPlan): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.idealPlan = idealPlan;
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
