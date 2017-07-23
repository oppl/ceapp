import { BaseEntity } from './../../shared';

export class CollisionLevelTwo implements BaseEntity {
    constructor(
        public id?: number,
        public examCollision?: number,
        public instituteCollision?: number,
        public curriculumCollision?: number,
        public collisionValueAvg?: number,
        public collisionValueMax?: number,
        public colWS?: boolean,
        public colSS?: boolean,
        public collisionLevelOneId?: number,
        public collisionLevelThrees?: BaseEntity[],
        public lvaId?: number,
    ) {
        this.colWS = false;
        this.colSS = false;
    }
}
