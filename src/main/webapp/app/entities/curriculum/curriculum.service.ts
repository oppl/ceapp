import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Curriculum } from './curriculum.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CurriculumService {

    private resourceUrl = 'api/curricula';

    constructor(private http: Http) { }

    create(curriculum: Curriculum): Observable<Curriculum> {
        const copy = this.convert(curriculum);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(curriculum: Curriculum): Observable<Curriculum> {
        const copy = this.convert(curriculum);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Curriculum> {
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

    private convert(curriculum: Curriculum): Curriculum {
        const copy: Curriculum = Object.assign({}, curriculum);
        return copy;
    }
}
