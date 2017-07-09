import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Curriculum } from './curriculum.model';
import { CurriculumService } from './curriculum.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-curriculum',
    templateUrl: './curriculum.component.html'
})
export class CurriculumComponent implements OnInit, OnDestroy {
curricula: Curriculum[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private curriculumService: CurriculumService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.curriculumService.query().subscribe(
            (res: ResponseWrapper) => {
                this.curricula = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCurricula();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Curriculum) {
        return item.id;
    }
    registerChangeInCurricula() {
        this.eventSubscriber = this.eventManager.subscribe('curriculumListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
