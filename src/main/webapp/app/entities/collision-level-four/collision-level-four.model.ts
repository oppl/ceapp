import { BaseEntity } from './../../shared';

export class CollisionLevelFour implements BaseEntity {
    constructor(
        public id?: number,
        public examCollision?: number,
        public collisionLevelThreeId?: number,
        public sourceAppointments?: BaseEntity[],
        public targetAppointments?: BaseEntity[],
    ) {
    }
}
