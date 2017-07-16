import { BaseEntity } from './../../shared';

export class CollisionLevelOne implements BaseEntity {
    constructor(
        public id?: number,
        public examCollision?: number,
        public curriculumSubjectId?: number,
        public collisionLevelTwos?: BaseEntity[],
    ) {
    }
}
