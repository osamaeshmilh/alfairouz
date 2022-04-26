import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICytology, getCytologyIdentifier } from '../cytology.model';

export type EntityResponseType = HttpResponse<ICytology>;
export type EntityArrayResponseType = HttpResponse<ICytology[]>;

@Injectable({ providedIn: 'root' })
export class CytologyService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cytologies');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cytology: ICytology): Observable<EntityResponseType> {
    return this.http.post<ICytology>(this.resourceUrl, cytology, { observe: 'response' });
  }

  update(cytology: ICytology): Observable<EntityResponseType> {
    return this.http.put<ICytology>(`${this.resourceUrl}/${getCytologyIdentifier(cytology) as number}`, cytology, { observe: 'response' });
  }

  partialUpdate(cytology: ICytology): Observable<EntityResponseType> {
    return this.http.patch<ICytology>(`${this.resourceUrl}/${getCytologyIdentifier(cytology) as number}`, cytology, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICytology>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICytology[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCytologyToCollectionIfMissing(cytologyCollection: ICytology[], ...cytologiesToCheck: (ICytology | null | undefined)[]): ICytology[] {
    const cytologies: ICytology[] = cytologiesToCheck.filter(isPresent);
    if (cytologies.length > 0) {
      const cytologyCollectionIdentifiers = cytologyCollection.map(cytologyItem => getCytologyIdentifier(cytologyItem)!);
      const cytologiesToAdd = cytologies.filter(cytologyItem => {
        const cytologyIdentifier = getCytologyIdentifier(cytologyItem);
        if (cytologyIdentifier == null || cytologyCollectionIdentifiers.includes(cytologyIdentifier)) {
          return false;
        }
        cytologyCollectionIdentifiers.push(cytologyIdentifier);
        return true;
      });
      return [...cytologiesToAdd, ...cytologyCollection];
    }
    return cytologyCollection;
  }
}
