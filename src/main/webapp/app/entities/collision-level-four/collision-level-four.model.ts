import { BaseEntity } from './../../shared';

const enum CollisionType {
    'INST_INST',
    'WIN_WIN',
    'WIN_EZK',
    'EZK_EZK',
    'WIN_ZK',
    'EZK_ZK',
    'ZK_ZK'
}

export class CollisionLevelFour implements BaseEntity {
    constructor(
        public id?: number,
        public examCollision?: number,
        public instituteCollision?: number,
        public curriculumCollision?: number,
        public collisionType?: CollisionType,
        public collisionLevelThreeId?: number,
        public collisionLevelFives?: BaseEntity[],
        public lvaId?: number,
    ) {
    }
}
