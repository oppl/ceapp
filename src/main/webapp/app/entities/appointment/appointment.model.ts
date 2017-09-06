import { BaseEntity } from './../../shared';

export class Appointment implements BaseEntity {
    constructor(
        public id?: number,
        public startDateTime?: any,
        public endDateTime?: any,
        public isExam?: boolean,
        public room?: string,
        public theme?: string,
        public title?: string,
        public lvaId?: number,
    ) {
        this.isExam = false;
    }
}
