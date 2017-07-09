/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CeappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LvaDetailComponent } from '../../../../../../main/webapp/app/entities/lva/lva-detail.component';
import { LvaService } from '../../../../../../main/webapp/app/entities/lva/lva.service';
import { Lva } from '../../../../../../main/webapp/app/entities/lva/lva.model';

describe('Component Tests', () => {

    describe('Lva Management Detail Component', () => {
        let comp: LvaDetailComponent;
        let fixture: ComponentFixture<LvaDetailComponent>;
        let service: LvaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CeappTestModule],
                declarations: [LvaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LvaService,
                    JhiEventManager
                ]
            }).overrideTemplate(LvaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LvaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LvaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Lva(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.lva).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
