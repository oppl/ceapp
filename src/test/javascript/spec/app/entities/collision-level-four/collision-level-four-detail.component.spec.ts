/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CeappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CollisionLevelFourDetailComponent } from '../../../../../../main/webapp/app/entities/collision-level-four/collision-level-four-detail.component';
import { CollisionLevelFourService } from '../../../../../../main/webapp/app/entities/collision-level-four/collision-level-four.service';
import { CollisionLevelFour } from '../../../../../../main/webapp/app/entities/collision-level-four/collision-level-four.model';

describe('Component Tests', () => {

    describe('CollisionLevelFour Management Detail Component', () => {
        let comp: CollisionLevelFourDetailComponent;
        let fixture: ComponentFixture<CollisionLevelFourDetailComponent>;
        let service: CollisionLevelFourService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CeappTestModule],
                declarations: [CollisionLevelFourDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CollisionLevelFourService,
                    JhiEventManager
                ]
            }).overrideTemplate(CollisionLevelFourDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CollisionLevelFourDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CollisionLevelFourService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CollisionLevelFour(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.collisionLevelFour).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
