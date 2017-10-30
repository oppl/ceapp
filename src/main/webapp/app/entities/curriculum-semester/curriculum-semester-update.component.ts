import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CurriculumSemester } from './curriculum-semester.model';
import { CurriculumSemesterService } from './curriculum-semester.service';

@Component({
    selector: 'jhi-curriculum-semester-update',
    templateUrl: './curriculum-semester-update.component.html'
})
export class CurriculumSemesterUpdateComponent implements OnInit, OnDestroy {

    curriculumSemester: CurriculumSemester;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private curriculumSemesterService: CurriculumSemesterService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCurriculumSemesters();
    }

    load(id) {
        this.curriculumSemesterService.updateSemester(id).subscribe((curriculumSemester) => {
            this.curriculumSemester = curriculumSemester;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCurriculumSemesters() {
        this.eventSubscriber = this.eventManager.subscribe(
            'curriculumSemesterListModification',
            (response) => this.load(this.curriculumSemester.id)
        );
    }
}
