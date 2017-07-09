/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CeappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { InstituteDetailComponent } from '../../../../../../main/webapp/app/entities/institute/institute-detail.component';
import { InstituteService } from '../../../../../../main/webapp/app/entities/institute/institute.service';
import { Institute } from '../../../../../../main/webapp/app/entities/institute/institute.model';

describe('Component Tests', () => {

    describe('Institute Management Detail Component', () => {
        let comp: InstituteDetailComponent;
        let fixture: ComponentFixture<InstituteDetailComponent>;
        let service: InstituteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CeappTestModule],
                declarations: [InstituteDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    InstituteService,
                    JhiEventManager
                ]
            }).overrideTemplate(InstituteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InstituteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InstituteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Institute(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.institute).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
