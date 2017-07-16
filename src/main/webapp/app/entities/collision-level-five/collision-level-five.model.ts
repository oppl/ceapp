import { BaseEntity } from './../../shared';

export class CollisionLevelFive implements BaseEntity {
    constructor(
        public id?: number,
        public examCollision?: number,
        public collisionValue?: number,
        public collisionLevelFourId?: number,
        public sourceAppointmentId?: number,
        public targetAppointmentId?: number,
    ) {
    }
}
