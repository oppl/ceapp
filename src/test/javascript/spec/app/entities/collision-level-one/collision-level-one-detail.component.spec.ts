/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CeappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CollisionLevelOneDetailComponent } from '../../../../../../main/webapp/app/entities/collision-level-one/collision-level-one-detail.component';
import { CollisionLevelOneService } from '../../../../../../main/webapp/app/entities/collision-level-one/collision-level-one.service';
import { CollisionLevelOne } from '../../../../../../main/webapp/app/entities/collision-level-one/collision-level-one.model';

describe('Component Tests', () => {

    describe('CollisionLevelOne Management Detail Component', () => {
        let comp: CollisionLevelOneDetailComponent;
        let fixture: ComponentFixture<CollisionLevelOneDetailComponent>;
        let service: CollisionLevelOneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CeappTestModule],
                declarations: [CollisionLevelOneDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CollisionLevelOneService,
                    JhiEventManager
                ]
            }).overrideTemplate(CollisionLevelOneDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CollisionLevelOneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CollisionLevelOneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CollisionLevelOne(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.collisionLevelOne).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
