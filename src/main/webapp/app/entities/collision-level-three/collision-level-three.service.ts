import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CollisionLevelThree } from './collision-level-three.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CollisionLevelThreeService {

    private resourceUrl = 'api/collision-level-threes';
    private resourceSearchUrl = 'api/_search/collision-level-threes';

    constructor(private http: Http) { }

    create(collisionLevelThree: CollisionLevelThree): Observable<CollisionLevelThree> {
        const copy = this.convert(collisionLevelThree);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(collisionLevelThree: CollisionLevelThree): Observable<CollisionLevelThree> {
        const copy = this.convert(collisionLevelThree);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CollisionLevelThree> {
        return this.http.get(`${this.resourceUrl}/filtered/${id}`).map((res: Response) => this.convertResponse(res));
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

    private convert(collisionLevelThree: CollisionLevelThree): CollisionLevelThree {
        const copy: CollisionLevelThree = Object.assign({}, collisionLevelThree);
        return copy;
    }
}
