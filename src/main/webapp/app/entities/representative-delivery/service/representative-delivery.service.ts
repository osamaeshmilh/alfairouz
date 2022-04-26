import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRepresentativeDelivery, getRepresentativeDeliveryIdentifier } from '../representative-delivery.model';

export type EntityResponseType = HttpResponse<IRepresentativeDelivery>;
export type EntityArrayResponseType = HttpResponse<IRepresentativeDelivery[]>;

@Injectable({ providedIn: 'root' })
export class RepresentativeDeliveryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/representative-deliveries');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(representativeDelivery: IRepresentativeDelivery): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(representativeDelivery);
    return this.http
      .post<IRepresentativeDelivery>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(representativeDelivery: IRepresentativeDelivery): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(representativeDelivery);
    return this.http
      .put<IRepresentativeDelivery>(`${this.resourceUrl}/${getRepresentativeDeliveryIdentifier(representativeDelivery) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(representativeDelivery: IRepresentativeDelivery): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(representativeDelivery);
    return this.http
      .patch<IRepresentativeDelivery>(
        `${this.resourceUrl}/${getRepresentativeDeliveryIdentifier(representativeDelivery) as number}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRepresentativeDelivery>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRepresentativeDelivery[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRepresentativeDeliveryToCollectionIfMissing(
    representativeDeliveryCollection: IRepresentativeDelivery[],
    ...representativeDeliveriesToCheck: (IRepresentativeDelivery | null | undefined)[]
  ): IRepresentativeDelivery[] {
    const representativeDeliveries: IRepresentativeDelivery[] = representativeDeliveriesToCheck.filter(isPresent);
    if (representativeDeliveries.length > 0) {
      const representativeDeliveryCollectionIdentifiers = representativeDeliveryCollection.map(
        representativeDeliveryItem => getRepresentativeDeliveryIdentifier(representativeDeliveryItem)!
      );
      const representativeDeliveriesToAdd = representativeDeliveries.filter(representativeDeliveryItem => {
        const representativeDeliveryIdentifier = getRepresentativeDeliveryIdentifier(representativeDeliveryItem);
        if (
          representativeDeliveryIdentifier == null ||
          representativeDeliveryCollectionIdentifiers.includes(representativeDeliveryIdentifier)
        ) {
          return false;
        }
        representativeDeliveryCollectionIdentifiers.push(representativeDeliveryIdentifier);
        return true;
      });
      return [...representativeDeliveriesToAdd, ...representativeDeliveryCollection];
    }
    return representativeDeliveryCollection;
  }

  protected convertDateFromClient(representativeDelivery: IRepresentativeDelivery): IRepresentativeDelivery {
    return Object.assign({}, representativeDelivery, {
      dateAt: representativeDelivery.dateAt?.isValid() ? representativeDelivery.dateAt.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((representativeDelivery: IRepresentativeDelivery) => {
        representativeDelivery.dateAt = representativeDelivery.dateAt ? dayjs(representativeDelivery.dateAt) : undefined;
      });
    }
    return res;
  }
}
