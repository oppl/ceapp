import { BaseEntity } from './../../shared';

export class CollisionLevelFour implements BaseEntity {
    constructor(
        public id?: number,
        public examCollision?: number,
        public collisionLevelThreeId?: number,
        public collisionLevelFives?: BaseEntity[],
        public lvaId?: number,
    ) {
    }
}
