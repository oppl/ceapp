/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CeappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CollisionSummaryCSDetailComponent } from '../../../../../../main/webapp/app/entities/collision-summary-cs/collision-summary-cs-detail.component';
import { CollisionSummaryCSService } from '../../../../../../main/webapp/app/entities/collision-summary-cs/collision-summary-cs.service';
import { CollisionSummaryCS } from '../../../../../../main/webapp/app/entities/collision-summary-cs/collision-summary-cs.model';

describe('Component Tests', () => {

    describe('CollisionSummaryCS Management Detail Component', () => {
        let comp: CollisionSummaryCSDetailComponent;
        let fixture: ComponentFixture<CollisionSummaryCSDetailComponent>;
        let service: CollisionSummaryCSService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CeappTestModule],
                declarations: [CollisionSummaryCSDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CollisionSummaryCSService,
                    JhiEventManager
                ]
            }).overrideTemplate(CollisionSummaryCSDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CollisionSummaryCSDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CollisionSummaryCSService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CollisionSummaryCS(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.collisionSummaryCS).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
