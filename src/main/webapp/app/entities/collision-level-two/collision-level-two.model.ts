import { BaseEntity } from './../../shared';

export class CollisionLevelTwo implements BaseEntity {
    constructor(
        public id?: number,
        public examCollision?: number,
        public collisionLevelOneId?: number,
    ) {
    }
}
