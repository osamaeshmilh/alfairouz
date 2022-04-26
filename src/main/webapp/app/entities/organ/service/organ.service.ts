import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrgan, getOrganIdentifier } from '../organ.model';

export type EntityResponseType = HttpResponse<IOrgan>;
export type EntityArrayResponseType = HttpResponse<IOrgan[]>;

@Injectable({ providedIn: 'root' })
export class OrganService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/organs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(organ: IOrgan): Observable<EntityResponseType> {
    return this.http.post<IOrgan>(this.resourceUrl, organ, { observe: 'response' });
  }

  update(organ: IOrgan): Observable<EntityResponseType> {
    return this.http.put<IOrgan>(`${this.resourceUrl}/${getOrganIdentifier(organ) as number}`, organ, { observe: 'response' });
  }

  partialUpdate(organ: IOrgan): Observable<EntityResponseType> {
    return this.http.patch<IOrgan>(`${this.resourceUrl}/${getOrganIdentifier(organ) as number}`, organ, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrgan>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrgan[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addOrganToCollectionIfMissing(organCollection: IOrgan[], ...organsToCheck: (IOrgan | null | undefined)[]): IOrgan[] {
    const organs: IOrgan[] = organsToCheck.filter(isPresent);
    if (organs.length > 0) {
      const organCollectionIdentifiers = organCollection.map(organItem => getOrganIdentifier(organItem)!);
      const organsToAdd = organs.filter(organItem => {
        const organIdentifier = getOrganIdentifier(organItem);
        if (organIdentifier == null || organCollectionIdentifiers.includes(organIdentifier)) {
          return false;
        }
        organCollectionIdentifiers.push(organIdentifier);
        return true;
      });
      return [...organsToAdd, ...organCollection];
    }
    return organCollection;
  }
}
