/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CeappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CollisionLevelThreeDetailComponent } from '../../../../../../main/webapp/app/entities/collision-level-three/collision-level-three-detail.component';
import { CollisionLevelThreeService } from '../../../../../../main/webapp/app/entities/collision-level-three/collision-level-three.service';
import { CollisionLevelThree } from '../../../../../../main/webapp/app/entities/collision-level-three/collision-level-three.model';

describe('Component Tests', () => {

    describe('CollisionLevelThree Management Detail Component', () => {
        let comp: CollisionLevelThreeDetailComponent;
        let fixture: ComponentFixture<CollisionLevelThreeDetailComponent>;
        let service: CollisionLevelThreeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CeappTestModule],
                declarations: [CollisionLevelThreeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CollisionLevelThreeService,
                    JhiEventManager
                ]
            }).overrideTemplate(CollisionLevelThreeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CollisionLevelThreeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CollisionLevelThreeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CollisionLevelThree(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.collisionLevelThree).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
