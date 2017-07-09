import { BaseEntity } from './../../shared';

const enum LvaType {
    'WEEKLY',
    'BLOCK'
}

const enum Semester {
    'WS',
    'SS'
}

export class Lva implements BaseEntity {
    constructor(
        public id?: number,
        public lvaNr?: string,
        public lvaType?: LvaType,
        public year?: number,
        public semester?: Semester,
        public appointments?: BaseEntity[],
        public csl1S?: BaseEntity[],
        public csl2S?: BaseEntity[],
        public subjectId?: number,
        public curriculumsubjects?: BaseEntity[],
    ) {
    }
}
