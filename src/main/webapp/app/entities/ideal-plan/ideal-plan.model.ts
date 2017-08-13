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
        public active?: boolean,
        public idealplanentries?: BaseEntity[],
        public curriculumId?: number,
    ) {
        this.active = false;
    }
}
