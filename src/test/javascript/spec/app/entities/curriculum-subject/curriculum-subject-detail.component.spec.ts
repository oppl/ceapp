/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CeappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CurriculumSubjectDetailComponent } from '../../../../../../main/webapp/app/entities/curriculum-subject/curriculum-subject-detail.component';
import { CurriculumSubjectService } from '../../../../../../main/webapp/app/entities/curriculum-subject/curriculum-subject.service';
import { CurriculumSubject } from '../../../../../../main/webapp/app/entities/curriculum-subject/curriculum-subject.model';

describe('Component Tests', () => {

    describe('CurriculumSubject Management Detail Component', () => {
        let comp: CurriculumSubjectDetailComponent;
        let fixture: ComponentFixture<CurriculumSubjectDetailComponent>;
        let service: CurriculumSubjectService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CeappTestModule],
                declarations: [CurriculumSubjectDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CurriculumSubjectService,
                    JhiEventManager
                ]
            }).overrideTemplate(CurriculumSubjectDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CurriculumSubjectDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CurriculumSubjectService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CurriculumSubject(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.curriculumSubject).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
