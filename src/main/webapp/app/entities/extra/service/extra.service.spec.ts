import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ExtraAction } from 'app/entities/enumerations/extra-action.model';
import { IExtra, Extra } from '../extra.model';

import { ExtraService } from './extra.service';

describe('Extra Service', () => {
  let service: ExtraService;
  let httpMock: HttpTestingController;
  let elemDefault: IExtra;
  let expectedResult: IExtra | IExtra[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ExtraService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      dateAt: currentDate,
      extraAction: ExtraAction.EXTRA,
      details: 'AAAAAAA',
      amount: 0,
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

    it('should create a Extra', () => {
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

      service.create(new Extra()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Extra', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateAt: currentDate.format(DATE_FORMAT),
          extraAction: 'BBBBBB',
          details: 'BBBBBB',
          amount: 1,
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

    it('should partial update a Extra', () => {
      const patchObject = Object.assign({}, new Extra());

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

    it('should return a list of Extra', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateAt: currentDate.format(DATE_FORMAT),
          extraAction: 'BBBBBB',
          details: 'BBBBBB',
          amount: 1,
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

    it('should delete a Extra', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addExtraToCollectionIfMissing', () => {
      it('should add a Extra to an empty array', () => {
        const extra: IExtra = { id: 123 };
        expectedResult = service.addExtraToCollectionIfMissing([], extra);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(extra);
      });

      it('should not add a Extra to an array that contains it', () => {
        const extra: IExtra = { id: 123 };
        const extraCollection: IExtra[] = [
          {
            ...extra,
          },
          { id: 456 },
        ];
        expectedResult = service.addExtraToCollectionIfMissing(extraCollection, extra);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Extra to an array that doesn't contain it", () => {
        const extra: IExtra = { id: 123 };
        const extraCollection: IExtra[] = [{ id: 456 }];
        expectedResult = service.addExtraToCollectionIfMissing(extraCollection, extra);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(extra);
      });

      it('should add only unique Extra to an array', () => {
        const extraArray: IExtra[] = [{ id: 123 }, { id: 456 }, { id: 93336 }];
        const extraCollection: IExtra[] = [{ id: 123 }];
        expectedResult = service.addExtraToCollectionIfMissing(extraCollection, ...extraArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const extra: IExtra = { id: 123 };
        const extra2: IExtra = { id: 456 };
        expectedResult = service.addExtraToCollectionIfMissing([], extra, extra2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(extra);
        expect(expectedResult).toContain(extra2);
      });

      it('should accept null and undefined values', () => {
        const extra: IExtra = { id: 123 };
        expectedResult = service.addExtraToCollectionIfMissing([], null, extra, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(extra);
      });

      it('should return initial array if no Extra is added', () => {
        const extraCollection: IExtra[] = [{ id: 123 }];
        expectedResult = service.addExtraToCollectionIfMissing(extraCollection, undefined, null);
        expect(expectedResult).toEqual(extraCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
