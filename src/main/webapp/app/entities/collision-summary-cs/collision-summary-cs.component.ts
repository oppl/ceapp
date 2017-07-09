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

    constructor(
        private collisionSummaryCSService: CollisionSummaryCSService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.collisionSummaryCSService.query().subscribe(
            (res: ResponseWrapper) => {
                this.collisionSummaryCS = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
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
