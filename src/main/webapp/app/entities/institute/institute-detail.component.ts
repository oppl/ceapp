import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Institute } from './institute.model';
import { InstituteService } from './institute.service';

@Component({
    selector: 'jhi-institute-detail',
    templateUrl: './institute-detail.component.html'
})
export class InstituteDetailComponent implements OnInit, OnDestroy {

    institute: Institute;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private instituteService: InstituteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInstitutes();
    }

    load(id) {
        this.instituteService.find(id).subscribe((institute) => {
            this.institute = institute;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInstitutes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'instituteListModification',
            (response) => this.load(this.institute.id)
        );
    }
}
