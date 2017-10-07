import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CollisionLevelOne } from './collision-level-one.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CollisionLevelOneService {

    private resourceUrl = 'api/collision-level-ones';
    private resourceSearchUrl = 'api/_search/collision-level-ones';

    constructor(private http: Http) { }

    create(collisionLevelOne: CollisionLevelOne): Observable<CollisionLevelOne> {
        const copy = this.convert(collisionLevelOne);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(collisionLevelOne: CollisionLevelOne): Observable<CollisionLevelOne> {
        const copy = this.convert(collisionLevelOne);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CollisionLevelOne> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    find2(cs: number, ip: number): Observable<CollisionLevelOne> {
        return this.http.get(`${this.resourceUrl}/${cs}/${ip}`).map((res: Response) => this.convertResponse(res));
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

    private convert(collisionLevelOne: CollisionLevelOne): CollisionLevelOne {
        const copy: CollisionLevelOne = Object.assign({}, collisionLevelOne);
        return copy;
    }
}
