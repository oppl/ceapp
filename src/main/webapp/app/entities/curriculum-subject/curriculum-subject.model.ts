import { BaseEntity } from './../../shared';

export class CurriculumSubject implements BaseEntity {
    constructor(
        public id?: number,
        public lvas?: BaseEntity[],
        public subjectId?: number,
        public curriculumSemesterId?: number,
        public collisionLevelOnes?: BaseEntity[],
    ) {
    }
}
