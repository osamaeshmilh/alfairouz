import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISpecimenType, getSpecimenTypeIdentifier } from '../specimen-type.model';

export type EntityResponseType = HttpResponse<ISpecimenType>;
export type EntityArrayResponseType = HttpResponse<ISpecimenType[]>;

@Injectable({ providedIn: 'root' })
export class SpecimenTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/specimen-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(specimenType: ISpecimenType): Observable<EntityResponseType> {
    return this.http.post<ISpecimenType>(this.resourceUrl, specimenType, { observe: 'response' });
  }

  update(specimenType: ISpecimenType): Observable<EntityResponseType> {
    return this.http.put<ISpecimenType>(`${this.resourceUrl}/${getSpecimenTypeIdentifier(specimenType) as number}`, specimenType, {
      observe: 'response',
    });
  }

  partialUpdate(specimenType: ISpecimenType): Observable<EntityResponseType> {
    return this.http.patch<ISpecimenType>(`${this.resourceUrl}/${getSpecimenTypeIdentifier(specimenType) as number}`, specimenType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpecimenType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpecimenType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSpecimenTypeToCollectionIfMissing(
    specimenTypeCollection: ISpecimenType[],
    ...specimenTypesToCheck: (ISpecimenType | null | undefined)[]
  ): ISpecimenType[] {
    const specimenTypes: ISpecimenType[] = specimenTypesToCheck.filter(isPresent);
    if (specimenTypes.length > 0) {
      const specimenTypeCollectionIdentifiers = specimenTypeCollection.map(
        specimenTypeItem => getSpecimenTypeIdentifier(specimenTypeItem)!
      );
      const specimenTypesToAdd = specimenTypes.filter(specimenTypeItem => {
        const specimenTypeIdentifier = getSpecimenTypeIdentifier(specimenTypeItem);
        if (specimenTypeIdentifier == null || specimenTypeCollectionIdentifiers.includes(specimenTypeIdentifier)) {
          return false;
        }
        specimenTypeCollectionIdentifiers.push(specimenTypeIdentifier);
        return true;
      });
      return [...specimenTypesToAdd, ...specimenTypeCollection];
    }
    return specimenTypeCollection;
  }
}
