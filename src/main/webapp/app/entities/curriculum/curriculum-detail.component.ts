import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Curriculum } from './curriculum.model';
import { CurriculumService } from './curriculum.service';

@Component({
    selector: 'jhi-curriculum-detail',
    templateUrl: './curriculum-detail.component.html'
})
export class CurriculumDetailComponent implements OnInit, OnDestroy {

    curriculum: Curriculum;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private curriculumService: CurriculumService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCurricula();
    }

    load(id) {
        this.curriculumService.find(id).subscribe((curriculum) => {
            this.curriculum = curriculum;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCurricula() {
        this.eventSubscriber = this.eventManager.subscribe(
            'curriculumListModification',
            (response) => this.load(this.curriculum.id)
        );
        this.eventSubscriber = this.eventManager.subscribe(
            'curriculumSemesterListModification',
            (response) => this.load(this.curriculum.id)
        );
    }
}
