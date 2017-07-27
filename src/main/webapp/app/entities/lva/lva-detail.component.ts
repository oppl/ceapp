import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService  } from 'ng-jhipster';

import { Lva } from './lva.model';
import { LvaService } from './lva.service';
import {Appointment} from '../appointment/appointment.model';
import {AppointmentService} from "../appointment/appointment.service";
import {ResponseWrapper} from "../../shared/model/response-wrapper.model";

@Component({
    selector: 'jhi-lva-detail',
    templateUrl: './lva-detail.component.html'
})
export class LvaDetailComponent implements OnInit, OnDestroy {

    lva: Lva;
    appointments: Appointment[];
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    events: any[];
    headerConfig: any;
    hugo: boolean;
    scrollTime: any;
    height: any;

    constructor(
        private eventManager: JhiEventManager,
        private alertService: JhiAlertService,
        private lvaService: LvaService,
        private route: ActivatedRoute,
        private appointmentService: AppointmentService
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLvas();
        this.headerConfig = {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        };
        this.hugo = false;
        this.scrollTime = '08:00:00';
        this.height = 800;

        this.appointmentService.query2(3151, 2).subscribe(
            (res: ResponseWrapper) => {
                this.events = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );


    }

    load(id) {
        this.lvaService.find(id).subscribe((lva) => {
            this.lva = lva;
            this.appointments = lva.appointments;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLvas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lvaListModification',
            (response) => this.load(this.lva.id)
        );
    }
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
