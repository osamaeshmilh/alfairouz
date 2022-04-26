import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBlockWithdraw, getBlockWithdrawIdentifier } from '../block-withdraw.model';

export type EntityResponseType = HttpResponse<IBlockWithdraw>;
export type EntityArrayResponseType = HttpResponse<IBlockWithdraw[]>;

@Injectable({ providedIn: 'root' })
export class BlockWithdrawService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/block-withdraws');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(blockWithdraw: IBlockWithdraw): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(blockWithdraw);
    return this.http
      .post<IBlockWithdraw>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(blockWithdraw: IBlockWithdraw): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(blockWithdraw);
    return this.http
      .put<IBlockWithdraw>(`${this.resourceUrl}/${getBlockWithdrawIdentifier(blockWithdraw) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(blockWithdraw: IBlockWithdraw): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(blockWithdraw);
    return this.http
      .patch<IBlockWithdraw>(`${this.resourceUrl}/${getBlockWithdrawIdentifier(blockWithdraw) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBlockWithdraw>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBlockWithdraw[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBlockWithdrawToCollectionIfMissing(
    blockWithdrawCollection: IBlockWithdraw[],
    ...blockWithdrawsToCheck: (IBlockWithdraw | null | undefined)[]
  ): IBlockWithdraw[] {
    const blockWithdraws: IBlockWithdraw[] = blockWithdrawsToCheck.filter(isPresent);
    if (blockWithdraws.length > 0) {
      const blockWithdrawCollectionIdentifiers = blockWithdrawCollection.map(
        blockWithdrawItem => getBlockWithdrawIdentifier(blockWithdrawItem)!
      );
      const blockWithdrawsToAdd = blockWithdraws.filter(blockWithdrawItem => {
        const blockWithdrawIdentifier = getBlockWithdrawIdentifier(blockWithdrawItem);
        if (blockWithdrawIdentifier == null || blockWithdrawCollectionIdentifiers.includes(blockWithdrawIdentifier)) {
          return false;
        }
        blockWithdrawCollectionIdentifiers.push(blockWithdrawIdentifier);
        return true;
      });
      return [...blockWithdrawsToAdd, ...blockWithdrawCollection];
    }
    return blockWithdrawCollection;
  }

  protected convertDateFromClient(blockWithdraw: IBlockWithdraw): IBlockWithdraw {
    return Object.assign({}, blockWithdraw, {
      withdrawDate: blockWithdraw.withdrawDate?.isValid() ? blockWithdraw.withdrawDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.withdrawDate = res.body.withdrawDate ? dayjs(res.body.withdrawDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((blockWithdraw: IBlockWithdraw) => {
        blockWithdraw.withdrawDate = blockWithdraw.withdrawDate ? dayjs(blockWithdraw.withdrawDate) : undefined;
      });
    }
    return res;
  }
}
