import { BaseEntity } from './../../shared';

export class IdealPlanEntries implements BaseEntity {
    constructor(
        public id?: number,
        public winterSemesterDefault?: number,
        public summerSemesterDefault?: number,
        public optionalSubject?: boolean,
        public exclusive?: boolean,
        public subjectId?: number,
        public idealplanId?: number,
    ) {
        this.optionalSubject = false;
        this.exclusive = false;
    }
}
