import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CollisionSummaryLva } from './collision-summary-lva.model';
import { CollisionSummaryLvaService } from './collision-summary-lva.service';

@Component({
    selector: 'jhi-collision-summary-lva-detail',
    templateUrl: './collision-summary-lva-detail.component.html'
})
export class CollisionSummaryLvaDetailComponent implements OnInit, OnDestroy {

    collisionSummaryLva: CollisionSummaryLva;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private collisionSummaryLvaService: CollisionSummaryLvaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCollisionSummaryLvas();
    }

    load(id) {
        this.collisionSummaryLvaService.find(id).subscribe((collisionSummaryLva) => {
            this.collisionSummaryLva = collisionSummaryLva;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCollisionSummaryLvas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'collisionSummaryLvaListModification',
            (response) => this.load(this.collisionSummaryLva.id)
        );
    }
}
