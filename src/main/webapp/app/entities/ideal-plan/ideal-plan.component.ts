import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { IdealPlan } from './ideal-plan.model';
import { IdealPlanService } from './ideal-plan.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-ideal-plan',
    templateUrl: './ideal-plan.component.html'
})
export class IdealPlanComponent implements OnInit, OnDestroy {
idealPlans: IdealPlan[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private idealPlanService: IdealPlanService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.idealPlanService.query().subscribe(
            (res: ResponseWrapper) => {
                this.idealPlans = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInIdealPlans();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IdealPlan) {
        return item.id;
    }
    registerChangeInIdealPlans() {
        this.eventSubscriber = this.eventManager.subscribe('idealPlanListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
