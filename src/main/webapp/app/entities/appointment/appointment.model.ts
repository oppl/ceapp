import { BaseEntity } from './../../shared';

export class Appointment implements BaseEntity {
    constructor(
        public id?: number,
        public startDateTime?: any,
        public endDateTime?: any,
        public isExam?: boolean,
        public room?: string,
        public lvaId?: number,
    ) {
        this.isExam = false;
    }
}
