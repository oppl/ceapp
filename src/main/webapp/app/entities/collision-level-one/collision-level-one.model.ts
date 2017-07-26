import { BaseEntity } from './../../shared';

export class CollisionLevelOne implements BaseEntity {
    constructor(
        public id?: number,
        public examCollision?: number,
        public instituteCollision?: number,
        public curriculumCollision?: number,
        public collisionValueAvg?: number,
        public collisionValueMax?: number,
        public colWS?: boolean,
        public colSS?: boolean,
        public curriculumSubjectId?: number,
        public collisionLevelTwos?: BaseEntity[],
        public idealPlanId?: number,
        public instituteId?: number,
    ) {
        this.colWS = false;
        this.colSS = false;
    }
}
