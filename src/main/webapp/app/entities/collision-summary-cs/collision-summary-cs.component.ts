import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { CollisionSummaryCS } from './collision-summary-cs.model';
import { CollisionSummaryCSService } from './collision-summary-cs.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-collision-summary-cs',
    templateUrl: './collision-summary-cs.component.html'
})
export class CollisionSummaryCSComponent implements OnInit, OnDestroy {
collisionSummaryCS: CollisionSummaryCS[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private collisionSummaryCSService: CollisionSummaryCSService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.collisionSummaryCSService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.collisionSummaryCS = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.collisionSummaryCSService.query().subscribe(
            (res: ResponseWrapper) => {
                this.collisionSummaryCS = res.json;
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
        this.registerChangeInCollisionSummaryCS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CollisionSummaryCS) {
        return item.id;
    }
    registerChangeInCollisionSummaryCS() {
        this.eventSubscriber = this.eventManager.subscribe('collisionSummaryCSListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
