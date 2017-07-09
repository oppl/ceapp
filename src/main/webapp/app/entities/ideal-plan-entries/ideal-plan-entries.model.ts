import { BaseEntity } from './../../shared';

export class IdealPlanEntries implements BaseEntity {
    constructor(
        public id?: number,
        public winterSemesterDefault?: number,
        public summerSemesterDefault?: number,
        public subjectId?: number,
        public idealplanId?: number,
    ) {
    }
}
