import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { WithdrawType } from 'app/entities/enumerations/withdraw-type.model';
import { IBlockWithdraw, BlockWithdraw } from '../block-withdraw.model';

import { BlockWithdrawService } from './block-withdraw.service';

describe('BlockWithdraw Service', () => {
  let service: BlockWithdrawService;
  let httpMock: HttpTestingController;
  let elemDefault: IBlockWithdraw;
  let expectedResult: IBlockWithdraw | IBlockWithdraw[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BlockWithdrawService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      personName: 'AAAAAAA',
      personId: 'AAAAAAA',
      quantity: 0,
      withdrawDate: currentDate,
      withdrawType: WithdrawType.BLOCK,
      pdfFileContentType: 'image/png',
      pdfFile: 'AAAAAAA',
      pdfFileUrl: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          withdrawDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a BlockWithdraw', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          withdrawDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          withdrawDate: currentDate,
        },
        returnedFromService
      );

      service.create(new BlockWithdraw()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BlockWithdraw', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          personName: 'BBBBBB',
          personId: 'BBBBBB',
          quantity: 1,
          withdrawDate: currentDate.format(DATE_FORMAT),
          withdrawType: 'BBBBBB',
          pdfFile: 'BBBBBB',
          pdfFileUrl: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          withdrawDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BlockWithdraw', () => {
      const patchObject = Object.assign(
        {
          personName: 'BBBBBB',
          personId: 'BBBBBB',
          quantity: 1,
          withdrawDate: currentDate.format(DATE_FORMAT),
        },
        new BlockWithdraw()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          withdrawDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BlockWithdraw', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          personName: 'BBBBBB',
          personId: 'BBBBBB',
          quantity: 1,
          withdrawDate: currentDate.format(DATE_FORMAT),
          withdrawType: 'BBBBBB',
          pdfFile: 'BBBBBB',
          pdfFileUrl: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          withdrawDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a BlockWithdraw', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBlockWithdrawToCollectionIfMissing', () => {
      it('should add a BlockWithdraw to an empty array', () => {
        const blockWithdraw: IBlockWithdraw = { id: 123 };
        expectedResult = service.addBlockWithdrawToCollectionIfMissing([], blockWithdraw);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(blockWithdraw);
      });

      it('should not add a BlockWithdraw to an array that contains it', () => {
        const blockWithdraw: IBlockWithdraw = { id: 123 };
        const blockWithdrawCollection: IBlockWithdraw[] = [
          {
            ...blockWithdraw,
          },
          { id: 456 },
        ];
        expectedResult = service.addBlockWithdrawToCollectionIfMissing(blockWithdrawCollection, blockWithdraw);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BlockWithdraw to an array that doesn't contain it", () => {
        const blockWithdraw: IBlockWithdraw = { id: 123 };
        const blockWithdrawCollection: IBlockWithdraw[] = [{ id: 456 }];
        expectedResult = service.addBlockWithdrawToCollectionIfMissing(blockWithdrawCollection, blockWithdraw);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(blockWithdraw);
      });

      it('should add only unique BlockWithdraw to an array', () => {
        const blockWithdrawArray: IBlockWithdraw[] = [{ id: 123 }, { id: 456 }, { id: 61696 }];
        const blockWithdrawCollection: IBlockWithdraw[] = [{ id: 123 }];
        expectedResult = service.addBlockWithdrawToCollectionIfMissing(blockWithdrawCollection, ...blockWithdrawArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const blockWithdraw: IBlockWithdraw = { id: 123 };
        const blockWithdraw2: IBlockWithdraw = { id: 456 };
        expectedResult = service.addBlockWithdrawToCollectionIfMissing([], blockWithdraw, blockWithdraw2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(blockWithdraw);
        expect(expectedResult).toContain(blockWithdraw2);
      });

      it('should accept null and undefined values', () => {
        const blockWithdraw: IBlockWithdraw = { id: 123 };
        expectedResult = service.addBlockWithdrawToCollectionIfMissing([], null, blockWithdraw, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(blockWithdraw);
      });

      it('should return initial array if no BlockWithdraw is added', () => {
        const blockWithdrawCollection: IBlockWithdraw[] = [{ id: 123 }];
        expectedResult = service.addBlockWithdrawToCollectionIfMissing(blockWithdrawCollection, undefined, null);
        expect(expectedResult).toEqual(blockWithdrawCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
