import { BaseEntity } from './../../shared';

export class CollisionSummaryCS implements BaseEntity {
    constructor(
        public id?: number,
        public instituteCollision?: number,
        public collisionsummarylvas?: BaseEntity[],
        public csSourceId?: number,
        public csTargetId?: number,
        public idealPlanId?: number,
    ) {
    }
}
