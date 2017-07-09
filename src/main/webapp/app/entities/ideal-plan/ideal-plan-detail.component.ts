import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { IdealPlan } from './ideal-plan.model';
import { IdealPlanService } from './ideal-plan.service';

@Component({
    selector: 'jhi-ideal-plan-detail',
    templateUrl: './ideal-plan-detail.component.html'
})
export class IdealPlanDetailComponent implements OnInit, OnDestroy {

    idealPlan: IdealPlan;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private idealPlanService: IdealPlanService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIdealPlans();
    }

    load(id) {
        this.idealPlanService.find(id).subscribe((idealPlan) => {
            this.idealPlan = idealPlan;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIdealPlans() {
        this.eventSubscriber = this.eventManager.subscribe(
            'idealPlanListModification',
            (response) => this.load(this.idealPlan.id)
        );
    }
}
