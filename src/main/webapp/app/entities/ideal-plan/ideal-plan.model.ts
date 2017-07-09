import { BaseEntity } from './../../shared';

const enum Semester {
    'WS',
    'SS'
}

export class IdealPlan implements BaseEntity {
    constructor(
        public id?: number,
        public year?: number,
        public semester?: Semester,
        public idealplanentries?: BaseEntity[],
        public collisionSummaryCS?: BaseEntity[],
        public curriculumId?: number,
    ) {
    }
}
