import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CollisionLevelFive } from './collision-level-five.model';
import { CollisionLevelFiveService } from './collision-level-five.service';

@Component({
    selector: 'jhi-collision-level-five-detail',
    templateUrl: './collision-level-five-detail.component.html'
})
export class CollisionLevelFiveDetailComponent implements OnInit, OnDestroy {

    collisionLevelFive: CollisionLevelFive;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private collisionLevelFiveService: CollisionLevelFiveService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCollisionLevelFives();
    }

    load(id) {
        this.collisionLevelFiveService.find(id).subscribe((collisionLevelFive) => {
            this.collisionLevelFive = collisionLevelFive;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCollisionLevelFives() {
        this.eventSubscriber = this.eventManager.subscribe(
            'collisionLevelFiveListModification',
            (response) => this.load(this.collisionLevelFive.id)
        );
    }
}
