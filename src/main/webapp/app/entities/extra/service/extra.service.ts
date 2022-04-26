import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IExtra, getExtraIdentifier } from '../extra.model';

export type EntityResponseType = HttpResponse<IExtra>;
export type EntityArrayResponseType = HttpResponse<IExtra[]>;

@Injectable({ providedIn: 'root' })
export class ExtraService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/extras');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(extra: IExtra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(extra);
    return this.http
      .post<IExtra>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(extra: IExtra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(extra);
    return this.http
      .put<IExtra>(`${this.resourceUrl}/${getExtraIdentifier(extra) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(extra: IExtra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(extra);
    return this.http
      .patch<IExtra>(`${this.resourceUrl}/${getExtraIdentifier(extra) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExtra>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExtra[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addExtraToCollectionIfMissing(extraCollection: IExtra[], ...extrasToCheck: (IExtra | null | undefined)[]): IExtra[] {
    const extras: IExtra[] = extrasToCheck.filter(isPresent);
    if (extras.length > 0) {
      const extraCollectionIdentifiers = extraCollection.map(extraItem => getExtraIdentifier(extraItem)!);
      const extrasToAdd = extras.filter(extraItem => {
        const extraIdentifier = getExtraIdentifier(extraItem);
        if (extraIdentifier == null || extraCollectionIdentifiers.includes(extraIdentifier)) {
          return false;
        }
        extraCollectionIdentifiers.push(extraIdentifier);
        return true;
      });
      return [...extrasToAdd, ...extraCollection];
    }
    return extraCollection;
  }

  protected convertDateFromClient(extra: IExtra): IExtra {
    return Object.assign({}, extra, {
      dateAt: extra.dateAt?.isValid() ? extra.dateAt.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAt = res.body.dateAt ? dayjs(res.body.dateAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((extra: IExtra) => {
        extra.dateAt = extra.dateAt ? dayjs(extra.dateAt) : undefined;
      });
    }
    return res;
  }
}
