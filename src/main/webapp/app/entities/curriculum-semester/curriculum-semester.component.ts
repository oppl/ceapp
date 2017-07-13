import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { CurriculumSemester } from './curriculum-semester.model';
import { CurriculumSemesterService } from './curriculum-semester.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-curriculum-semester',
    templateUrl: './curriculum-semester.component.html'
})
export class CurriculumSemesterComponent implements OnInit, OnDestroy {
curriculumSemesters: CurriculumSemester[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private curriculumSemesterService: CurriculumSemesterService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.curriculumSemesterService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.curriculumSemesters = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.curriculumSemesterService.query().subscribe(
            (res: ResponseWrapper) => {
                this.curriculumSemesters = res.json;
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
        this.registerChangeInCurriculumSemesters();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CurriculumSemester) {
        return item.id;
    }
    registerChangeInCurriculumSemesters() {
        this.eventSubscriber = this.eventManager.subscribe('curriculumSemesterListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
