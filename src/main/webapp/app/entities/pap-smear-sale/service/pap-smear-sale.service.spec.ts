import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { PaymentType } from 'app/entities/enumerations/payment-type.model';
import { IPapSmearSale, PapSmearSale } from '../pap-smear-sale.model';

import { PapSmearSaleService } from './pap-smear-sale.service';

describe('PapSmearSale Service', () => {
  let service: PapSmearSaleService;
  let httpMock: HttpTestingController;
  let elemDefault: IPapSmearSale;
  let expectedResult: IPapSmearSale | IPapSmearSale[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PapSmearSaleService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      dateAt: currentDate,
      details: 'AAAAAAA',
      paymentType: PaymentType.CASH,
      quantity: 0,
      total: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateAt: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a PapSmearSale', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateAt: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAt: currentDate,
        },
        returnedFromService
      );

      service.create(new PapSmearSale()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PapSmearSale', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateAt: currentDate.format(DATE_FORMAT),
          details: 'BBBBBB',
          paymentType: 'BBBBBB',
          quantity: 1,
          total: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAt: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PapSmearSale', () => {
      const patchObject = Object.assign(
        {
          dateAt: currentDate.format(DATE_FORMAT),
          total: 1,
        },
        new PapSmearSale()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateAt: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PapSmearSale', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateAt: currentDate.format(DATE_FORMAT),
          details: 'BBBBBB',
          paymentType: 'BBBBBB',
          quantity: 1,
          total: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAt: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a PapSmearSale', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPapSmearSaleToCollectionIfMissing', () => {
      it('should add a PapSmearSale to an empty array', () => {
        const papSmearSale: IPapSmearSale = { id: 123 };
        expectedResult = service.addPapSmearSaleToCollectionIfMissing([], papSmearSale);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(papSmearSale);
      });

      it('should not add a PapSmearSale to an array that contains it', () => {
        const papSmearSale: IPapSmearSale = { id: 123 };
        const papSmearSaleCollection: IPapSmearSale[] = [
          {
            ...papSmearSale,
          },
          { id: 456 },
        ];
        expectedResult = service.addPapSmearSaleToCollectionIfMissing(papSmearSaleCollection, papSmearSale);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PapSmearSale to an array that doesn't contain it", () => {
        const papSmearSale: IPapSmearSale = { id: 123 };
        const papSmearSaleCollection: IPapSmearSale[] = [{ id: 456 }];
        expectedResult = service.addPapSmearSaleToCollectionIfMissing(papSmearSaleCollection, papSmearSale);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(papSmearSale);
      });

      it('should add only unique PapSmearSale to an array', () => {
        const papSmearSaleArray: IPapSmearSale[] = [{ id: 123 }, { id: 456 }, { id: 47952 }];
        const papSmearSaleCollection: IPapSmearSale[] = [{ id: 123 }];
        expectedResult = service.addPapSmearSaleToCollectionIfMissing(papSmearSaleCollection, ...papSmearSaleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const papSmearSale: IPapSmearSale = { id: 123 };
        const papSmearSale2: IPapSmearSale = { id: 456 };
        expectedResult = service.addPapSmearSaleToCollectionIfMissing([], papSmearSale, papSmearSale2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(papSmearSale);
        expect(expectedResult).toContain(papSmearSale2);
      });

      it('should accept null and undefined values', () => {
        const papSmearSale: IPapSmearSale = { id: 123 };
        expectedResult = service.addPapSmearSaleToCollectionIfMissing([], null, papSmearSale, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(papSmearSale);
      });

      it('should return initial array if no PapSmearSale is added', () => {
        const papSmearSaleCollection: IPapSmearSale[] = [{ id: 123 }];
        expectedResult = service.addPapSmearSaleToCollectionIfMissing(papSmearSaleCollection, undefined, null);
        expect(expectedResult).toEqual(papSmearSaleCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
