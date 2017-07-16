import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CollisionLevelFour } from './collision-level-four.model';
import { CollisionLevelFourService } from './collision-level-four.service';

@Component({
    selector: 'jhi-collision-level-four-detail',
    templateUrl: './collision-level-four-detail.component.html'
})
export class CollisionLevelFourDetailComponent implements OnInit, OnDestroy {

    collisionLevelFour: CollisionLevelFour;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private collisionLevelFourService: CollisionLevelFourService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCollisionLevelFours();
    }

    load(id) {
        this.collisionLevelFourService.find(id).subscribe((collisionLevelFour) => {
            this.collisionLevelFour = collisionLevelFour;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCollisionLevelFours() {
        this.eventSubscriber = this.eventManager.subscribe(
            'collisionLevelFourListModification',
            (response) => this.load(this.collisionLevelFour.id)
        );
    }
}
