import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CollisionLevelFour } from './collision-level-four.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CollisionLevelFourService {

    private resourceUrl = 'api/collision-level-fours';
    private resourceSearchUrl = 'api/_search/collision-level-fours';

    constructor(private http: Http) { }

    create(collisionLevelFour: CollisionLevelFour): Observable<CollisionLevelFour> {
        const copy = this.convert(collisionLevelFour);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(collisionLevelFour: CollisionLevelFour): Observable<CollisionLevelFour> {
        const copy = this.convert(collisionLevelFour);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CollisionLevelFour> {
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

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(collisionLevelFour: CollisionLevelFour): CollisionLevelFour {
        const copy: CollisionLevelFour = Object.assign({}, collisionLevelFour);
        return copy;
    }
}
