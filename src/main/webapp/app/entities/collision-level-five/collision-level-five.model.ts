import { BaseEntity } from './../../shared';

export class CollisionLevelFive implements BaseEntity {
    constructor(
        public id?: number,
        public examCollision?: number,
        public collisionLevelFourId?: number,
    ) {
    }
}
