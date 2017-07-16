import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CollisionLevelThree } from './collision-level-three.model';
import { CollisionLevelThreeService } from './collision-level-three.service';

@Component({
    selector: 'jhi-collision-level-three-detail',
    templateUrl: './collision-level-three-detail.component.html'
})
export class CollisionLevelThreeDetailComponent implements OnInit, OnDestroy {

    collisionLevelThree: CollisionLevelThree;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private collisionLevelThreeService: CollisionLevelThreeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCollisionLevelThrees();
    }

    load(id) {
        this.collisionLevelThreeService.find(id).subscribe((collisionLevelThree) => {
            this.collisionLevelThree = collisionLevelThree;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCollisionLevelThrees() {
        this.eventSubscriber = this.eventManager.subscribe(
            'collisionLevelThreeListModification',
            (response) => this.load(this.collisionLevelThree.id)
        );
    }
}
