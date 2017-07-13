/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CeappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CurriculumSemesterDetailComponent } from '../../../../../../main/webapp/app/entities/curriculum-semester/curriculum-semester-detail.component';
import { CurriculumSemesterService } from '../../../../../../main/webapp/app/entities/curriculum-semester/curriculum-semester.service';
import { CurriculumSemester } from '../../../../../../main/webapp/app/entities/curriculum-semester/curriculum-semester.model';

describe('Component Tests', () => {

    describe('CurriculumSemester Management Detail Component', () => {
        let comp: CurriculumSemesterDetailComponent;
        let fixture: ComponentFixture<CurriculumSemesterDetailComponent>;
        let service: CurriculumSemesterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CeappTestModule],
                declarations: [CurriculumSemesterDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CurriculumSemesterService,
                    JhiEventManager
                ]
            }).overrideTemplate(CurriculumSemesterDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CurriculumSemesterDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CurriculumSemesterService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CurriculumSemester(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.curriculumSemester).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
