import { BaseEntity } from './../../shared';

export class CollisionLevelThree implements BaseEntity {
    constructor(
        public id?: number,
        public examCollision?: number,
        public collisionLevelTwoId?: number,
    ) {
    }
}
