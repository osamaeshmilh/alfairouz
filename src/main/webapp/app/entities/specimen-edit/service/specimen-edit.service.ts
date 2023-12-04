import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';

import {isPresent} from 'app/core/util/operators';
import {ApplicationConfigService} from 'app/core/config/application-config.service';
import {createRequestOption} from 'app/core/request/request-util';
import {ISpecimenEdit, getSpecimenEditIdentifier} from '../specimen-edit.model';

export type EntityResponseType = HttpResponse<ISpecimenEdit>;
export type EntityArrayResponseType = HttpResponse<ISpecimenEdit[]>;

@Injectable({providedIn: 'root'})
export class SpecimenEditService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/specimen-edits');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {
  }

  create(specimenEdit: ISpecimenEdit): Observable<EntityResponseType> {
    return this.http.post<ISpecimenEdit>(this.resourceUrl, specimenEdit, {observe: 'response'});
  }

  update(specimenEdit: ISpecimenEdit): Observable<EntityResponseType> {
    return this.http.put<ISpecimenEdit>(`${this.resourceUrl}/${getSpecimenEditIdentifier(specimenEdit) as number}`, specimenEdit, {
      observe: 'response',
    });
  }

  partialUpdate(specimenEdit: ISpecimenEdit): Observable<EntityResponseType> {
    return this.http.patch<ISpecimenEdit>(`${this.resourceUrl}/${getSpecimenEditIdentifier(specimenEdit) as number}`, specimenEdit, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpecimenEdit>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpecimenEdit[]>(this.resourceUrl, {params: options, observe: 'response'});
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  addSpecimenEditToCollectionIfMissing(
    specimenEditCollection: ISpecimenEdit[],
    ...specimenEditsToCheck: (ISpecimenEdit | null | undefined)[]
  ): ISpecimenEdit[] {
    const specimenEdits: ISpecimenEdit[] = specimenEditsToCheck.filter(isPresent);
    if (specimenEdits.length > 0) {
      const specimenEditCollectionIdentifiers = specimenEditCollection.map(
        specimenEditItem => getSpecimenEditIdentifier(specimenEditItem)!
      );
      const specimenEditsToAdd = specimenEdits.filter(specimenEditItem => {
        const specimenEditIdentifier = getSpecimenEditIdentifier(specimenEditItem);
        if (specimenEditIdentifier == null || specimenEditCollectionIdentifiers.includes(specimenEditIdentifier)) {
          return false;
        }
        specimenEditCollectionIdentifiers.push(specimenEditIdentifier);
        return true;
      });
      return [...specimenEditsToAdd, ...specimenEditCollection];
    }
    return specimenEditCollection;
  }
}
