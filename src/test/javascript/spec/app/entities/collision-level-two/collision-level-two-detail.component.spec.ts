/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CeappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CollisionLevelTwoDetailComponent } from '../../../../../../main/webapp/app/entities/collision-level-two/collision-level-two-detail.component';
import { CollisionLevelTwoService } from '../../../../../../main/webapp/app/entities/collision-level-two/collision-level-two.service';
import { CollisionLevelTwo } from '../../../../../../main/webapp/app/entities/collision-level-two/collision-level-two.model';

describe('Component Tests', () => {

    describe('CollisionLevelTwo Management Detail Component', () => {
        let comp: CollisionLevelTwoDetailComponent;
        let fixture: ComponentFixture<CollisionLevelTwoDetailComponent>;
        let service: CollisionLevelTwoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CeappTestModule],
                declarations: [CollisionLevelTwoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CollisionLevelTwoService,
                    JhiEventManager
                ]
            }).overrideTemplate(CollisionLevelTwoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CollisionLevelTwoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CollisionLevelTwoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CollisionLevelTwo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.collisionLevelTwo).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
