import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { CollisionLevelThree } from './collision-level-three.model';
import { CollisionLevelThreeService } from './collision-level-three.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-collision-level-three',
    templateUrl: './collision-level-three.component.html'
})
export class CollisionLevelThreeComponent implements OnInit, OnDestroy {
collisionLevelThrees: CollisionLevelThree[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private collisionLevelThreeService: CollisionLevelThreeService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.collisionLevelThreeService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.collisionLevelThrees = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.collisionLevelThreeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.collisionLevelThrees = res.json;
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
        this.registerChangeInCollisionLevelThrees();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CollisionLevelThree) {
        return item.id;
    }
    registerChangeInCollisionLevelThrees() {
        this.eventSubscriber = this.eventManager.subscribe('collisionLevelThreeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
