import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISpecimen, getSpecimenIdentifier } from '../specimen.model';

export type EntityResponseType = HttpResponse<ISpecimen>;
export type EntityArrayResponseType = HttpResponse<ISpecimen[]>;

@Injectable({ providedIn: 'root' })
export class SpecimenService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/specimen');
  protected resourcePublicUrl = this.applicationConfigService.getEndpointFor('api/public/specimen');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(specimen: ISpecimen): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(specimen);
    return this.http
      .post<ISpecimen>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(specimen: ISpecimen): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(specimen);
    return this.http
      .put<ISpecimen>(`${this.resourceUrl}/${getSpecimenIdentifier(specimen) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(specimen: ISpecimen): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(specimen);
    return this.http
      .patch<ISpecimen>(`${this.resourceUrl}/${getSpecimenIdentifier(specimen) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISpecimen>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISpecimen[]>(this.resourceUrl, {params: options, observe: 'response'})
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }


  findPublic(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISpecimen>(`${this.resourcePublicUrl}/${id}`, {observe: 'response'})
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  addSpecimenToCollectionIfMissing(specimenCollection: ISpecimen[], ...specimenToCheck: (ISpecimen | null | undefined)[]): ISpecimen[] {
    const specimen: ISpecimen[] = specimenToCheck.filter(isPresent);
    if (specimen.length > 0) {
      const specimenCollectionIdentifiers = specimenCollection.map(specimenItem => getSpecimenIdentifier(specimenItem)!);
      const specimenToAdd = specimen.filter(specimenItem => {
        const specimenIdentifier = getSpecimenIdentifier(specimenItem);
        if (specimenIdentifier == null || specimenCollectionIdentifiers.includes(specimenIdentifier)) {
          return false;
        }
        specimenCollectionIdentifiers.push(specimenIdentifier);
        return true;
      });
      return [...specimenToAdd, ...specimenCollection];
    }
    return specimenCollection;
  }

  protected convertDateFromClient(specimen: ISpecimen): ISpecimen {
    return Object.assign({}, specimen, {
      samplingDate: specimen.samplingDate?.isValid() ? specimen.samplingDate.format(DATE_FORMAT) : undefined,
      receivingDate: specimen.receivingDate?.isValid() ? specimen.receivingDate.format(DATE_FORMAT) : undefined,
      withdrawDate: specimen.withdrawDate?.isValid() ? specimen.withdrawDate.format(DATE_FORMAT) : undefined,
      revisionDate: specimen.revisionDate?.isValid() ? specimen.revisionDate.format(DATE_FORMAT) : undefined,
      reportDate: specimen.reportDate?.isValid() ? specimen.reportDate.format(DATE_FORMAT) : undefined,
      clinicalDate: specimen.clinicalDate?.isValid() ? specimen.clinicalDate.format(DATE_FORMAT) : undefined,
      grossDate: specimen.grossDate?.isValid() ? specimen.grossDate.format(DATE_FORMAT) : undefined,
      microscopicDate: specimen.microscopicDate?.isValid() ? specimen.microscopicDate.format(DATE_FORMAT) : undefined,
      conclusionDate: specimen.conclusionDate?.isValid() ? specimen.conclusionDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.samplingDate = res.body.samplingDate ? dayjs(res.body.samplingDate) : undefined;
      res.body.receivingDate = res.body.receivingDate ? dayjs(res.body.receivingDate) : undefined;
      res.body.withdrawDate = res.body.withdrawDate ? dayjs(res.body.withdrawDate) : undefined;
      res.body.revisionDate = res.body.revisionDate ? dayjs(res.body.revisionDate) : undefined;
      res.body.reportDate = res.body.reportDate ? dayjs(res.body.reportDate) : undefined;
      res.body.clinicalDate = res.body.clinicalDate ? dayjs(res.body.clinicalDate) : undefined;
      res.body.grossDate = res.body.grossDate ? dayjs(res.body.grossDate) : undefined;
      res.body.microscopicDate = res.body.microscopicDate ? dayjs(res.body.microscopicDate) : undefined;
      res.body.conclusionDate = res.body.conclusionDate ? dayjs(res.body.conclusionDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((specimen: ISpecimen) => {
        specimen.samplingDate = specimen.samplingDate ? dayjs(specimen.samplingDate) : undefined;
        specimen.receivingDate = specimen.receivingDate ? dayjs(specimen.receivingDate) : undefined;
        specimen.withdrawDate = specimen.withdrawDate ? dayjs(specimen.withdrawDate) : undefined;
        specimen.revisionDate = specimen.revisionDate ? dayjs(specimen.revisionDate) : undefined;
        specimen.reportDate = specimen.reportDate ? dayjs(specimen.reportDate) : undefined;
        specimen.clinicalDate = specimen.clinicalDate ? dayjs(specimen.clinicalDate) : undefined;
        specimen.grossDate = specimen.grossDate ? dayjs(specimen.grossDate) : undefined;
        specimen.microscopicDate = specimen.microscopicDate ? dayjs(specimen.microscopicDate) : undefined;
        specimen.conclusionDate = specimen.conclusionDate ? dayjs(specimen.conclusionDate) : undefined;
      });
    }
    return res;
  }

}
