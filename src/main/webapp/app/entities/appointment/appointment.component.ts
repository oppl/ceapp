import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Appointment } from './appointment.model';
import { AppointmentService } from './appointment.service';
import { CurriculumSemester } from '../curriculum-semester/curriculum-semester.model';
import { CurriculumSemesterService } from '../curriculum-semester/curriculum-semester.service';
import { IdealPlan} from '../ideal-plan/ideal-plan.model';
import { Principal, ResponseWrapper } from '../../shared';
import {IdealPlanService} from '../ideal-plan/ideal-plan.service';

@Component({
    selector: 'jhi-appointment',
    templateUrl: './appointment.component.html'
})
export class AppointmentComponent implements OnInit, OnDestroy {
    appointments: Appointment[];
    idealPlans: IdealPlan[];
    idealPlanSelected: any;
    curriculumSemesters: CurriculumSemester[];
    curriculumSelected: any;
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    event: any[];
    events: any[];
    headerConfig: any;
    hiddenDays: any[];
    locale: string;
    columnFormat: string;
    defaultView: string;
    defaultDate: string;
    scrollTime: string;
    minTime: string;
    maxTime: string;
    slotLabelInterval: string;
    slotLabelFormat: string;
    timeFormat: string;
    weekNumbersWithinDays: boolean;
    contentHeight: any;
    titleFormat: any;
    eventColor: any;
    semesters: any[];
    semester: any;
    selectedSemester: any;
    curriculumSemester: any;
    curriculum: any;
    constructor(
        private appointmentService: AppointmentService,
        private idealPlanService: IdealPlanService,
        private curriculumSemesterService: CurriculumSemesterService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
        this.headerConfig = {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        };
        this.hiddenDays =  [ 0 ];
        this.locale = 'de';
        this.columnFormat = 'D ddd';
        this.defaultView = 'agendaWeek';
        this.defaultDate = (new Date).getFullYear() + '-10-03';
        this.scrollTime = '08:00:00';
        this.minTime = '08:00:00';
        this.maxTime = '22:00:00';
        this.slotLabelInterval = '00:15:00';
        this.weekNumbersWithinDays = true;
        this.contentHeight = 800;
        this.timeFormat = 'hh:mm';
        this.titleFormat = 'D MM YYYY';
        this.semesters = [
            { semester: '1 Semester', value: 1 },
            { semester: '2 Semester', value: 2 },
            { semester: '3 Semester', value: 3 },
            { semester: '4 Semester', value: 4 },
            { semester: '5 Semester', value: 5 },
            { semester: '6 Semester', value: 6 }];

        this.idealPlanService.query().subscribe(
            (res: ResponseWrapper) => {
                this.idealPlans = res.json;
                this.idealPlanSelected = ' ';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );

        this.curriculumSemesterService.query().subscribe(
            (res: ResponseWrapper) => {
                this.curriculumSemesters = res.json;
                this.curriculumSelected = ' ';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.selectedSemester = ' ';
    }

    loadAll() {
        if (this.currentSearch) {
            this.appointmentService.search({
                query: this.currentSearch,
            }).subscribe(
                (res: ResponseWrapper) => this.appointments = res.json,
                (res: ResponseWrapper) => this.onError(res.json)
            );
            return;
        }
        this.appointmentService.query().subscribe(
            (res: ResponseWrapper) => {
                this.appointments = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.curriculumSemesterService.query().subscribe(
            (res: ResponseWrapper) => {
                this.curriculumSemesters = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query, semester, curriculum) {
        if (!query) {
            return this.clear();
        }
        this.appointmentService.query2(query, semester, curriculum).subscribe(
            (res: ResponseWrapper) => {
                this.events = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAppointments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Appointment) {
        return item.id;
    }
    registerChangeInAppointments() {
        this.eventSubscriber = this.eventManager.subscribe('appointmentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
