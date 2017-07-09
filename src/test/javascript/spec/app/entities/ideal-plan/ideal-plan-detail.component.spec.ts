/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CeappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { IdealPlanDetailComponent } from '../../../../../../main/webapp/app/entities/ideal-plan/ideal-plan-detail.component';
import { IdealPlanService } from '../../../../../../main/webapp/app/entities/ideal-plan/ideal-plan.service';
import { IdealPlan } from '../../../../../../main/webapp/app/entities/ideal-plan/ideal-plan.model';

describe('Component Tests', () => {

    describe('IdealPlan Management Detail Component', () => {
        let comp: IdealPlanDetailComponent;
        let fixture: ComponentFixture<IdealPlanDetailComponent>;
        let service: IdealPlanService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CeappTestModule],
                declarations: [IdealPlanDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    IdealPlanService,
                    JhiEventManager
                ]
            }).overrideTemplate(IdealPlanDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IdealPlanDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IdealPlanService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new IdealPlan(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.idealPlan).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
