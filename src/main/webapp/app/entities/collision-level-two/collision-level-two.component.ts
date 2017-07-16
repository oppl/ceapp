import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { CollisionLevelTwo } from './collision-level-two.model';
import { CollisionLevelTwoService } from './collision-level-two.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-collision-level-two',
    templateUrl: './collision-level-two.component.html'
})
export class CollisionLevelTwoComponent implements OnInit, OnDestroy {
collisionLevelTwos: CollisionLevelTwo[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private collisionLevelTwoService: CollisionLevelTwoService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.collisionLevelTwoService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.collisionLevelTwos = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.collisionLevelTwoService.query().subscribe(
            (res: ResponseWrapper) => {
                this.collisionLevelTwos = res.json;
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
        this.registerChangeInCollisionLevelTwos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CollisionLevelTwo) {
        return item.id;
    }
    registerChangeInCollisionLevelTwos() {
        this.eventSubscriber = this.eventManager.subscribe('collisionLevelTwoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
