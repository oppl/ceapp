import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CurriculumSubject } from './curriculum-subject.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CurriculumSubjectService {

    private resourceUrl = 'api/curriculum-subjects';

    constructor(private http: Http) { }

    create(curriculumSubject: CurriculumSubject): Observable<CurriculumSubject> {
        const copy = this.convert(curriculumSubject);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(curriculumSubject: CurriculumSubject): Observable<CurriculumSubject> {
        const copy = this.convert(curriculumSubject);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CurriculumSubject> {
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

    private convert(curriculumSubject: CurriculumSubject): CurriculumSubject {
        const copy: CurriculumSubject = Object.assign({}, curriculumSubject);
        return copy;
    }
}
