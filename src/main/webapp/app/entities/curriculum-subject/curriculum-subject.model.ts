import { BaseEntity } from './../../shared';

const enum Semester {
    'WS',
    'SS'
}

export class CurriculumSubject implements BaseEntity {
    constructor(
        public id?: number,
        public year?: number,
        public semester?: Semester,
        public collCSSources?: BaseEntity[],
        public collCSTargets?: BaseEntity[],
        public lvas?: BaseEntity[],
        public curriculumId?: number,
        public subjectId?: number,
    ) {
    }
}
