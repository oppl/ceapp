import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CurriculumSubject } from './curriculum-subject.model';
import { CurriculumSubjectService } from './curriculum-subject.service';

@Component({
    selector: 'jhi-curriculum-subject-detail',
    templateUrl: './curriculum-subject-detail.component.html'
})
export class CurriculumSubjectDetailComponent implements OnInit, OnDestroy {

    curriculumSubject: CurriculumSubject;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private curriculumSubjectService: CurriculumSubjectService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCurriculumSubjects();
    }

    load(id) {
        this.curriculumSubjectService.find(id).subscribe((curriculumSubject) => {
            this.curriculumSubject = curriculumSubject;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCurriculumSubjects() {
        this.eventSubscriber = this.eventManager.subscribe(
            'curriculumSubjectListModification',
            (response) => this.load(this.curriculumSubject.id)
        );
    }
}
