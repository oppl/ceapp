import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Lva } from './lva.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LvaService {

    private resourceUrl = 'api/lvas';

    constructor(private http: Http) { }

    create(lva: Lva): Observable<Lva> {
        const copy = this.convert(lva);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(lva: Lva): Observable<Lva> {
        const copy = this.convert(lva);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Lva> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(lva: Lva): Lva {
        const copy: Lva = Object.assign({}, lva);
        return copy;
    }
}
