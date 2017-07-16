import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CollisionLevelTwo } from './collision-level-two.model';
import { CollisionLevelTwoService } from './collision-level-two.service';

@Component({
    selector: 'jhi-collision-level-two-detail',
    templateUrl: './collision-level-two-detail.component.html'
})
export class CollisionLevelTwoDetailComponent implements OnInit, OnDestroy {

    collisionLevelTwo: CollisionLevelTwo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private collisionLevelTwoService: CollisionLevelTwoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCollisionLevelTwos();
    }

    load(id) {
        this.collisionLevelTwoService.find(id).subscribe((collisionLevelTwo) => {
            this.collisionLevelTwo = collisionLevelTwo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCollisionLevelTwos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'collisionLevelTwoListModification',
            (response) => this.load(this.collisionLevelTwo.id)
        );
    }
}
