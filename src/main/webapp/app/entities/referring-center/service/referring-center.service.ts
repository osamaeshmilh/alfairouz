import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import {IReferringCenter, getReferringCenterIdentifier} from '../referring-center.model';
import {IReferringCenterPrice} from "../../referring-center-price/referring-center-price.model";
import { IReferringCenterLedgerEntry, IReferringCenterLedgerSummary } from '../referring-center-ledger.model';

export type EntityResponseType = HttpResponse<IReferringCenter>;
export type EntityArrayResponseType = HttpResponse<IReferringCenter[]>;
export type LedgerResponseType = HttpResponse<IReferringCenterLedgerSummary>;

@Injectable({ providedIn: 'root' })
export class ReferringCenterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/referring-centers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(referringCenter: IReferringCenter): Observable<EntityResponseType> {
    return this.http.post<IReferringCenter>(this.resourceUrl, referringCenter, { observe: 'response' });
  }

  update(referringCenter: IReferringCenter): Observable<EntityResponseType> {
    return this.http.put<IReferringCenter>(
      `${this.resourceUrl}/${getReferringCenterIdentifier(referringCenter) as number}`,
      referringCenter,
      { observe: 'response' }
    );
  }

  partialUpdate(referringCenter: IReferringCenter): Observable<EntityResponseType> {
    return this.http.patch<IReferringCenter>(
      `${this.resourceUrl}/${getReferringCenterIdentifier(referringCenter) as number}`,
      referringCenter,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReferringCenter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReferringCenter[]>(this.resourceUrl, {params: options, observe: 'response'});
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  count(req?: any): Observable<HttpResponse<any>> {
    const options = createRequestOption(req);
    return this.http.get<number>(this.resourceUrl + '/count', {params: options, observe: 'response'});
  }

  resetPrices(referringCenterId: number): Observable<EntityResponseType> {
    return this.http.get<IReferringCenterPrice>(`${this.resourceUrl}/reset-price/${referringCenterId}`, {observe: 'response'});
  }

  getLedger(referringCenterId: number): Observable<LedgerResponseType> {
    return this.http.get<IReferringCenterLedgerSummary>(`${this.resourceUrl}/${referringCenterId}/ledger`, { observe: 'response' });
  }

  createOpeningBalance(referringCenterId: number, entry: IReferringCenterLedgerEntry): Observable<LedgerResponseType> {
    return this.http.post<IReferringCenterLedgerSummary>(`${this.resourceUrl}/${referringCenterId}/ledger/opening-balance`, entry, {
      observe: 'response',
    });
  }

  createSettlementPayment(referringCenterId: number, entry: IReferringCenterLedgerEntry): Observable<LedgerResponseType> {
    return this.http.post<IReferringCenterLedgerSummary>(`${this.resourceUrl}/${referringCenterId}/ledger/settlements`, entry, {
      observe: 'response',
    });
  }

  getProofDownloadUrl(fileName: string): string {
    return this.applicationConfigService.getEndpointFor(`api/public/file/download/${encodeURIComponent(fileName)}`);
  }

  addReferringCenterToCollectionIfMissing(
    referringCenterCollection: IReferringCenter[],
    ...referringCentersToCheck: (IReferringCenter | null | undefined)[]
  ): IReferringCenter[] {
    const referringCenters: IReferringCenter[] = referringCentersToCheck.filter(isPresent);
    if (referringCenters.length > 0) {
      const referringCenterCollectionIdentifiers = referringCenterCollection.map(
        referringCenterItem => getReferringCenterIdentifier(referringCenterItem)!
      );
      const referringCentersToAdd = referringCenters.filter(referringCenterItem => {
        const referringCenterIdentifier = getReferringCenterIdentifier(referringCenterItem);
        if (referringCenterIdentifier == null || referringCenterCollectionIdentifiers.includes(referringCenterIdentifier)) {
          return false;
        }
        referringCenterCollectionIdentifiers.push(referringCenterIdentifier);
        return true;
      });
      return [...referringCentersToAdd, ...referringCenterCollection];
    }
    return referringCenterCollection;
  }

}
