import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CollisionSummaryLva } from './collision-summary-lva.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CollisionSummaryLvaService {

    private resourceUrl = 'api/collision-summary-lvas';

    constructor(private http: Http) { }

    create(collisionSummaryLva: CollisionSummaryLva): Observable<CollisionSummaryLva> {
        const copy = this.convert(collisionSummaryLva);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(collisionSummaryLva: CollisionSummaryLva): Observable<CollisionSummaryLva> {
        const copy = this.convert(collisionSummaryLva);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CollisionSummaryLva> {
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

    private convert(collisionSummaryLva: CollisionSummaryLva): CollisionSummaryLva {
        const copy: CollisionSummaryLva = Object.assign({}, collisionSummaryLva);
        return copy;
    }
}
