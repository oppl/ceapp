import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Lva } from './lva.model';
import { LvaService } from './lva.service';
import {Appointment} from '../appointment/appointment.model';

@Component({
    selector: 'jhi-lva-detail',
    templateUrl: './lva-detail.component.html'
})
export class LvaDetailComponent implements OnInit, OnDestroy {

    lva: Lva;
    appointments: Appointment[];
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private lvaService: LvaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLvas();
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
}
