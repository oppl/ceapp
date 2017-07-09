/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CeappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CollisionSummaryLvaDetailComponent } from '../../../../../../main/webapp/app/entities/collision-summary-lva/collision-summary-lva-detail.component';
import { CollisionSummaryLvaService } from '../../../../../../main/webapp/app/entities/collision-summary-lva/collision-summary-lva.service';
import { CollisionSummaryLva } from '../../../../../../main/webapp/app/entities/collision-summary-lva/collision-summary-lva.model';

describe('Component Tests', () => {

    describe('CollisionSummaryLva Management Detail Component', () => {
        let comp: CollisionSummaryLvaDetailComponent;
        let fixture: ComponentFixture<CollisionSummaryLvaDetailComponent>;
        let service: CollisionSummaryLvaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CeappTestModule],
                declarations: [CollisionSummaryLvaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CollisionSummaryLvaService,
                    JhiEventManager
                ]
            }).overrideTemplate(CollisionSummaryLvaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CollisionSummaryLvaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CollisionSummaryLvaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CollisionSummaryLva(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.collisionSummaryLva).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
