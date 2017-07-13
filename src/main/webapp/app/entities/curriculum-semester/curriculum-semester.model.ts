import { BaseEntity } from './../../shared';

const enum Semester {
    'WS',
    'SS'
}

export class CurriculumSemester implements BaseEntity {
    constructor(
        public id?: number,
        public year?: number,
        public semester?: Semester,
        public curriculumSubjects?: BaseEntity[],
        public curriculumId?: number,
    ) {
    }
}
