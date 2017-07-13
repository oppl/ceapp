import { BaseEntity } from './../../shared';

export class Curriculum implements BaseEntity {
    constructor(
        public id?: number,
        public curId?: number,
        public curName?: string,
        public idealplanentries?: BaseEntity[],
        public institutes?: BaseEntity[],
        public curriculumSemesters?: BaseEntity[],
    ) {
    }
}
