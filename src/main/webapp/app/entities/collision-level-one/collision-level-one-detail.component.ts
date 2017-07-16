import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CollisionLevelOne } from './collision-level-one.model';
import { CollisionLevelOneService } from './collision-level-one.service';

@Component({
    selector: 'jhi-collision-level-one-detail',
    templateUrl: './collision-level-one-detail.component.html'
})
export class CollisionLevelOneDetailComponent implements OnInit, OnDestroy {

    collisionLevelOne: CollisionLevelOne;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private collisionLevelOneService: CollisionLevelOneService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCollisionLevelOnes();
    }

    load(id) {
        this.collisionLevelOneService.find(id).subscribe((collisionLevelOne) => {
            this.collisionLevelOne = collisionLevelOne;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCollisionLevelOnes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'collisionLevelOneListModification',
            (response) => this.load(this.collisionLevelOne.id)
        );
    }
}
