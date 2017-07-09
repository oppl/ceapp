import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { IdealPlanEntries } from './ideal-plan-entries.model';
import { IdealPlanEntriesService } from './ideal-plan-entries.service';

@Component({
    selector: 'jhi-ideal-plan-entries-detail',
    templateUrl: './ideal-plan-entries-detail.component.html'
})
export class IdealPlanEntriesDetailComponent implements OnInit, OnDestroy {

    idealPlanEntries: IdealPlanEntries;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private idealPlanEntriesService: IdealPlanEntriesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIdealPlanEntries();
    }

    load(id) {
        this.idealPlanEntriesService.find(id).subscribe((idealPlanEntries) => {
            this.idealPlanEntries = idealPlanEntries;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIdealPlanEntries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'idealPlanEntriesListModification',
            (response) => this.load(this.idealPlanEntries.id)
        );
    }
}
