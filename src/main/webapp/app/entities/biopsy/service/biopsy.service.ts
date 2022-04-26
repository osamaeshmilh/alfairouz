import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBiopsy, getBiopsyIdentifier } from '../biopsy.model';

export type EntityResponseType = HttpResponse<IBiopsy>;
export type EntityArrayResponseType = HttpResponse<IBiopsy[]>;

@Injectable({ providedIn: 'root' })
export class BiopsyService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/biopsies');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(biopsy: IBiopsy): Observable<EntityResponseType> {
    return this.http.post<IBiopsy>(this.resourceUrl, biopsy, { observe: 'response' });
  }

  update(biopsy: IBiopsy): Observable<EntityResponseType> {
    return this.http.put<IBiopsy>(`${this.resourceUrl}/${getBiopsyIdentifier(biopsy) as number}`, biopsy, { observe: 'response' });
  }

  partialUpdate(biopsy: IBiopsy): Observable<EntityResponseType> {
    return this.http.patch<IBiopsy>(`${this.resourceUrl}/${getBiopsyIdentifier(biopsy) as number}`, biopsy, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBiopsy>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBiopsy[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBiopsyToCollectionIfMissing(biopsyCollection: IBiopsy[], ...biopsiesToCheck: (IBiopsy | null | undefined)[]): IBiopsy[] {
    const biopsies: IBiopsy[] = biopsiesToCheck.filter(isPresent);
    if (biopsies.length > 0) {
      const biopsyCollectionIdentifiers = biopsyCollection.map(biopsyItem => getBiopsyIdentifier(biopsyItem)!);
      const biopsiesToAdd = biopsies.filter(biopsyItem => {
        const biopsyIdentifier = getBiopsyIdentifier(biopsyItem);
        if (biopsyIdentifier == null || biopsyCollectionIdentifiers.includes(biopsyIdentifier)) {
          return false;
        }
        biopsyCollectionIdentifiers.push(biopsyIdentifier);
        return true;
      });
      return [...biopsiesToAdd, ...biopsyCollection];
    }
    return biopsyCollection;
  }
}
