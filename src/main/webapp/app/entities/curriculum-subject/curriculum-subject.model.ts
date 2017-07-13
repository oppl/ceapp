import { BaseEntity } from './../../shared';

export class CurriculumSubject implements BaseEntity {
    constructor(
        public id?: number,
        public collCSSources?: BaseEntity[],
        public collCSTargets?: BaseEntity[],
        public lvas?: BaseEntity[],
        public subjectId?: number,
        public curriculumSemesterId?: number,
    ) {
    }
}
