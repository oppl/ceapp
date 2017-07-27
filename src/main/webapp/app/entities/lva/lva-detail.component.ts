import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Lva } from './lva.model';
import { LvaService } from './lva.service';
import {Appointment} from '../appointment/appointment.model';

@Component({
    selector: 'jhi-lva-detail',
    templateUrl: './lva-detail.component.html'
})
export class LvaDetailComponent implements OnInit, OnDestroy {

    lva: Lva;
    appointments: Appointment[];
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    events: any[];
    headerConfig: any;
    hugo: boolean;
    scrollTime: any;
    height: any;

    constructor(
        private eventManager: JhiEventManager,
        private lvaService: LvaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLvas();
        this.headerConfig = {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        };
        this.hugo = false;
        this.scrollTime = '08:00:00';
        this.height = 800;
        this.events = [ {
            "id" : 1664,
            "startDateTime" : "2017-06-21T10:15:00+02:00",
            "endDateTime" : "2017-06-21T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1424,
            "title" : "259000",
            "start" : "2017-06-21T10:15:00+02:00",
            "end" : "2017-06-21T11:45:00+02:00"
        }, {
            "id" : 1665,
            "startDateTime" : "2017-04-26T10:15:00+02:00",
            "endDateTime" : "2017-04-26T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1424,
            "title" : "259000",
            "start" : "2017-04-26T10:15:00+02:00",
            "end" : "2017-04-26T11:45:00+02:00"
        }, {
            "id" : 1666,
            "startDateTime" : "2017-06-07T10:15:00+02:00",
            "endDateTime" : "2017-06-07T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1424,
            "title" : "259000",
            "start" : "2017-06-07T10:15:00+02:00",
            "end" : "2017-06-07T11:45:00+02:00"
        }, {
            "id" : 1667,
            "startDateTime" : "2017-03-08T09:15:00+01:00",
            "endDateTime" : "2017-03-08T10:45:00+01:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "Beginn der Vorlesung",
            "lvaId" : 1424,
            "title" : "259000",
            "start" : "2017-03-08T09:15:00+01:00",
            "end" : "2017-03-08T10:45:00+01:00"
        }, {
            "id" : 1668,
            "startDateTime" : "2017-03-15T09:15:00+01:00",
            "endDateTime" : "2017-03-15T10:45:00+01:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1424,
            "title" : "259000",
            "start" : "2017-03-15T09:15:00+01:00",
            "end" : "2017-03-15T10:45:00+01:00"
        }, {
            "id" : 1669,
            "startDateTime" : "2017-05-03T10:15:00+02:00",
            "endDateTime" : "2017-05-03T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1424,
            "title" : "259000",
            "start" : "2017-05-03T10:15:00+02:00",
            "end" : "2017-05-03T11:45:00+02:00"
        }, {
            "id" : 1654,
            "startDateTime" : "2017-06-28T10:15:00+02:00",
            "endDateTime" : "2017-06-28T11:45:00+02:00",
            "isExam" : true,
            "room" : "HS 16",
            "theme" : "Klausur",
            "lvaId" : 1424,
            "title" : "259000",
            "start" : "2017-06-28T10:15:00+02:00",
            "end" : "2017-06-28T11:45:00+02:00"
        }, {
            "id" : 1655,
            "startDateTime" : "2017-03-29T10:15:00+02:00",
            "endDateTime" : "2017-03-29T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1424,
            "title" : "259000",
            "start" : "2017-03-29T10:15:00+02:00",
            "end" : "2017-03-29T11:45:00+02:00"
        }, {
            "id" : 1656,
            "startDateTime" : "2017-05-17T10:15:00+02:00",
            "endDateTime" : "2017-05-17T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1424,
            "title" : "259000",
            "start" : "2017-05-17T10:15:00+02:00",
            "end" : "2017-05-17T11:45:00+02:00"
        }, {
            "id" : 1657,
            "startDateTime" : "2017-04-05T10:15:00+02:00",
            "endDateTime" : "2017-04-05T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1424,
            "title" : "259000",
            "start" : "2017-04-05T10:15:00+02:00",
            "end" : "2017-04-05T11:45:00+02:00"
        }, {
            "id" : 1658,
            "startDateTime" : "2017-06-28T10:15:00+02:00",
            "endDateTime" : "2017-06-28T11:45:00+02:00",
            "isExam" : true,
            "room" : "HS 18",
            "theme" : "Klausur",
            "lvaId" : 1424,
            "title" : "259000",
            "start" : "2017-06-28T10:15:00+02:00",
            "end" : "2017-06-28T11:45:00+02:00"
        }, {
            "id" : 1659,
            "startDateTime" : "2017-05-31T10:15:00+02:00",
            "endDateTime" : "2017-05-31T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1424,
            "title" : "259000",
            "start" : "2017-05-31T10:15:00+02:00",
            "end" : "2017-05-31T11:45:00+02:00"
        }, {
            "id" : 1660,
            "startDateTime" : "2017-03-22T09:15:00+01:00",
            "endDateTime" : "2017-03-22T10:45:00+01:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1424,
            "title" : "259000",
            "start" : "2017-03-22T09:15:00+01:00",
            "end" : "2017-03-22T10:45:00+01:00"
        }, {
            "id" : 1661,
            "startDateTime" : "2017-05-10T10:15:00+02:00",
            "endDateTime" : "2017-05-10T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1424,
            "title" : "259000",
            "start" : "2017-05-10T10:15:00+02:00",
            "end" : "2017-05-10T11:45:00+02:00"
        }, {
            "id" : 1662,
            "startDateTime" : "2017-06-14T10:15:00+02:00",
            "endDateTime" : "2017-06-14T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1424,
            "title" : "259000",
            "start" : "2017-06-14T10:15:00+02:00",
            "end" : "2017-06-14T11:45:00+02:00"
        }, {
            "id" : 1663,
            "startDateTime" : "2017-05-24T10:15:00+02:00",
            "endDateTime" : "2017-05-24T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1424,
            "title" : "259000",
            "start" : "2017-05-24T10:15:00+02:00",
            "end" : "2017-05-24T11:45:00+02:00"
        }, {
            "id" : 1670,
            "startDateTime" : "2017-06-07T15:30:00+02:00",
            "endDateTime" : "2017-06-07T17:00:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1425,
            "title" : "259006",
            "start" : "2017-06-07T15:30:00+02:00",
            "end" : "2017-06-07T17:00:00+02:00"
        }, {
            "id" : 1671,
            "startDateTime" : "2017-03-29T15:30:00+02:00",
            "endDateTime" : "2017-03-29T17:00:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1425,
            "title" : "259006",
            "start" : "2017-03-29T15:30:00+02:00",
            "end" : "2017-03-29T17:00:00+02:00"
        }, {
            "id" : 1672,
            "startDateTime" : "2017-05-24T15:30:00+02:00",
            "endDateTime" : "2017-05-24T17:00:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1425,
            "title" : "259006",
            "start" : "2017-05-24T15:30:00+02:00",
            "end" : "2017-05-24T17:00:00+02:00"
        }, {
            "id" : 1673,
            "startDateTime" : "2017-04-26T15:30:00+02:00",
            "endDateTime" : "2017-04-26T17:00:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1425,
            "title" : "259006",
            "start" : "2017-04-26T15:30:00+02:00",
            "end" : "2017-04-26T17:00:00+02:00"
        }, {
            "id" : 1674,
            "startDateTime" : "2017-03-15T14:30:00+01:00",
            "endDateTime" : "2017-03-15T16:00:00+01:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1425,
            "title" : "259006",
            "start" : "2017-03-15T14:30:00+01:00",
            "end" : "2017-03-15T16:00:00+01:00"
        }, {
            "id" : 1675,
            "startDateTime" : "2017-04-05T15:30:00+02:00",
            "endDateTime" : "2017-04-05T17:00:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1425,
            "title" : "259006",
            "start" : "2017-04-05T15:30:00+02:00",
            "end" : "2017-04-05T17:00:00+02:00"
        }, {
            "id" : 1676,
            "startDateTime" : "2017-03-08T14:30:00+01:00",
            "endDateTime" : "2017-03-08T16:00:00+01:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "Vorbesprechung - ANWESENHEITSPFLICHT!",
            "lvaId" : 1425,
            "title" : "259006",
            "start" : "2017-03-08T14:30:00+01:00",
            "end" : "2017-03-08T16:00:00+01:00"
        }, {
            "id" : 1677,
            "startDateTime" : "2017-05-10T15:30:00+02:00",
            "endDateTime" : "2017-05-10T17:00:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1425,
            "title" : "259006",
            "start" : "2017-05-10T15:30:00+02:00",
            "end" : "2017-05-10T17:00:00+02:00"
        }, {
            "id" : 1678,
            "startDateTime" : "2017-06-14T15:30:00+02:00",
            "endDateTime" : "2017-06-14T17:00:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1425,
            "title" : "259006",
            "start" : "2017-06-14T15:30:00+02:00",
            "end" : "2017-06-14T17:00:00+02:00"
        }, {
            "id" : 1679,
            "startDateTime" : "2017-05-17T15:30:00+02:00",
            "endDateTime" : "2017-05-17T17:00:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1425,
            "title" : "259006",
            "start" : "2017-05-17T15:30:00+02:00",
            "end" : "2017-05-17T17:00:00+02:00"
        }, {
            "id" : 1680,
            "startDateTime" : "2017-05-31T15:30:00+02:00",
            "endDateTime" : "2017-05-31T17:00:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1425,
            "title" : "259006",
            "start" : "2017-05-31T15:30:00+02:00",
            "end" : "2017-05-31T17:00:00+02:00"
        }, {
            "id" : 1681,
            "startDateTime" : "2017-03-22T14:30:00+01:00",
            "endDateTime" : "2017-03-22T16:00:00+01:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1425,
            "title" : "259006",
            "start" : "2017-03-22T14:30:00+01:00",
            "end" : "2017-03-22T16:00:00+01:00"
        }, {
            "id" : 1682,
            "startDateTime" : "2017-05-03T15:30:00+02:00",
            "endDateTime" : "2017-05-03T17:00:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1425,
            "title" : "259006",
            "start" : "2017-05-03T15:30:00+02:00",
            "end" : "2017-05-03T17:00:00+02:00"
        }, {
            "id" : 1683,
            "startDateTime" : "2017-05-10T17:15:00+02:00",
            "endDateTime" : "2017-05-10T18:45:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1426,
            "title" : "259005",
            "start" : "2017-05-10T17:15:00+02:00",
            "end" : "2017-05-10T18:45:00+02:00"
        }, {
            "id" : 1684,
            "startDateTime" : "2017-05-17T17:15:00+02:00",
            "endDateTime" : "2017-05-17T18:45:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1426,
            "title" : "259005",
            "start" : "2017-05-17T17:15:00+02:00",
            "end" : "2017-05-17T18:45:00+02:00"
        }, {
            "id" : 1685,
            "startDateTime" : "2017-05-31T17:15:00+02:00",
            "endDateTime" : "2017-05-31T18:45:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1426,
            "title" : "259005",
            "start" : "2017-05-31T17:15:00+02:00",
            "end" : "2017-05-31T18:45:00+02:00"
        }, {
            "id" : 1686,
            "startDateTime" : "2017-04-05T17:15:00+02:00",
            "endDateTime" : "2017-04-05T18:45:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1426,
            "title" : "259005",
            "start" : "2017-04-05T17:15:00+02:00",
            "end" : "2017-04-05T18:45:00+02:00"
        }, {
            "id" : 1687,
            "startDateTime" : "2017-05-03T17:15:00+02:00",
            "endDateTime" : "2017-05-03T18:45:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1426,
            "title" : "259005",
            "start" : "2017-05-03T17:15:00+02:00",
            "end" : "2017-05-03T18:45:00+02:00"
        }, {
            "id" : 1688,
            "startDateTime" : "2017-03-15T16:15:00+01:00",
            "endDateTime" : "2017-03-15T17:45:00+01:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1426,
            "title" : "259005",
            "start" : "2017-03-15T16:15:00+01:00",
            "end" : "2017-03-15T17:45:00+01:00"
        }, {
            "id" : 1689,
            "startDateTime" : "2017-05-24T17:15:00+02:00",
            "endDateTime" : "2017-05-24T18:45:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1426,
            "title" : "259005",
            "start" : "2017-05-24T17:15:00+02:00",
            "end" : "2017-05-24T18:45:00+02:00"
        }, {
            "id" : 1690,
            "startDateTime" : "2017-04-26T17:15:00+02:00",
            "endDateTime" : "2017-04-26T18:45:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1426,
            "title" : "259005",
            "start" : "2017-04-26T17:15:00+02:00",
            "end" : "2017-04-26T18:45:00+02:00"
        }, {
            "id" : 1691,
            "startDateTime" : "2017-06-14T17:15:00+02:00",
            "endDateTime" : "2017-06-14T18:45:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1426,
            "title" : "259005",
            "start" : "2017-06-14T17:15:00+02:00",
            "end" : "2017-06-14T18:45:00+02:00"
        }, {
            "id" : 1692,
            "startDateTime" : "2017-03-08T16:15:00+01:00",
            "endDateTime" : "2017-03-08T17:45:00+01:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "Vorbesprechung - ANWESENHEITSPFLICHT!",
            "lvaId" : 1426,
            "title" : "259005",
            "start" : "2017-03-08T16:15:00+01:00",
            "end" : "2017-03-08T17:45:00+01:00"
        }, {
            "id" : 1693,
            "startDateTime" : "2017-06-07T17:15:00+02:00",
            "endDateTime" : "2017-06-07T18:45:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1426,
            "title" : "259005",
            "start" : "2017-06-07T17:15:00+02:00",
            "end" : "2017-06-07T18:45:00+02:00"
        }, {
            "id" : 1694,
            "startDateTime" : "2017-03-22T16:15:00+01:00",
            "endDateTime" : "2017-03-22T17:45:00+01:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1426,
            "title" : "259005",
            "start" : "2017-03-22T16:15:00+01:00",
            "end" : "2017-03-22T17:45:00+01:00"
        }, {
            "id" : 1695,
            "startDateTime" : "2017-03-29T17:15:00+02:00",
            "endDateTime" : "2017-03-29T18:45:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1426,
            "title" : "259005",
            "start" : "2017-03-29T17:15:00+02:00",
            "end" : "2017-03-29T18:45:00+02:00"
        }, {
            "id" : 1696,
            "startDateTime" : "2017-05-03T12:00:00+02:00",
            "endDateTime" : "2017-05-03T13:30:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1427,
            "title" : "259004",
            "start" : "2017-05-03T12:00:00+02:00",
            "end" : "2017-05-03T13:30:00+02:00"
        }, {
            "id" : 1697,
            "startDateTime" : "2017-05-24T12:00:00+02:00",
            "endDateTime" : "2017-05-24T13:30:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1427,
            "title" : "259004",
            "start" : "2017-05-24T12:00:00+02:00",
            "end" : "2017-05-24T13:30:00+02:00"
        }, {
            "id" : 1698,
            "startDateTime" : "2017-06-14T12:00:00+02:00",
            "endDateTime" : "2017-06-14T13:30:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1427,
            "title" : "259004",
            "start" : "2017-06-14T12:00:00+02:00",
            "end" : "2017-06-14T13:30:00+02:00"
        }, {
            "id" : 1699,
            "startDateTime" : "2017-03-08T11:00:00+01:00",
            "endDateTime" : "2017-03-08T12:30:00+01:00",
            "isExam" : false,
            "room" : "HS 4",
            "theme" : "Vorbesprechung - ANWESENHEITSPFLICHT!",
            "lvaId" : 1427,
            "title" : "259004",
            "start" : "2017-03-08T11:00:00+01:00",
            "end" : "2017-03-08T12:30:00+01:00"
        }, {
            "id" : 1700,
            "startDateTime" : "2017-03-15T11:00:00+01:00",
            "endDateTime" : "2017-03-15T12:30:00+01:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1427,
            "title" : "259004",
            "start" : "2017-03-15T11:00:00+01:00",
            "end" : "2017-03-15T12:30:00+01:00"
        }, {
            "id" : 1701,
            "startDateTime" : "2017-03-22T11:00:00+01:00",
            "endDateTime" : "2017-03-22T12:30:00+01:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1427,
            "title" : "259004",
            "start" : "2017-03-22T11:00:00+01:00",
            "end" : "2017-03-22T12:30:00+01:00"
        }, {
            "id" : 1702,
            "startDateTime" : "2017-04-05T12:00:00+02:00",
            "endDateTime" : "2017-04-05T13:30:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1427,
            "title" : "259004",
            "start" : "2017-04-05T12:00:00+02:00",
            "end" : "2017-04-05T13:30:00+02:00"
        }, {
            "id" : 1703,
            "startDateTime" : "2017-05-10T12:00:00+02:00",
            "endDateTime" : "2017-05-10T13:30:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1427,
            "title" : "259004",
            "start" : "2017-05-10T12:00:00+02:00",
            "end" : "2017-05-10T13:30:00+02:00"
        }, {
            "id" : 1704,
            "startDateTime" : "2017-06-07T12:00:00+02:00",
            "endDateTime" : "2017-06-07T13:30:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1427,
            "title" : "259004",
            "start" : "2017-06-07T12:00:00+02:00",
            "end" : "2017-06-07T13:30:00+02:00"
        }, {
            "id" : 1705,
            "startDateTime" : "2017-04-26T12:00:00+02:00",
            "endDateTime" : "2017-04-26T13:30:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1427,
            "title" : "259004",
            "start" : "2017-04-26T12:00:00+02:00",
            "end" : "2017-04-26T13:30:00+02:00"
        }, {
            "id" : 1706,
            "startDateTime" : "2017-05-31T12:00:00+02:00",
            "endDateTime" : "2017-05-31T13:30:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1427,
            "title" : "259004",
            "start" : "2017-05-31T12:00:00+02:00",
            "end" : "2017-05-31T13:30:00+02:00"
        }, {
            "id" : 1707,
            "startDateTime" : "2017-03-29T12:00:00+02:00",
            "endDateTime" : "2017-03-29T13:30:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1427,
            "title" : "259004",
            "start" : "2017-03-29T12:00:00+02:00",
            "end" : "2017-03-29T13:30:00+02:00"
        }, {
            "id" : 1708,
            "startDateTime" : "2017-05-17T12:00:00+02:00",
            "endDateTime" : "2017-05-17T13:30:00+02:00",
            "isExam" : false,
            "room" : "S3 057",
            "theme" : "",
            "lvaId" : 1427,
            "title" : "259004",
            "start" : "2017-05-17T12:00:00+02:00",
            "end" : "2017-05-17T13:30:00+02:00"
        }, {
            "id" : 1709,
            "startDateTime" : "2017-03-22T11:00:00+01:00",
            "endDateTime" : "2017-03-22T12:30:00+01:00",
            "isExam" : false,
            "room" : "S2 046",
            "theme" : "",
            "lvaId" : 1428,
            "title" : "259010",
            "start" : "2017-03-22T11:00:00+01:00",
            "end" : "2017-03-22T12:30:00+01:00"
        }, {
            "id" : 1710,
            "startDateTime" : "2017-05-31T12:00:00+02:00",
            "endDateTime" : "2017-05-31T13:30:00+02:00",
            "isExam" : false,
            "room" : "S2 046",
            "theme" : "",
            "lvaId" : 1428,
            "title" : "259010",
            "start" : "2017-05-31T12:00:00+02:00",
            "end" : "2017-05-31T13:30:00+02:00"
        }, {
            "id" : 1711,
            "startDateTime" : "2017-06-14T12:00:00+02:00",
            "endDateTime" : "2017-06-14T13:30:00+02:00",
            "isExam" : false,
            "room" : "S2 046",
            "theme" : "",
            "lvaId" : 1428,
            "title" : "259010",
            "start" : "2017-06-14T12:00:00+02:00",
            "end" : "2017-06-14T13:30:00+02:00"
        }, {
            "id" : 1712,
            "startDateTime" : "2017-03-29T12:00:00+02:00",
            "endDateTime" : "2017-03-29T13:30:00+02:00",
            "isExam" : false,
            "room" : "S2 046",
            "theme" : "",
            "lvaId" : 1428,
            "title" : "259010",
            "start" : "2017-03-29T12:00:00+02:00",
            "end" : "2017-03-29T13:30:00+02:00"
        }, {
            "id" : 1713,
            "startDateTime" : "2017-05-24T12:00:00+02:00",
            "endDateTime" : "2017-05-24T13:30:00+02:00",
            "isExam" : false,
            "room" : "S2 046",
            "theme" : "",
            "lvaId" : 1428,
            "title" : "259010",
            "start" : "2017-05-24T12:00:00+02:00",
            "end" : "2017-05-24T13:30:00+02:00"
        }, {
            "id" : 1714,
            "startDateTime" : "2017-03-08T11:00:00+01:00",
            "endDateTime" : "2017-03-08T12:30:00+01:00",
            "isExam" : false,
            "room" : "HS 4",
            "theme" : "Vorbesprechung - ANWESENHEITSPFLICHT!",
            "lvaId" : 1428,
            "title" : "259010",
            "start" : "2017-03-08T11:00:00+01:00",
            "end" : "2017-03-08T12:30:00+01:00"
        }, {
            "id" : 1715,
            "startDateTime" : "2017-05-17T12:00:00+02:00",
            "endDateTime" : "2017-05-17T13:30:00+02:00",
            "isExam" : false,
            "room" : "S2 046",
            "theme" : "",
            "lvaId" : 1428,
            "title" : "259010",
            "start" : "2017-05-17T12:00:00+02:00",
            "end" : "2017-05-17T13:30:00+02:00"
        }, {
            "id" : 1716,
            "startDateTime" : "2017-06-07T12:00:00+02:00",
            "endDateTime" : "2017-06-07T13:30:00+02:00",
            "isExam" : false,
            "room" : "S2 046",
            "theme" : "",
            "lvaId" : 1428,
            "title" : "259010",
            "start" : "2017-06-07T12:00:00+02:00",
            "end" : "2017-06-07T13:30:00+02:00"
        }, {
            "id" : 1717,
            "startDateTime" : "2017-06-21T12:00:00+02:00",
            "endDateTime" : "2017-06-21T13:30:00+02:00",
            "isExam" : false,
            "room" : "S2 046",
            "theme" : "",
            "lvaId" : 1428,
            "title" : "259010",
            "start" : "2017-06-21T12:00:00+02:00",
            "end" : "2017-06-21T13:30:00+02:00"
        }, {
            "id" : 1718,
            "startDateTime" : "2017-04-26T12:00:00+02:00",
            "endDateTime" : "2017-04-26T13:30:00+02:00",
            "isExam" : false,
            "room" : "S2 046",
            "theme" : "",
            "lvaId" : 1428,
            "title" : "259010",
            "start" : "2017-04-26T12:00:00+02:00",
            "end" : "2017-04-26T13:30:00+02:00"
        }, {
            "id" : 1719,
            "startDateTime" : "2017-03-15T11:00:00+01:00",
            "endDateTime" : "2017-03-15T12:30:00+01:00",
            "isExam" : false,
            "room" : "S2 046",
            "theme" : "",
            "lvaId" : 1428,
            "title" : "259010",
            "start" : "2017-03-15T11:00:00+01:00",
            "end" : "2017-03-15T12:30:00+01:00"
        }, {
            "id" : 1720,
            "startDateTime" : "2017-04-05T12:00:00+02:00",
            "endDateTime" : "2017-04-05T13:30:00+02:00",
            "isExam" : false,
            "room" : "S2 046",
            "theme" : "",
            "lvaId" : 1428,
            "title" : "259010",
            "start" : "2017-04-05T12:00:00+02:00",
            "end" : "2017-04-05T13:30:00+02:00"
        }, {
            "id" : 1721,
            "startDateTime" : "2017-05-03T12:00:00+02:00",
            "endDateTime" : "2017-05-03T13:30:00+02:00",
            "isExam" : false,
            "room" : "S2 046",
            "theme" : "",
            "lvaId" : 1428,
            "title" : "259010",
            "start" : "2017-05-03T12:00:00+02:00",
            "end" : "2017-05-03T13:30:00+02:00"
        }, {
            "id" : 1722,
            "startDateTime" : "2017-06-28T12:00:00+02:00",
            "endDateTime" : "2017-06-28T13:30:00+02:00",
            "isExam" : false,
            "room" : "S2 046",
            "theme" : "",
            "lvaId" : 1428,
            "title" : "259010",
            "start" : "2017-06-28T12:00:00+02:00",
            "end" : "2017-06-28T13:30:00+02:00"
        }, {
            "id" : 1723,
            "startDateTime" : "2017-05-10T12:00:00+02:00",
            "endDateTime" : "2017-05-10T13:30:00+02:00",
            "isExam" : false,
            "room" : "S2 046",
            "theme" : "",
            "lvaId" : 1428,
            "title" : "259010",
            "start" : "2017-05-10T12:00:00+02:00",
            "end" : "2017-05-10T13:30:00+02:00"
        }, {
            "id" : 3040,
            "startDateTime" : "2017-03-22T12:45:00+01:00",
            "endDateTime" : "2017-03-22T14:15:00+01:00",
            "isExam" : false,
            "room" : "HS 16",
            "theme" : "",
            "lvaId" : 2963,
            "title" : "257200",
            "start" : "2017-03-22T12:45:00+01:00",
            "end" : "2017-03-22T14:15:00+01:00"
        }, {
            "id" : 3041,
            "startDateTime" : "2017-04-05T13:45:00+02:00",
            "endDateTime" : "2017-04-05T15:15:00+02:00",
            "isExam" : false,
            "room" : "HS 16",
            "theme" : "",
            "lvaId" : 2963,
            "title" : "257200",
            "start" : "2017-04-05T13:45:00+02:00",
            "end" : "2017-04-05T15:15:00+02:00"
        }, {
            "id" : 3042,
            "startDateTime" : "2017-05-03T13:45:00+02:00",
            "endDateTime" : "2017-05-03T15:15:00+02:00",
            "isExam" : false,
            "room" : "HS 16",
            "theme" : "",
            "lvaId" : 2963,
            "title" : "257200",
            "start" : "2017-05-03T13:45:00+02:00",
            "end" : "2017-05-03T15:15:00+02:00"
        }, {
            "id" : 3043,
            "startDateTime" : "2017-03-07T12:45:00+01:00",
            "endDateTime" : "2017-03-07T14:15:00+01:00",
            "isExam" : false,
            "room" : "HS 10",
            "theme" : "Vorbesprechung",
            "lvaId" : 2963,
            "title" : "257200",
            "start" : "2017-03-07T12:45:00+01:00",
            "end" : "2017-03-07T14:15:00+01:00"
        }, {
            "id" : 3036,
            "startDateTime" : "2017-03-08T12:45:00+01:00",
            "endDateTime" : "2017-03-08T14:15:00+01:00",
            "isExam" : false,
            "room" : "HS 16",
            "theme" : "",
            "lvaId" : 2963,
            "title" : "257200",
            "start" : "2017-03-08T12:45:00+01:00",
            "end" : "2017-03-08T14:15:00+01:00"
        }, {
            "id" : 3037,
            "startDateTime" : "2017-04-26T13:45:00+02:00",
            "endDateTime" : "2017-04-26T15:15:00+02:00",
            "isExam" : false,
            "room" : "HS 16",
            "theme" : "",
            "lvaId" : 2963,
            "title" : "257200",
            "start" : "2017-04-26T13:45:00+02:00",
            "end" : "2017-04-26T15:15:00+02:00"
        }, {
            "id" : 3038,
            "startDateTime" : "2017-03-29T13:45:00+02:00",
            "endDateTime" : "2017-03-29T15:15:00+02:00",
            "isExam" : false,
            "room" : "HS 16",
            "theme" : "",
            "lvaId" : 2963,
            "title" : "257200",
            "start" : "2017-03-29T13:45:00+02:00",
            "end" : "2017-03-29T15:15:00+02:00"
        }, {
            "id" : 3039,
            "startDateTime" : "2017-03-15T12:45:00+01:00",
            "endDateTime" : "2017-03-15T14:15:00+01:00",
            "isExam" : false,
            "room" : "HS 16",
            "theme" : "",
            "lvaId" : 2963,
            "title" : "257200",
            "start" : "2017-03-15T12:45:00+01:00",
            "end" : "2017-03-15T14:15:00+01:00"
        }, {
            "id" : 3044,
            "startDateTime" : "2017-05-29T08:30:00+02:00",
            "endDateTime" : "2017-05-29T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 4",
            "theme" : "",
            "lvaId" : 2964,
            "title" : "257210",
            "start" : "2017-05-29T08:30:00+02:00",
            "end" : "2017-05-29T11:45:00+02:00"
        }, {
            "id" : 3045,
            "startDateTime" : "2017-06-19T08:30:00+02:00",
            "endDateTime" : "2017-06-19T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 4",
            "theme" : "",
            "lvaId" : 2964,
            "title" : "257210",
            "start" : "2017-06-19T08:30:00+02:00",
            "end" : "2017-06-19T11:45:00+02:00"
        }, {
            "id" : 3046,
            "startDateTime" : "2017-05-08T08:30:00+02:00",
            "endDateTime" : "2017-05-08T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 1",
            "theme" : "",
            "lvaId" : 2964,
            "title" : "257210",
            "start" : "2017-05-08T08:30:00+02:00",
            "end" : "2017-05-08T11:45:00+02:00"
        }, {
            "id" : 3047,
            "startDateTime" : "2017-06-12T08:30:00+02:00",
            "endDateTime" : "2017-06-12T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 1",
            "theme" : "",
            "lvaId" : 2964,
            "title" : "257210",
            "start" : "2017-06-12T08:30:00+02:00",
            "end" : "2017-06-12T11:45:00+02:00"
        }, {
            "id" : 3048,
            "startDateTime" : "2017-03-13T07:30:00+01:00",
            "endDateTime" : "2017-03-13T10:45:00+01:00",
            "isExam" : false,
            "room" : "HS 1",
            "theme" : "",
            "lvaId" : 2964,
            "title" : "257210",
            "start" : "2017-03-13T07:30:00+01:00",
            "end" : "2017-03-13T10:45:00+01:00"
        }, {
            "id" : 3049,
            "startDateTime" : "2017-03-13T07:30:00+01:00",
            "endDateTime" : "2017-03-13T10:45:00+01:00",
            "isExam" : false,
            "room" : "HS 1",
            "theme" : "",
            "lvaId" : 2965,
            "title" : "257211",
            "start" : "2017-03-13T07:30:00+01:00",
            "end" : "2017-03-13T10:45:00+01:00"
        }, {
            "id" : 3050,
            "startDateTime" : "2017-06-19T08:30:00+02:00",
            "endDateTime" : "2017-06-19T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 4",
            "theme" : "",
            "lvaId" : 2965,
            "title" : "257211",
            "start" : "2017-06-19T08:30:00+02:00",
            "end" : "2017-06-19T11:45:00+02:00"
        }, {
            "id" : 3051,
            "startDateTime" : "2017-06-12T08:30:00+02:00",
            "endDateTime" : "2017-06-12T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 1",
            "theme" : "",
            "lvaId" : 2965,
            "title" : "257211",
            "start" : "2017-06-12T08:30:00+02:00",
            "end" : "2017-06-12T11:45:00+02:00"
        }, {
            "id" : 3052,
            "startDateTime" : "2017-05-29T08:30:00+02:00",
            "endDateTime" : "2017-05-29T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 4",
            "theme" : "",
            "lvaId" : 2965,
            "title" : "257211",
            "start" : "2017-05-29T08:30:00+02:00",
            "end" : "2017-05-29T11:45:00+02:00"
        }, {
            "id" : 3053,
            "startDateTime" : "2017-05-08T08:30:00+02:00",
            "endDateTime" : "2017-05-08T11:45:00+02:00",
            "isExam" : true,
            "room" : "HS 1",
            "theme" : "Übungstest",
            "lvaId" : 2965,
            "title" : "257211",
            "start" : "2017-05-08T08:30:00+02:00",
            "end" : "2017-05-08T11:45:00+02:00"
        }, {
            "id" : 3056,
            "startDateTime" : "2017-06-12T08:30:00+02:00",
            "endDateTime" : "2017-06-12T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 1",
            "theme" : "",
            "lvaId" : 2966,
            "title" : "257212",
            "start" : "2017-06-12T08:30:00+02:00",
            "end" : "2017-06-12T11:45:00+02:00"
        }, {
            "id" : 3057,
            "startDateTime" : "2017-05-08T08:30:00+02:00",
            "endDateTime" : "2017-05-08T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 1",
            "theme" : "",
            "lvaId" : 2966,
            "title" : "257212",
            "start" : "2017-05-08T08:30:00+02:00",
            "end" : "2017-05-08T11:45:00+02:00"
        }, {
            "id" : 3058,
            "startDateTime" : "2017-06-19T08:30:00+02:00",
            "endDateTime" : "2017-06-19T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 4",
            "theme" : "",
            "lvaId" : 2966,
            "title" : "257212",
            "start" : "2017-06-19T08:30:00+02:00",
            "end" : "2017-06-19T11:45:00+02:00"
        }, {
            "id" : 3054,
            "startDateTime" : "2017-03-13T07:30:00+01:00",
            "endDateTime" : "2017-03-13T10:45:00+01:00",
            "isExam" : false,
            "room" : "HS 1",
            "theme" : "",
            "lvaId" : 2966,
            "title" : "257212",
            "start" : "2017-03-13T07:30:00+01:00",
            "end" : "2017-03-13T10:45:00+01:00"
        }, {
            "id" : 3055,
            "startDateTime" : "2017-05-29T08:30:00+02:00",
            "endDateTime" : "2017-05-29T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 4",
            "theme" : "",
            "lvaId" : 2966,
            "title" : "257212",
            "start" : "2017-05-29T08:30:00+02:00",
            "end" : "2017-05-29T11:45:00+02:00"
        }, {
            "id" : 3120,
            "startDateTime" : "2017-03-13T07:30:00+01:00",
            "endDateTime" : "2017-03-13T10:45:00+01:00",
            "isExam" : false,
            "room" : "HS 1",
            "theme" : "",
            "lvaId" : 2973,
            "title" : "257213",
            "start" : "2017-03-13T07:30:00+01:00",
            "end" : "2017-03-13T10:45:00+01:00"
        }, {
            "id" : 3121,
            "startDateTime" : "2017-05-08T08:30:00+02:00",
            "endDateTime" : "2017-05-08T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 1",
            "theme" : "",
            "lvaId" : 2973,
            "title" : "257213",
            "start" : "2017-05-08T08:30:00+02:00",
            "end" : "2017-05-08T11:45:00+02:00"
        }, {
            "id" : 3122,
            "startDateTime" : "2017-06-12T08:30:00+02:00",
            "endDateTime" : "2017-06-12T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 1",
            "theme" : "",
            "lvaId" : 2973,
            "title" : "257213",
            "start" : "2017-06-12T08:30:00+02:00",
            "end" : "2017-06-12T11:45:00+02:00"
        }, {
            "id" : 3123,
            "startDateTime" : "2017-05-29T08:30:00+02:00",
            "endDateTime" : "2017-05-29T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 4",
            "theme" : "",
            "lvaId" : 2973,
            "title" : "257213",
            "start" : "2017-05-29T08:30:00+02:00",
            "end" : "2017-05-29T11:45:00+02:00"
        }, {
            "id" : 3119,
            "startDateTime" : "2017-06-19T08:30:00+02:00",
            "endDateTime" : "2017-06-19T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 4",
            "theme" : "",
            "lvaId" : 2973,
            "title" : "257213",
            "start" : "2017-06-19T08:30:00+02:00",
            "end" : "2017-06-19T11:45:00+02:00"
        }, {
            "id" : 3124,
            "startDateTime" : "2017-05-29T08:30:00+02:00",
            "endDateTime" : "2017-05-29T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 4",
            "theme" : "",
            "lvaId" : 2974,
            "title" : "257214",
            "start" : "2017-05-29T08:30:00+02:00",
            "end" : "2017-05-29T11:45:00+02:00"
        }, {
            "id" : 3125,
            "startDateTime" : "2017-03-13T07:30:00+01:00",
            "endDateTime" : "2017-03-13T10:45:00+01:00",
            "isExam" : false,
            "room" : "HS 1",
            "theme" : "",
            "lvaId" : 2974,
            "title" : "257214",
            "start" : "2017-03-13T07:30:00+01:00",
            "end" : "2017-03-13T10:45:00+01:00"
        }, {
            "id" : 3126,
            "startDateTime" : "2017-05-08T08:30:00+02:00",
            "endDateTime" : "2017-05-08T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 1",
            "theme" : "",
            "lvaId" : 2974,
            "title" : "257214",
            "start" : "2017-05-08T08:30:00+02:00",
            "end" : "2017-05-08T11:45:00+02:00"
        }, {
            "id" : 3127,
            "startDateTime" : "2017-06-12T08:30:00+02:00",
            "endDateTime" : "2017-06-12T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 1",
            "theme" : "",
            "lvaId" : 2974,
            "title" : "257214",
            "start" : "2017-06-12T08:30:00+02:00",
            "end" : "2017-06-12T11:45:00+02:00"
        }, {
            "id" : 3128,
            "startDateTime" : "2017-06-19T08:30:00+02:00",
            "endDateTime" : "2017-06-19T11:45:00+02:00",
            "isExam" : false,
            "room" : "HS 4",
            "theme" : "",
            "lvaId" : 2974,
            "title" : "257214",
            "start" : "2017-06-19T08:30:00+02:00",
            "end" : "2017-06-19T11:45:00+02:00"
        }, {
            "id" : 2244,
            "startDateTime" : "2017-05-08T12:45:00+02:00",
            "endDateTime" : "2017-05-08T15:15:00+02:00",
            "isExam" : false,
            "room" : "HS 10",
            "theme" : "",
            "lvaId" : 1983,
            "title" : "353006",
            "start" : "2017-05-08T12:45:00+02:00",
            "end" : "2017-05-08T15:15:00+02:00"
        }, {
            "id" : 2245,
            "startDateTime" : "2017-04-03T12:45:00+02:00",
            "endDateTime" : "2017-04-03T15:15:00+02:00",
            "isExam" : false,
            "room" : "HS 10",
            "theme" : "",
            "lvaId" : 1983,
            "title" : "353006",
            "start" : "2017-04-03T12:45:00+02:00",
            "end" : "2017-04-03T15:15:00+02:00"
        }, {
            "id" : 2246,
            "startDateTime" : "2017-06-20T15:30:00+02:00",
            "endDateTime" : "2017-06-20T17:00:00+02:00",
            "isExam" : false,
            "room" : "HS 1",
            "theme" : "Sitzplan Nachname A-Lich",
            "lvaId" : 1983,
            "title" : "353006",
            "start" : "2017-06-20T15:30:00+02:00",
            "end" : "2017-06-20T17:00:00+02:00"
        }, {
            "id" : 2247,
            "startDateTime" : "2017-05-15T12:45:00+02:00",
            "endDateTime" : "2017-05-15T15:15:00+02:00",
            "isExam" : false,
            "room" : "HS 10",
            "theme" : "",
            "lvaId" : 1983,
            "title" : "353006",
            "start" : "2017-05-15T12:45:00+02:00",
            "end" : "2017-05-15T15:15:00+02:00"
        }, {
            "id" : 2248,
            "startDateTime" : "2017-03-06T11:45:00+01:00",
            "endDateTime" : "2017-03-06T14:15:00+01:00",
            "isExam" : false,
            "room" : "HS 10",
            "theme" : "",
            "lvaId" : 1983,
            "title" : "353006",
            "start" : "2017-03-06T11:45:00+01:00",
            "end" : "2017-03-06T14:15:00+01:00"
        }, {
            "id" : 2249,
            "startDateTime" : "2017-06-20T15:30:00+02:00",
            "endDateTime" : "2017-06-20T17:00:00+02:00",
            "isExam" : false,
            "room" : "HS 2",
            "theme" : "Sitzplan Nachname Lin-Ra",
            "lvaId" : 1983,
            "title" : "353006",
            "start" : "2017-06-20T15:30:00+02:00",
            "end" : "2017-06-20T17:00:00+02:00"
        }, {
            "id" : 2250,
            "startDateTime" : "2017-06-20T15:30:00+02:00",
            "endDateTime" : "2017-06-20T17:00:00+02:00",
            "isExam" : false,
            "room" : "HS 16",
            "theme" : "Sitzplan Nachname Re-Z",
            "lvaId" : 1983,
            "title" : "353006",
            "start" : "2017-06-20T15:30:00+02:00",
            "end" : "2017-06-20T17:00:00+02:00"
        }, {
            "id" : 2251,
            "startDateTime" : "2017-06-12T12:45:00+02:00",
            "endDateTime" : "2017-06-12T15:15:00+02:00",
            "isExam" : false,
            "room" : "HS 10",
            "theme" : "Fragestunde zur Klausur",
            "lvaId" : 1983,
            "title" : "353006",
            "start" : "2017-06-12T12:45:00+02:00",
            "end" : "2017-06-12T15:15:00+02:00"
        }, {
            "id" : 2252,
            "startDateTime" : "2017-05-22T12:45:00+02:00",
            "endDateTime" : "2017-05-22T15:15:00+02:00",
            "isExam" : false,
            "room" : "HS 10",
            "theme" : "",
            "lvaId" : 1983,
            "title" : "353006",
            "start" : "2017-05-22T12:45:00+02:00",
            "end" : "2017-05-22T15:15:00+02:00"
        }, {
            "id" : 2253,
            "startDateTime" : "2017-03-13T11:45:00+01:00",
            "endDateTime" : "2017-03-13T14:15:00+01:00",
            "isExam" : false,
            "room" : "HS 10",
            "theme" : "",
            "lvaId" : 1983,
            "title" : "353006",
            "start" : "2017-03-13T11:45:00+01:00",
            "end" : "2017-03-13T14:15:00+01:00"
        }, {
            "id" : 2254,
            "startDateTime" : "2017-04-24T12:45:00+02:00",
            "endDateTime" : "2017-04-24T15:15:00+02:00",
            "isExam" : false,
            "room" : "HS 10",
            "theme" : "",
            "lvaId" : 1983,
            "title" : "353006",
            "start" : "2017-04-24T12:45:00+02:00",
            "end" : "2017-04-24T15:15:00+02:00"
        }, {
            "id" : 2255,
            "startDateTime" : "2017-03-27T12:45:00+02:00",
            "endDateTime" : "2017-03-27T15:15:00+02:00",
            "isExam" : false,
            "room" : "HS 10",
            "theme" : "",
            "lvaId" : 1983,
            "title" : "353006",
            "start" : "2017-03-27T12:45:00+02:00",
            "end" : "2017-03-27T15:15:00+02:00"
        }, {
            "id" : 1536,
            "startDateTime" : "2017-06-08T08:30:00+02:00",
            "endDateTime" : "2017-06-08T10:00:00+02:00",
            "isExam" : false,
            "room" : "HS 16",
            "theme" : "",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-06-08T08:30:00+02:00",
            "end" : "2017-06-08T10:00:00+02:00"
        }, {
            "id" : 1537,
            "startDateTime" : "2017-06-29T08:30:00+02:00",
            "endDateTime" : "2017-06-29T10:00:00+02:00",
            "isExam" : true,
            "room" : "BA 9910",
            "theme" : "Klausur (L-Ma)",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-06-29T08:30:00+02:00",
            "end" : "2017-06-29T10:00:00+02:00"
        }, {
            "id" : 1538,
            "startDateTime" : "2017-06-29T08:30:00+02:00",
            "endDateTime" : "2017-06-29T10:00:00+02:00",
            "isExam" : true,
            "room" : "MT 127",
            "theme" : "Klausur (Mi-Or)",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-06-29T08:30:00+02:00",
            "end" : "2017-06-29T10:00:00+02:00"
        }, {
            "id" : 1539,
            "startDateTime" : "2017-06-01T08:30:00+02:00",
            "endDateTime" : "2017-06-01T10:00:00+02:00",
            "isExam" : false,
            "room" : "HS 16",
            "theme" : "",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-06-01T08:30:00+02:00",
            "end" : "2017-06-01T10:00:00+02:00"
        }, {
            "id" : 1540,
            "startDateTime" : "2017-06-29T08:30:00+02:00",
            "endDateTime" : "2017-06-29T10:00:00+02:00",
            "isExam" : true,
            "room" : "K 033C",
            "theme" : "Klausur (K)",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-06-29T08:30:00+02:00",
            "end" : "2017-06-29T10:00:00+02:00"
        }, {
            "id" : 1541,
            "startDateTime" : "2017-03-02T07:30:00+01:00",
            "endDateTime" : "2017-03-02T09:00:00+01:00",
            "isExam" : false,
            "room" : "HS 10",
            "theme" : "",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-03-02T07:30:00+01:00",
            "end" : "2017-03-02T09:00:00+01:00"
        }, {
            "id" : 1542,
            "startDateTime" : "2017-04-27T08:30:00+02:00",
            "endDateTime" : "2017-04-27T10:00:00+02:00",
            "isExam" : false,
            "room" : "HS 16",
            "theme" : "",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-04-27T08:30:00+02:00",
            "end" : "2017-04-27T10:00:00+02:00"
        }, {
            "id" : 1543,
            "startDateTime" : "2017-03-16T07:30:00+01:00",
            "endDateTime" : "2017-03-16T09:00:00+01:00",
            "isExam" : false,
            "room" : "HS 10",
            "theme" : "",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-03-16T07:30:00+01:00",
            "end" : "2017-03-16T09:00:00+01:00"
        }, {
            "id" : 1544,
            "startDateTime" : "2017-06-29T08:30:00+02:00",
            "endDateTime" : "2017-06-29T10:00:00+02:00",
            "isExam" : true,
            "room" : "HS 16",
            "theme" : "Klausur (Schr-Z)",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-06-29T08:30:00+02:00",
            "end" : "2017-06-29T10:00:00+02:00"
        }, {
            "id" : 1545,
            "startDateTime" : "2017-06-22T08:30:00+02:00",
            "endDateTime" : "2017-06-22T10:00:00+02:00",
            "isExam" : false,
            "room" : "HS 16",
            "theme" : "",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-06-22T08:30:00+02:00",
            "end" : "2017-06-22T10:00:00+02:00"
        }, {
            "id" : 1546,
            "startDateTime" : "2017-06-29T08:30:00+02:00",
            "endDateTime" : "2017-06-29T10:00:00+02:00",
            "isExam" : true,
            "room" : "BA 9911",
            "theme" : "Klausur (Bo-E)",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-06-29T08:30:00+02:00",
            "end" : "2017-06-29T10:00:00+02:00"
        }, {
            "id" : 1527,
            "startDateTime" : "2017-04-06T08:30:00+02:00",
            "endDateTime" : "2017-04-06T10:00:00+02:00",
            "isExam" : false,
            "room" : "HS 16",
            "theme" : "",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-04-06T08:30:00+02:00",
            "end" : "2017-04-06T10:00:00+02:00"
        }, {
            "id" : 1528,
            "startDateTime" : "2017-03-30T08:30:00+02:00",
            "endDateTime" : "2017-03-30T10:00:00+02:00",
            "isExam" : false,
            "room" : "HS 16",
            "theme" : "",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-03-30T08:30:00+02:00",
            "end" : "2017-03-30T10:00:00+02:00"
        }, {
            "id" : 1529,
            "startDateTime" : "2017-06-29T08:30:00+02:00",
            "endDateTime" : "2017-06-29T10:00:00+02:00",
            "isExam" : true,
            "room" : "HS 7",
            "theme" : "Klausur (Os-Schö)",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-06-29T08:30:00+02:00",
            "end" : "2017-06-29T10:00:00+02:00"
        }, {
            "id" : 1530,
            "startDateTime" : "2017-03-23T07:30:00+01:00",
            "endDateTime" : "2017-03-23T09:00:00+01:00",
            "isExam" : false,
            "room" : "HS 16",
            "theme" : "",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-03-23T07:30:00+01:00",
            "end" : "2017-03-23T09:00:00+01:00"
        }, {
            "id" : 1531,
            "startDateTime" : "2017-03-09T07:30:00+01:00",
            "endDateTime" : "2017-03-09T09:00:00+01:00",
            "isExam" : false,
            "room" : "HS 16",
            "theme" : "",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-03-09T07:30:00+01:00",
            "end" : "2017-03-09T09:00:00+01:00"
        }, {
            "id" : 1532,
            "startDateTime" : "2017-06-29T08:30:00+02:00",
            "endDateTime" : "2017-06-29T10:00:00+02:00",
            "isExam" : true,
            "room" : "HS 10",
            "theme" : "Klausur (F-J)",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-06-29T08:30:00+02:00",
            "end" : "2017-06-29T10:00:00+02:00"
        }, {
            "id" : 1533,
            "startDateTime" : "2017-06-29T08:30:00+02:00",
            "endDateTime" : "2017-06-29T10:00:00+02:00",
            "isExam" : true,
            "room" : "HS 3",
            "theme" : "Klausur (A-Bl)",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-06-29T08:30:00+02:00",
            "end" : "2017-06-29T10:00:00+02:00"
        }, {
            "id" : 1534,
            "startDateTime" : "2017-05-11T08:30:00+02:00",
            "endDateTime" : "2017-05-11T10:00:00+02:00",
            "isExam" : false,
            "room" : "HS 16",
            "theme" : "",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-05-11T08:30:00+02:00",
            "end" : "2017-05-11T10:00:00+02:00"
        }, {
            "id" : 1535,
            "startDateTime" : "2017-05-18T08:30:00+02:00",
            "endDateTime" : "2017-05-18T10:00:00+02:00",
            "isExam" : false,
            "room" : "HS 9",
            "theme" : "",
            "lvaId" : 1412,
            "title" : "339191",
            "start" : "2017-05-18T08:30:00+02:00",
            "end" : "2017-05-18T10:00:00+02:00"
        }, {
            "id" : 2432,
            "startDateTime" : "2017-03-30T15:30:00+02:00",
            "endDateTime" : "2017-03-30T17:00:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2453,
            "title" : "367028",
            "start" : "2017-03-30T15:30:00+02:00",
            "end" : "2017-03-30T17:00:00+02:00"
        }, {
            "id" : 2433,
            "startDateTime" : "2017-06-29T17:15:00+02:00",
            "endDateTime" : "2017-06-29T18:45:00+02:00",
            "isExam" : true,
            "room" : "HS 9",
            "theme" : "Übungstest",
            "lvaId" : 2453,
            "title" : "367028",
            "start" : "2017-06-29T17:15:00+02:00",
            "end" : "2017-06-29T18:45:00+02:00"
        }, {
            "id" : 2434,
            "startDateTime" : "2017-06-01T15:30:00+02:00",
            "endDateTime" : "2017-06-01T17:00:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2453,
            "title" : "367028",
            "start" : "2017-06-01T15:30:00+02:00",
            "end" : "2017-06-01T17:00:00+02:00"
        }, {
            "id" : 2435,
            "startDateTime" : "2017-04-06T15:30:00+02:00",
            "endDateTime" : "2017-04-06T17:00:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2453,
            "title" : "367028",
            "start" : "2017-04-06T15:30:00+02:00",
            "end" : "2017-04-06T17:00:00+02:00"
        }, {
            "id" : 2436,
            "startDateTime" : "2017-04-27T15:30:00+02:00",
            "endDateTime" : "2017-04-27T17:00:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2453,
            "title" : "367028",
            "start" : "2017-04-27T15:30:00+02:00",
            "end" : "2017-04-27T17:00:00+02:00"
        }, {
            "id" : 2437,
            "startDateTime" : "2017-03-23T14:30:00+01:00",
            "endDateTime" : "2017-03-23T16:00:00+01:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2453,
            "title" : "367028",
            "start" : "2017-03-23T14:30:00+01:00",
            "end" : "2017-03-23T16:00:00+01:00"
        }, {
            "id" : 2438,
            "startDateTime" : "2017-05-11T15:30:00+02:00",
            "endDateTime" : "2017-05-11T17:00:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2453,
            "title" : "367028",
            "start" : "2017-05-11T15:30:00+02:00",
            "end" : "2017-05-11T17:00:00+02:00"
        }, {
            "id" : 2439,
            "startDateTime" : "2017-03-02T14:30:00+01:00",
            "endDateTime" : "2017-03-02T16:00:00+01:00",
            "isExam" : false,
            "room" : "S3 055",
            "theme" : "",
            "lvaId" : 2453,
            "title" : "367028",
            "start" : "2017-03-02T14:30:00+01:00",
            "end" : "2017-03-02T16:00:00+01:00"
        }, {
            "id" : 2440,
            "startDateTime" : "2017-05-18T15:30:00+02:00",
            "endDateTime" : "2017-05-18T17:00:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2453,
            "title" : "367028",
            "start" : "2017-05-18T15:30:00+02:00",
            "end" : "2017-05-18T17:00:00+02:00"
        }, {
            "id" : 2441,
            "startDateTime" : "2017-03-09T14:30:00+01:00",
            "endDateTime" : "2017-03-09T16:00:00+01:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2453,
            "title" : "367028",
            "start" : "2017-03-09T14:30:00+01:00",
            "end" : "2017-03-09T16:00:00+01:00"
        }, {
            "id" : 2442,
            "startDateTime" : "2017-06-08T15:30:00+02:00",
            "endDateTime" : "2017-06-08T17:00:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2453,
            "title" : "367028",
            "start" : "2017-06-08T15:30:00+02:00",
            "end" : "2017-06-08T17:00:00+02:00"
        }, {
            "id" : 2443,
            "startDateTime" : "2017-06-22T15:30:00+02:00",
            "endDateTime" : "2017-06-22T17:00:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2453,
            "title" : "367028",
            "start" : "2017-06-22T15:30:00+02:00",
            "end" : "2017-06-22T17:00:00+02:00"
        }, {
            "id" : 2431,
            "startDateTime" : "2017-03-16T14:30:00+01:00",
            "endDateTime" : "2017-03-16T16:00:00+01:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2453,
            "title" : "367028",
            "start" : "2017-03-16T14:30:00+01:00",
            "end" : "2017-03-16T16:00:00+01:00"
        }, {
            "id" : 2501,
            "startDateTime" : "2017-03-09T11:00:00+01:00",
            "endDateTime" : "2017-03-09T12:30:00+01:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2454,
            "title" : "367029",
            "start" : "2017-03-09T11:00:00+01:00",
            "end" : "2017-03-09T12:30:00+01:00"
        }, {
            "id" : 2502,
            "startDateTime" : "2017-06-01T12:00:00+02:00",
            "endDateTime" : "2017-06-01T13:30:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2454,
            "title" : "367029",
            "start" : "2017-06-01T12:00:00+02:00",
            "end" : "2017-06-01T13:30:00+02:00"
        }, {
            "id" : 2503,
            "startDateTime" : "2017-03-16T11:00:00+01:00",
            "endDateTime" : "2017-03-16T12:30:00+01:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2454,
            "title" : "367029",
            "start" : "2017-03-16T11:00:00+01:00",
            "end" : "2017-03-16T12:30:00+01:00"
        }, {
            "id" : 2504,
            "startDateTime" : "2017-03-23T11:00:00+01:00",
            "endDateTime" : "2017-03-23T12:30:00+01:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2454,
            "title" : "367029",
            "start" : "2017-03-23T11:00:00+01:00",
            "end" : "2017-03-23T12:30:00+01:00"
        }, {
            "id" : 2505,
            "startDateTime" : "2017-05-11T12:00:00+02:00",
            "endDateTime" : "2017-05-11T13:30:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2454,
            "title" : "367029",
            "start" : "2017-05-11T12:00:00+02:00",
            "end" : "2017-05-11T13:30:00+02:00"
        }, {
            "id" : 2506,
            "startDateTime" : "2017-05-18T12:00:00+02:00",
            "endDateTime" : "2017-05-18T13:30:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2454,
            "title" : "367029",
            "start" : "2017-05-18T12:00:00+02:00",
            "end" : "2017-05-18T13:30:00+02:00"
        }, {
            "id" : 2444,
            "startDateTime" : "2017-03-30T12:00:00+02:00",
            "endDateTime" : "2017-03-30T13:30:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2454,
            "title" : "367029",
            "start" : "2017-03-30T12:00:00+02:00",
            "end" : "2017-03-30T13:30:00+02:00"
        }, {
            "id" : 2445,
            "startDateTime" : "2017-04-27T12:00:00+02:00",
            "endDateTime" : "2017-04-27T13:30:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2454,
            "title" : "367029",
            "start" : "2017-04-27T12:00:00+02:00",
            "end" : "2017-04-27T13:30:00+02:00"
        }, {
            "id" : 2446,
            "startDateTime" : "2017-06-08T12:00:00+02:00",
            "endDateTime" : "2017-06-08T13:30:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2454,
            "title" : "367029",
            "start" : "2017-06-08T12:00:00+02:00",
            "end" : "2017-06-08T13:30:00+02:00"
        }, {
            "id" : 2447,
            "startDateTime" : "2017-06-22T12:00:00+02:00",
            "endDateTime" : "2017-06-22T13:30:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2454,
            "title" : "367029",
            "start" : "2017-06-22T12:00:00+02:00",
            "end" : "2017-06-22T13:30:00+02:00"
        }, {
            "id" : 2448,
            "startDateTime" : "2017-04-06T12:00:00+02:00",
            "endDateTime" : "2017-04-06T13:30:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2454,
            "title" : "367029",
            "start" : "2017-04-06T12:00:00+02:00",
            "end" : "2017-04-06T13:30:00+02:00"
        }, {
            "id" : 2449,
            "startDateTime" : "2017-06-29T17:15:00+02:00",
            "endDateTime" : "2017-06-29T18:45:00+02:00",
            "isExam" : true,
            "room" : "HS 19",
            "theme" : "Übungstest",
            "lvaId" : 2454,
            "title" : "367029",
            "start" : "2017-06-29T17:15:00+02:00",
            "end" : "2017-06-29T18:45:00+02:00"
        }, {
            "id" : 2450,
            "startDateTime" : "2017-03-02T11:00:00+01:00",
            "endDateTime" : "2017-03-02T12:30:00+01:00",
            "isExam" : false,
            "room" : "S3 055",
            "theme" : "",
            "lvaId" : 2454,
            "title" : "367029",
            "start" : "2017-03-02T11:00:00+01:00",
            "end" : "2017-03-02T12:30:00+01:00"
        }, {
            "id" : 2507,
            "startDateTime" : "2017-03-09T09:15:00+01:00",
            "endDateTime" : "2017-03-09T10:45:00+01:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2455,
            "title" : "367030",
            "start" : "2017-03-09T09:15:00+01:00",
            "end" : "2017-03-09T10:45:00+01:00"
        }, {
            "id" : 2508,
            "startDateTime" : "2017-06-08T10:15:00+02:00",
            "endDateTime" : "2017-06-08T11:45:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2455,
            "title" : "367030",
            "start" : "2017-06-08T10:15:00+02:00",
            "end" : "2017-06-08T11:45:00+02:00"
        }, {
            "id" : 2509,
            "startDateTime" : "2017-05-11T10:15:00+02:00",
            "endDateTime" : "2017-05-11T11:45:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2455,
            "title" : "367030",
            "start" : "2017-05-11T10:15:00+02:00",
            "end" : "2017-05-11T11:45:00+02:00"
        }, {
            "id" : 2510,
            "startDateTime" : "2017-05-18T10:15:00+02:00",
            "endDateTime" : "2017-05-18T11:45:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2455,
            "title" : "367030",
            "start" : "2017-05-18T10:15:00+02:00",
            "end" : "2017-05-18T11:45:00+02:00"
        }, {
            "id" : 2511,
            "startDateTime" : "2017-03-23T09:15:00+01:00",
            "endDateTime" : "2017-03-23T10:45:00+01:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2455,
            "title" : "367030",
            "start" : "2017-03-23T09:15:00+01:00",
            "end" : "2017-03-23T10:45:00+01:00"
        }, {
            "id" : 2512,
            "startDateTime" : "2017-04-27T10:15:00+02:00",
            "endDateTime" : "2017-04-27T11:45:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2455,
            "title" : "367030",
            "start" : "2017-04-27T10:15:00+02:00",
            "end" : "2017-04-27T11:45:00+02:00"
        }, {
            "id" : 2513,
            "startDateTime" : "2017-06-22T10:15:00+02:00",
            "endDateTime" : "2017-06-22T11:45:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2455,
            "title" : "367030",
            "start" : "2017-06-22T10:15:00+02:00",
            "end" : "2017-06-22T11:45:00+02:00"
        }, {
            "id" : 2514,
            "startDateTime" : "2017-03-02T09:15:00+01:00",
            "endDateTime" : "2017-03-02T10:45:00+01:00",
            "isExam" : false,
            "room" : "S3 055",
            "theme" : "",
            "lvaId" : 2455,
            "title" : "367030",
            "start" : "2017-03-02T09:15:00+01:00",
            "end" : "2017-03-02T10:45:00+01:00"
        }, {
            "id" : 2515,
            "startDateTime" : "2017-03-30T10:15:00+02:00",
            "endDateTime" : "2017-03-30T11:45:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2455,
            "title" : "367030",
            "start" : "2017-03-30T10:15:00+02:00",
            "end" : "2017-03-30T11:45:00+02:00"
        }, {
            "id" : 2516,
            "startDateTime" : "2017-06-01T10:15:00+02:00",
            "endDateTime" : "2017-06-01T11:45:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2455,
            "title" : "367030",
            "start" : "2017-06-01T10:15:00+02:00",
            "end" : "2017-06-01T11:45:00+02:00"
        }, {
            "id" : 2517,
            "startDateTime" : "2017-03-16T09:15:00+01:00",
            "endDateTime" : "2017-03-16T10:45:00+01:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2455,
            "title" : "367030",
            "start" : "2017-03-16T09:15:00+01:00",
            "end" : "2017-03-16T10:45:00+01:00"
        }, {
            "id" : 2518,
            "startDateTime" : "2017-04-06T10:15:00+02:00",
            "endDateTime" : "2017-04-06T11:45:00+02:00",
            "isExam" : false,
            "room" : "MT 132",
            "theme" : "",
            "lvaId" : 2455,
            "title" : "367030",
            "start" : "2017-04-06T10:15:00+02:00",
            "end" : "2017-04-06T11:45:00+02:00"
        }, {
            "id" : 2519,
            "startDateTime" : "2017-06-29T17:15:00+02:00",
            "endDateTime" : "2017-06-29T18:45:00+02:00",
            "isExam" : true,
            "room" : "HS 9",
            "theme" : "Übungstest",
            "lvaId" : 2455,
            "title" : "367030",
            "start" : "2017-06-29T17:15:00+02:00",
            "end" : "2017-06-29T18:45:00+02:00"
        }, {
            "id" : 2304,
            "startDateTime" : "2017-03-30T13:45:00+02:00",
            "endDateTime" : "2017-03-30T16:15:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1989,
            "title" : "326712",
            "start" : "2017-03-30T13:45:00+02:00",
            "end" : "2017-03-30T16:15:00+02:00"
        }, {
            "id" : 2305,
            "startDateTime" : "2017-06-01T13:45:00+02:00",
            "endDateTime" : "2017-06-01T16:15:00+02:00",
            "isExam" : false,
            "room" : "HS 2",
            "theme" : "",
            "lvaId" : 1989,
            "title" : "326712",
            "start" : "2017-06-01T13:45:00+02:00",
            "end" : "2017-06-01T16:15:00+02:00"
        }, {
            "id" : 2306,
            "startDateTime" : "2017-03-23T12:45:00+01:00",
            "endDateTime" : "2017-03-23T15:15:00+01:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1989,
            "title" : "326712",
            "start" : "2017-03-23T12:45:00+01:00",
            "end" : "2017-03-23T15:15:00+01:00"
        }, {
            "id" : 2293,
            "startDateTime" : "2017-03-09T12:45:00+01:00",
            "endDateTime" : "2017-03-09T15:15:00+01:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1989,
            "title" : "326712",
            "start" : "2017-03-09T12:45:00+01:00",
            "end" : "2017-03-09T15:15:00+01:00"
        }, {
            "id" : 2294,
            "startDateTime" : "2017-06-08T13:45:00+02:00",
            "endDateTime" : "2017-06-08T16:15:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1989,
            "title" : "326712",
            "start" : "2017-06-08T13:45:00+02:00",
            "end" : "2017-06-08T16:15:00+02:00"
        }, {
            "id" : 2295,
            "startDateTime" : "2017-06-22T13:45:00+02:00",
            "endDateTime" : "2017-06-22T16:15:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1989,
            "title" : "326712",
            "start" : "2017-06-22T13:45:00+02:00",
            "end" : "2017-06-22T16:15:00+02:00"
        }, {
            "id" : 2296,
            "startDateTime" : "2017-03-16T12:45:00+01:00",
            "endDateTime" : "2017-03-16T15:15:00+01:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1989,
            "title" : "326712",
            "start" : "2017-03-16T12:45:00+01:00",
            "end" : "2017-03-16T15:15:00+01:00"
        }, {
            "id" : 2297,
            "startDateTime" : "2017-05-11T13:45:00+02:00",
            "endDateTime" : "2017-05-11T16:15:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1989,
            "title" : "326712",
            "start" : "2017-05-11T13:45:00+02:00",
            "end" : "2017-05-11T16:15:00+02:00"
        }, {
            "id" : 2298,
            "startDateTime" : "2017-04-27T13:45:00+02:00",
            "endDateTime" : "2017-04-27T16:15:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1989,
            "title" : "326712",
            "start" : "2017-04-27T13:45:00+02:00",
            "end" : "2017-04-27T16:15:00+02:00"
        }, {
            "id" : 2299,
            "startDateTime" : "2017-04-06T13:45:00+02:00",
            "endDateTime" : "2017-04-06T16:15:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1989,
            "title" : "326712",
            "start" : "2017-04-06T13:45:00+02:00",
            "end" : "2017-04-06T16:15:00+02:00"
        }, {
            "id" : 2300,
            "startDateTime" : "2017-07-06T13:45:00+02:00",
            "endDateTime" : "2017-07-06T16:15:00+02:00",
            "isExam" : false,
            "room" : "HS 19",
            "theme" : "",
            "lvaId" : 1989,
            "title" : "326712",
            "start" : "2017-07-06T13:45:00+02:00",
            "end" : "2017-07-06T16:15:00+02:00"
        }, {
            "id" : 2301,
            "startDateTime" : "2017-05-18T13:45:00+02:00",
            "endDateTime" : "2017-05-18T16:15:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1989,
            "title" : "326712",
            "start" : "2017-05-18T13:45:00+02:00",
            "end" : "2017-05-18T16:15:00+02:00"
        }, {
            "id" : 2302,
            "startDateTime" : "2017-07-06T13:45:00+02:00",
            "endDateTime" : "2017-07-06T16:15:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1989,
            "title" : "326712",
            "start" : "2017-07-06T13:45:00+02:00",
            "end" : "2017-07-06T16:15:00+02:00"
        }, {
            "id" : 2303,
            "startDateTime" : "2017-06-29T13:45:00+02:00",
            "endDateTime" : "2017-06-29T16:15:00+02:00",
            "isExam" : false,
            "room" : "HS 18",
            "theme" : "",
            "lvaId" : 1989,
            "title" : "326712",
            "start" : "2017-06-29T13:45:00+02:00",
            "end" : "2017-06-29T16:15:00+02:00"
        }, {
            "id" : 2307,
            "startDateTime" : "2017-06-08T18:00:00+02:00",
            "endDateTime" : "2017-06-08T18:45:00+02:00",
            "isExam" : false,
            "room" : "HS 3",
            "theme" : "",
            "lvaId" : 1990,
            "title" : "326703",
            "start" : "2017-06-08T18:00:00+02:00",
            "end" : "2017-06-08T18:45:00+02:00"
        }, {
            "id" : 2308,
            "startDateTime" : "2017-06-22T18:00:00+02:00",
            "endDateTime" : "2017-06-22T18:45:00+02:00",
            "isExam" : false,
            "room" : "HS 3",
            "theme" : "",
            "lvaId" : 1990,
            "title" : "326703",
            "start" : "2017-06-22T18:00:00+02:00",
            "end" : "2017-06-22T18:45:00+02:00"
        }, {
            "id" : 2309,
            "startDateTime" : "2017-04-27T18:00:00+02:00",
            "endDateTime" : "2017-04-27T18:45:00+02:00",
            "isExam" : false,
            "room" : "HS 3",
            "theme" : "",
            "lvaId" : 1990,
            "title" : "326703",
            "start" : "2017-04-27T18:00:00+02:00",
            "end" : "2017-04-27T18:45:00+02:00"
        }, {
            "id" : 2310,
            "startDateTime" : "2017-06-01T18:00:00+02:00",
            "endDateTime" : "2017-06-01T18:45:00+02:00",
            "isExam" : false,
            "room" : "HS 3",
            "theme" : "",
            "lvaId" : 1990,
            "title" : "326703",
            "start" : "2017-06-01T18:00:00+02:00",
            "end" : "2017-06-01T18:45:00+02:00"
        }, {
            "id" : 2311,
            "startDateTime" : "2017-03-09T17:00:00+01:00",
            "endDateTime" : "2017-03-09T17:45:00+01:00",
            "isExam" : false,
            "room" : "HS 3",
            "theme" : "",
            "lvaId" : 1990,
            "title" : "326703",
            "start" : "2017-03-09T17:00:00+01:00",
            "end" : "2017-03-09T17:45:00+01:00"
        }, {
            "id" : 2312,
            "startDateTime" : "2017-06-29T18:00:00+02:00",
            "endDateTime" : "2017-06-29T18:45:00+02:00",
            "isExam" : false,
            "room" : "HS 3",
            "theme" : "",
            "lvaId" : 1990,
            "title" : "326703",
            "start" : "2017-06-29T18:00:00+02:00",
            "end" : "2017-06-29T18:45:00+02:00"
        }, {
            "id" : 2313,
            "startDateTime" : "2017-03-23T17:00:00+01:00",
            "endDateTime" : "2017-03-23T17:45:00+01:00",
            "isExam" : false,
            "room" : "HS 3",
            "theme" : "",
            "lvaId" : 1990,
            "title" : "326703",
            "start" : "2017-03-23T17:00:00+01:00",
            "end" : "2017-03-23T17:45:00+01:00"
        }, {
            "id" : 2314,
            "startDateTime" : "2017-04-06T18:00:00+02:00",
            "endDateTime" : "2017-04-06T18:45:00+02:00",
            "isExam" : false,
            "room" : "HS 3",
            "theme" : "",
            "lvaId" : 1990,
            "title" : "326703",
            "start" : "2017-04-06T18:00:00+02:00",
            "end" : "2017-04-06T18:45:00+02:00"
        }, {
            "id" : 2315,
            "startDateTime" : "2017-05-11T18:00:00+02:00",
            "endDateTime" : "2017-05-11T18:45:00+02:00",
            "isExam" : false,
            "room" : "HS 3",
            "theme" : "",
            "lvaId" : 1990,
            "title" : "326703",
            "start" : "2017-05-11T18:00:00+02:00",
            "end" : "2017-05-11T18:45:00+02:00"
        }, {
            "id" : 2316,
            "startDateTime" : "2017-05-18T18:00:00+02:00",
            "endDateTime" : "2017-05-18T18:45:00+02:00",
            "isExam" : false,
            "room" : "HS 3",
            "theme" : "",
            "lvaId" : 1990,
            "title" : "326703",
            "start" : "2017-05-18T18:00:00+02:00",
            "end" : "2017-05-18T18:45:00+02:00"
        }, {
            "id" : 2317,
            "startDateTime" : "2017-03-30T18:00:00+02:00",
            "endDateTime" : "2017-03-30T18:45:00+02:00",
            "isExam" : false,
            "room" : "HS 3",
            "theme" : "",
            "lvaId" : 1990,
            "title" : "326703",
            "start" : "2017-03-30T18:00:00+02:00",
            "end" : "2017-03-30T18:45:00+02:00"
        }, {
            "id" : 2318,
            "startDateTime" : "2017-03-16T17:00:00+01:00",
            "endDateTime" : "2017-03-16T17:45:00+01:00",
            "isExam" : false,
            "room" : "HS 3",
            "theme" : "",
            "lvaId" : 1990,
            "title" : "326703",
            "start" : "2017-03-16T17:00:00+01:00",
            "end" : "2017-03-16T17:45:00+01:00"
        }, {
            "id" : 2320,
            "startDateTime" : "2017-03-23T17:00:00+01:00",
            "endDateTime" : "2017-03-23T17:45:00+01:00",
            "isExam" : false,
            "room" : "T 405",
            "theme" : "",
            "lvaId" : 1991,
            "title" : "326714",
            "start" : "2017-03-23T17:00:00+01:00",
            "end" : "2017-03-23T17:45:00+01:00"
        }, {
            "id" : 2321,
            "startDateTime" : "2017-04-27T18:00:00+02:00",
            "endDateTime" : "2017-04-27T18:45:00+02:00",
            "isExam" : false,
            "room" : "T 405",
            "theme" : "",
            "lvaId" : 1991,
            "title" : "326714",
            "start" : "2017-04-27T18:00:00+02:00",
            "end" : "2017-04-27T18:45:00+02:00"
        }, {
            "id" : 2322,
            "startDateTime" : "2017-06-08T18:00:00+02:00",
            "endDateTime" : "2017-06-08T18:45:00+02:00",
            "isExam" : false,
            "room" : "T 405",
            "theme" : "",
            "lvaId" : 1991,
            "title" : "326714",
            "start" : "2017-06-08T18:00:00+02:00",
            "end" : "2017-06-08T18:45:00+02:00"
        }, {
            "id" : 2323,
            "startDateTime" : "2017-03-09T17:00:00+01:00",
            "endDateTime" : "2017-03-09T17:45:00+01:00",
            "isExam" : false,
            "room" : "T 405",
            "theme" : "",
            "lvaId" : 1991,
            "title" : "326714",
            "start" : "2017-03-09T17:00:00+01:00",
            "end" : "2017-03-09T17:45:00+01:00"
        }, {
            "id" : 2324,
            "startDateTime" : "2017-04-06T18:00:00+02:00",
            "endDateTime" : "2017-04-06T18:45:00+02:00",
            "isExam" : false,
            "room" : "T 405",
            "theme" : "",
            "lvaId" : 1991,
            "title" : "326714",
            "start" : "2017-04-06T18:00:00+02:00",
            "end" : "2017-04-06T18:45:00+02:00"
        }, {
            "id" : 2325,
            "startDateTime" : "2017-05-11T18:00:00+02:00",
            "endDateTime" : "2017-05-11T18:45:00+02:00",
            "isExam" : false,
            "room" : "T 405",
            "theme" : "",
            "lvaId" : 1991,
            "title" : "326714",
            "start" : "2017-05-11T18:00:00+02:00",
            "end" : "2017-05-11T18:45:00+02:00"
        }, {
            "id" : 2326,
            "startDateTime" : "2017-06-01T18:00:00+02:00",
            "endDateTime" : "2017-06-01T18:45:00+02:00",
            "isExam" : false,
            "room" : "T 405",
            "theme" : "",
            "lvaId" : 1991,
            "title" : "326714",
            "start" : "2017-06-01T18:00:00+02:00",
            "end" : "2017-06-01T18:45:00+02:00"
        }, {
            "id" : 2327,
            "startDateTime" : "2017-05-18T18:00:00+02:00",
            "endDateTime" : "2017-05-18T18:45:00+02:00",
            "isExam" : false,
            "room" : "T 405",
            "theme" : "",
            "lvaId" : 1991,
            "title" : "326714",
            "start" : "2017-05-18T18:00:00+02:00",
            "end" : "2017-05-18T18:45:00+02:00"
        }, {
            "id" : 2328,
            "startDateTime" : "2017-06-29T18:00:00+02:00",
            "endDateTime" : "2017-06-29T18:45:00+02:00",
            "isExam" : false,
            "room" : "T 405",
            "theme" : "",
            "lvaId" : 1991,
            "title" : "326714",
            "start" : "2017-06-29T18:00:00+02:00",
            "end" : "2017-06-29T18:45:00+02:00"
        }, {
            "id" : 2329,
            "startDateTime" : "2017-03-16T17:00:00+01:00",
            "endDateTime" : "2017-03-16T17:45:00+01:00",
            "isExam" : false,
            "room" : "T 405",
            "theme" : "",
            "lvaId" : 1991,
            "title" : "326714",
            "start" : "2017-03-16T17:00:00+01:00",
            "end" : "2017-03-16T17:45:00+01:00"
        }, {
            "id" : 2330,
            "startDateTime" : "2017-03-30T18:00:00+02:00",
            "endDateTime" : "2017-03-30T18:45:00+02:00",
            "isExam" : false,
            "room" : "T 405",
            "theme" : "",
            "lvaId" : 1991,
            "title" : "326714",
            "start" : "2017-03-30T18:00:00+02:00",
            "end" : "2017-03-30T18:45:00+02:00"
        }, {
            "id" : 2319,
            "startDateTime" : "2017-06-22T18:00:00+02:00",
            "endDateTime" : "2017-06-22T18:45:00+02:00",
            "isExam" : false,
            "room" : "T 405",
            "theme" : "",
            "lvaId" : 1991,
            "title" : "326714",
            "start" : "2017-06-22T18:00:00+02:00",
            "end" : "2017-06-22T18:45:00+02:00"
        } ];
    }

    load(id) {
        this.lvaService.find(id).subscribe((lva) => {
            this.lva = lva;
            this.appointments = lva.appointments;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLvas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lvaListModification',
            (response) => this.load(this.lva.id)
        );
    }
}
