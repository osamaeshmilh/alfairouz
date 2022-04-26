import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPapSmearSale, getPapSmearSaleIdentifier } from '../pap-smear-sale.model';

export type EntityResponseType = HttpResponse<IPapSmearSale>;
export type EntityArrayResponseType = HttpResponse<IPapSmearSale[]>;

@Injectable({ providedIn: 'root' })
export class PapSmearSaleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pap-smear-sales');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(papSmearSale: IPapSmearSale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(papSmearSale);
    return this.http
      .post<IPapSmearSale>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(papSmearSale: IPapSmearSale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(papSmearSale);
    return this.http
      .put<IPapSmearSale>(`${this.resourceUrl}/${getPapSmearSaleIdentifier(papSmearSale) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(papSmearSale: IPapSmearSale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(papSmearSale);
    return this.http
      .patch<IPapSmearSale>(`${this.resourceUrl}/${getPapSmearSaleIdentifier(papSmearSale) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPapSmearSale>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPapSmearSale[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPapSmearSaleToCollectionIfMissing(
    papSmearSaleCollection: IPapSmearSale[],
    ...papSmearSalesToCheck: (IPapSmearSale | null | undefined)[]
  ): IPapSmearSale[] {
    const papSmearSales: IPapSmearSale[] = papSmearSalesToCheck.filter(isPresent);
    if (papSmearSales.length > 0) {
      const papSmearSaleCollectionIdentifiers = papSmearSaleCollection.map(
        papSmearSaleItem => getPapSmearSaleIdentifier(papSmearSaleItem)!
      );
      const papSmearSalesToAdd = papSmearSales.filter(papSmearSaleItem => {
        const papSmearSaleIdentifier = getPapSmearSaleIdentifier(papSmearSaleItem);
        if (papSmearSaleIdentifier == null || papSmearSaleCollectionIdentifiers.includes(papSmearSaleIdentifier)) {
          return false;
        }
        papSmearSaleCollectionIdentifiers.push(papSmearSaleIdentifier);
        return true;
      });
      return [...papSmearSalesToAdd, ...papSmearSaleCollection];
    }
    return papSmearSaleCollection;
  }

  protected convertDateFromClient(papSmearSale: IPapSmearSale): IPapSmearSale {
    return Object.assign({}, papSmearSale, {
      dateAt: papSmearSale.dateAt?.isValid() ? papSmearSale.dateAt.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((papSmearSale: IPapSmearSale) => {
        papSmearSale.dateAt = papSmearSale.dateAt ? dayjs(papSmearSale.dateAt) : undefined;
      });
    }
    return res;
  }
}
