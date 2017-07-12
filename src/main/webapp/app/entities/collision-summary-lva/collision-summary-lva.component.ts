import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { CollisionSummaryLva } from './collision-summary-lva.model';
import { CollisionSummaryLvaService } from './collision-summary-lva.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-collision-summary-lva',
    templateUrl: './collision-summary-lva.component.html'
})
export class CollisionSummaryLvaComponent implements OnInit, OnDestroy {
collisionSummaryLvas: CollisionSummaryLva[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private collisionSummaryLvaService: CollisionSummaryLvaService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.collisionSummaryLvaService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.collisionSummaryLvas = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.collisionSummaryLvaService.query().subscribe(
            (res: ResponseWrapper) => {
                this.collisionSummaryLvas = res.json;
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
        this.registerChangeInCollisionSummaryLvas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CollisionSummaryLva) {
        return item.id;
    }
    registerChangeInCollisionSummaryLvas() {
        this.eventSubscriber = this.eventManager.subscribe('collisionSummaryLvaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
