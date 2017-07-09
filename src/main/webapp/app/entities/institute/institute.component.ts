import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Institute } from './institute.model';
import { InstituteService } from './institute.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-institute',
    templateUrl: './institute.component.html'
})
export class InstituteComponent implements OnInit, OnDestroy {
institutes: Institute[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private instituteService: InstituteService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.instituteService.query().subscribe(
            (res: ResponseWrapper) => {
                this.institutes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInInstitutes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Institute) {
        return item.id;
    }
    registerChangeInInstitutes() {
        this.eventSubscriber = this.eventManager.subscribe('instituteListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
