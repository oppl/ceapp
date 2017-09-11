import { BaseEntity } from './../../shared';

export class CollisionLevelThree implements BaseEntity {
    constructor(
        public id?: number,
        public examCollision?: number,
        public instituteCollision?: number,
        public curriculumCollision?: number,
        public collisionValueAvg?: number,
        public collisionValueMax?: number,
        public colWS?: boolean,
        public colSS?: boolean,
        public countCollisionLvas?: number,
        public collisionLevelTwoId?: number,
        public collisionLevelFours?: BaseEntity[],
        public curriculumSubjectId?: number,
    ) {
        this.colWS = false;
        this.colSS = false;
    }
}
