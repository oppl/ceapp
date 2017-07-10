import { BaseEntity } from './../../shared';

export class Institute implements BaseEntity {
    constructor(
        public id?: number,
        public instituteId?: number,
        public instituteName?: string,
        public curricula?: BaseEntity[],
        public lvas?: BaseEntity[],
    ) {
    }
}
