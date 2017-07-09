import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Appointment } from './appointment.model';
import { AppointmentService } from './appointment.service';

@Injectable()
export class AppointmentPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private appointmentService: AppointmentService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.appointmentService.find(id).subscribe((appointment) => {
                appointment.startDateTime = this.datePipe
                    .transform(appointment.startDateTime, 'yyyy-MM-ddThh:mm');
                appointment.endDateTime = this.datePipe
                    .transform(appointment.endDateTime, 'yyyy-MM-ddThh:mm');
                this.appointmentModalRef(component, appointment);
            });
        } else {
            return this.appointmentModalRef(component, new Appointment());
        }
    }

    appointmentModalRef(component: Component, appointment: Appointment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.appointment = appointment;
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
