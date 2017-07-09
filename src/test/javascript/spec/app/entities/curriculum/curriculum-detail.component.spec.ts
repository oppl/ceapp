/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CeappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CurriculumDetailComponent } from '../../../../../../main/webapp/app/entities/curriculum/curriculum-detail.component';
import { CurriculumService } from '../../../../../../main/webapp/app/entities/curriculum/curriculum.service';
import { Curriculum } from '../../../../../../main/webapp/app/entities/curriculum/curriculum.model';

describe('Component Tests', () => {

    describe('Curriculum Management Detail Component', () => {
        let comp: CurriculumDetailComponent;
        let fixture: ComponentFixture<CurriculumDetailComponent>;
        let service: CurriculumService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CeappTestModule],
                declarations: [CurriculumDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CurriculumService,
                    JhiEventManager
                ]
            }).overrideTemplate(CurriculumDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CurriculumDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CurriculumService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Curriculum(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.curriculum).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
