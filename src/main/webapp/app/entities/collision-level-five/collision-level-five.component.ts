import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { CollisionLevelFive } from './collision-level-five.model';
import { CollisionLevelFiveService } from './collision-level-five.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-collision-level-five',
    templateUrl: './collision-level-five.component.html'
})
export class CollisionLevelFiveComponent implements OnInit, OnDestroy {
collisionLevelFives: CollisionLevelFive[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private collisionLevelFiveService: CollisionLevelFiveService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.collisionLevelFiveService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.collisionLevelFives = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.collisionLevelFiveService.query().subscribe(
            (res: ResponseWrapper) => {
                this.collisionLevelFives = res.json;
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
        this.registerChangeInCollisionLevelFives();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CollisionLevelFive) {
        return item.id;
    }
    registerChangeInCollisionLevelFives() {
        this.eventSubscriber = this.eventManager.subscribe('collisionLevelFiveListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
