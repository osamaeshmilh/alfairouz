import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';

import {isPresent} from 'app/core/util/operators';
import {ApplicationConfigService} from 'app/core/config/application-config.service';
import {createRequestOption} from 'app/core/request/request-util';
import {IReferringCenterPrice, getReferringCenterPriceIdentifier} from '../referring-center-price.model';

export type EntityResponseType = HttpResponse<IReferringCenterPrice>;
export type EntityArrayResponseType = HttpResponse<IReferringCenterPrice[]>;

@Injectable({providedIn: 'root'})
export class ReferringCenterPriceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/referring-center-prices');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {
  }

  create(referringCenterPrice: IReferringCenterPrice): Observable<EntityResponseType> {
    return this.http.post<IReferringCenterPrice>(this.resourceUrl, referringCenterPrice, {observe: 'response'});
  }

  update(referringCenterPrice: IReferringCenterPrice): Observable<EntityResponseType> {
    return this.http.put<IReferringCenterPrice>(
      `${this.resourceUrl}/${getReferringCenterPriceIdentifier(referringCenterPrice) as number}`,
      referringCenterPrice,
      {observe: 'response'}
    );
  }

  partialUpdate(referringCenterPrice: IReferringCenterPrice): Observable<EntityResponseType> {
    return this.http.patch<IReferringCenterPrice>(
      `${this.resourceUrl}/${getReferringCenterPriceIdentifier(referringCenterPrice) as number}`,
      referringCenterPrice,
      {observe: 'response'}
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReferringCenterPrice>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReferringCenterPrice[]>(this.resourceUrl, {params: options, observe: 'response'});
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  addReferringCenterPriceToCollectionIfMissing(
    referringCenterPriceCollection: IReferringCenterPrice[],
    ...referringCenterPricesToCheck: (IReferringCenterPrice | null | undefined)[]
  ): IReferringCenterPrice[] {
    const referringCenterPrices: IReferringCenterPrice[] = referringCenterPricesToCheck.filter(isPresent);
    if (referringCenterPrices.length > 0) {
      const referringCenterPriceCollectionIdentifiers = referringCenterPriceCollection.map(
        referringCenterPriceItem => getReferringCenterPriceIdentifier(referringCenterPriceItem)!
      );
      const referringCenterPricesToAdd = referringCenterPrices.filter(referringCenterPriceItem => {
        const referringCenterPriceIdentifier = getReferringCenterPriceIdentifier(referringCenterPriceItem);
        if (referringCenterPriceIdentifier == null || referringCenterPriceCollectionIdentifiers.includes(referringCenterPriceIdentifier)) {
          return false;
        }
        referringCenterPriceCollectionIdentifiers.push(referringCenterPriceIdentifier);
        return true;
      });
      return [...referringCenterPricesToAdd, ...referringCenterPriceCollection];
    }
    return referringCenterPriceCollection;
  }
}
