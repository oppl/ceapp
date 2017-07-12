import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { IdealPlanEntries } from './ideal-plan-entries.model';
import { IdealPlanEntriesService } from './ideal-plan-entries.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-ideal-plan-entries',
    templateUrl: './ideal-plan-entries.component.html'
})
export class IdealPlanEntriesComponent implements OnInit, OnDestroy {
idealPlanEntries: IdealPlanEntries[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private idealPlanEntriesService: IdealPlanEntriesService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.idealPlanEntriesService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.idealPlanEntries = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.idealPlanEntriesService.query().subscribe(
            (res: ResponseWrapper) => {
                this.idealPlanEntries = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInIdealPlanEntries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IdealPlanEntries) {
        return item.id;
    }
    registerChangeInIdealPlanEntries() {
        this.eventSubscriber = this.eventManager.subscribe('idealPlanEntriesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
