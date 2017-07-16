import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CollisionLevelTwo } from './collision-level-two.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CollisionLevelTwoService {

    private resourceUrl = 'api/collision-level-twos';
    private resourceSearchUrl = 'api/_search/collision-level-twos';

    constructor(private http: Http) { }

    create(collisionLevelTwo: CollisionLevelTwo): Observable<CollisionLevelTwo> {
        const copy = this.convert(collisionLevelTwo);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(collisionLevelTwo: CollisionLevelTwo): Observable<CollisionLevelTwo> {
        const copy = this.convert(collisionLevelTwo);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CollisionLevelTwo> {
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

    private convert(collisionLevelTwo: CollisionLevelTwo): CollisionLevelTwo {
        const copy: CollisionLevelTwo = Object.assign({}, collisionLevelTwo);
        return copy;
    }
}
