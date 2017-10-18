import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { CollisionLevelOne } from './collision-level-one.model';
import { CollisionLevelOneService } from './collision-level-one.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import {CollisionLevelTwoService} from '../collision-level-two/collision-level-two.service';
import {CollisionLevelTwo} from '../collision-level-two/collision-level-two.model';
import {CollisionLevelThreeService} from '../collision-level-three/collision-level-three.service';
import {CollisionLevelThree} from '../collision-level-three/collision-level-three.model';
import {CollisionLevelFour} from '../collision-level-four/collision-level-four.model';
import {CollisionLevelFive} from '../collision-level-five/collision-level-five.model';
import {CollisionLevelFourService} from '../collision-level-four/collision-level-four.service';
import {CollisionLevelFiveService} from '../collision-level-five/collision-level-five.service';

@Component({
    selector: 'jhi-collision-level-one',
    templateUrl: './collision-level-one.component.html'
})
export class CollisionLevelOneComponent implements OnInit, OnDestroy {
collisionLevelOnes: CollisionLevelOne[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    levelTwo: CollisionLevelTwo[];
    levelThree: CollisionLevelThree[];
    levelFour: CollisionLevelFour[];
    levelFive: CollisionLevelFive[];
    csId: number;
    ipId: number;

    constructor(
        private collisionLevelOneService: CollisionLevelOneService,
        private collisionLevelTwoService: CollisionLevelTwoService,
        private collisionLevelThreeService: CollisionLevelThreeService,
        private collisionLevelFourService: CollisionLevelFourService,
        private collisionLevelFiveService: CollisionLevelFiveService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : ''
    }

    loadAll() {
        console.log('testtest');
        console.log(this.csId);
        console.log(this.ipId);
        // if (this.csId && this.ipId) {

            this.collisionLevelOneService.find2(this.csId, this.ipId).subscribe(
                    (res: ResponseWrapper) => this.collisionLevelOnes = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       // }

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
        this.activatedRoute.params.subscribe((params) => {
            this.csId = +params['cs']; });
        this.activatedRoute.params.subscribe((params) => {
            this.ipId = +params['ip']; });
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCollisionLevelOnes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CollisionLevelOne) {
        return item.id;
    }
    registerChangeInCollisionLevelOnes() {
        this.eventSubscriber = this.eventManager.subscribe('collisionLevelOneListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    onRowExpand(cc) {
        console.log(cc);
        console.log(cc.data.id)
        this.collisionLevelTwoService.find(cc.data.id).subscribe(
            (res: ResponseWrapper) => this.levelTwo = res.json,
            (res: ResponseWrapper) => this.onError(res.json)
        );
        console.log(this.levelTwo);

    }

    onRowExpand2(cc) {
        console.log(cc);
        console.log(cc.data.id)
        this.collisionLevelThreeService.find(cc.data.id).subscribe(
            (res: ResponseWrapper) => this.levelThree = res.json,
            (res: ResponseWrapper) => this.onError(res.json)
        );

    }

    onRowExpand3(cc) {
        console.log(cc);
        console.log(cc.data.id)
        this.collisionLevelFourService.find(cc.data.id).subscribe(
            (res: ResponseWrapper) => this.levelFour = res.json,
            (res: ResponseWrapper) => this.onError(res.json)
        );

    }

    onRowExpand4(cc) {
        console.log(cc);
        console.log(cc.data.id)
        this.collisionLevelFiveService.find(cc.data.id).subscribe(
            (res: ResponseWrapper) => this.levelFive = res.json,
            (res: ResponseWrapper) => this.onError(res.json)
        );

    }

}
