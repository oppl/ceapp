import { BaseEntity } from './../../shared';

export class CollisionSummaryLva implements BaseEntity {
    constructor(
        public id?: number,
        public instituteCollision?: number,
        public collisionId?: number,
        public l1Id?: number,
        public l2Id?: number,
    ) {
    }
}
