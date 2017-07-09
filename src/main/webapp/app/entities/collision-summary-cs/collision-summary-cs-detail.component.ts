import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CollisionSummaryCS } from './collision-summary-cs.model';
import { CollisionSummaryCSService } from './collision-summary-cs.service';

@Component({
    selector: 'jhi-collision-summary-cs-detail',
    templateUrl: './collision-summary-cs-detail.component.html'
})
export class CollisionSummaryCSDetailComponent implements OnInit, OnDestroy {

    collisionSummaryCS: CollisionSummaryCS;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private collisionSummaryCSService: CollisionSummaryCSService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCollisionSummaryCS();
    }

    load(id) {
        this.collisionSummaryCSService.find(id).subscribe((collisionSummaryCS) => {
            this.collisionSummaryCS = collisionSummaryCS;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCollisionSummaryCS() {
        this.eventSubscriber = this.eventManager.subscribe(
            'collisionSummaryCSListModification',
            (response) => this.load(this.collisionSummaryCS.id)
        );
    }
}
