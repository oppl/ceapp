import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Appointment } from './appointment.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AppointmentService {

    private resourceUrl = 'api/appointments';
    private resourceSearchUrl = 'api/_search/appointments';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(appointment: Appointment): Observable<Appointment> {
        const copy = this.convert(appointment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(appointment: Appointment): Observable<Appointment> {
        const copy = this.convert(appointment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Appointment> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.startDateTime = this.dateUtils
            .convertDateTimeFromServer(entity.startDateTime);
        entity.endDateTime = this.dateUtils
            .convertDateTimeFromServer(entity.endDateTime);
    }

    private convert(appointment: Appointment): Appointment {
        const copy: Appointment = Object.assign({}, appointment);

        copy.startDateTime = this.dateUtils.toDate(appointment.startDateTime);

        copy.endDateTime = this.dateUtils.toDate(appointment.endDateTime);
        return copy;
    }
}
