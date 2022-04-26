import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IRepresentativeDelivery, RepresentativeDelivery } from '../representative-delivery.model';

import { RepresentativeDeliveryService } from './representative-delivery.service';

describe('RepresentativeDelivery Service', () => {
  let service: RepresentativeDeliveryService;
  let httpMock: HttpTestingController;
  let elemDefault: IRepresentativeDelivery;
  let expectedResult: IRepresentativeDelivery | IRepresentativeDelivery[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RepresentativeDeliveryService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      dateAt: currentDate,
      details: 'AAAAAAA',
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

    it('should create a RepresentativeDelivery', () => {
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

      service.create(new RepresentativeDelivery()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RepresentativeDelivery', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateAt: currentDate.format(DATE_FORMAT),
          details: 'BBBBBB',
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

    it('should partial update a RepresentativeDelivery', () => {
      const patchObject = Object.assign({}, new RepresentativeDelivery());

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

    it('should return a list of RepresentativeDelivery', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateAt: currentDate.format(DATE_FORMAT),
          details: 'BBBBBB',
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

    it('should delete a RepresentativeDelivery', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRepresentativeDeliveryToCollectionIfMissing', () => {
      it('should add a RepresentativeDelivery to an empty array', () => {
        const representativeDelivery: IRepresentativeDelivery = { id: 123 };
        expectedResult = service.addRepresentativeDeliveryToCollectionIfMissing([], representativeDelivery);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(representativeDelivery);
      });

      it('should not add a RepresentativeDelivery to an array that contains it', () => {
        const representativeDelivery: IRepresentativeDelivery = { id: 123 };
        const representativeDeliveryCollection: IRepresentativeDelivery[] = [
          {
            ...representativeDelivery,
          },
          { id: 456 },
        ];
        expectedResult = service.addRepresentativeDeliveryToCollectionIfMissing(representativeDeliveryCollection, representativeDelivery);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RepresentativeDelivery to an array that doesn't contain it", () => {
        const representativeDelivery: IRepresentativeDelivery = { id: 123 };
        const representativeDeliveryCollection: IRepresentativeDelivery[] = [{ id: 456 }];
        expectedResult = service.addRepresentativeDeliveryToCollectionIfMissing(representativeDeliveryCollection, representativeDelivery);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(representativeDelivery);
      });

      it('should add only unique RepresentativeDelivery to an array', () => {
        const representativeDeliveryArray: IRepresentativeDelivery[] = [{ id: 123 }, { id: 456 }, { id: 58308 }];
        const representativeDeliveryCollection: IRepresentativeDelivery[] = [{ id: 123 }];
        expectedResult = service.addRepresentativeDeliveryToCollectionIfMissing(
          representativeDeliveryCollection,
          ...representativeDeliveryArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const representativeDelivery: IRepresentativeDelivery = { id: 123 };
        const representativeDelivery2: IRepresentativeDelivery = { id: 456 };
        expectedResult = service.addRepresentativeDeliveryToCollectionIfMissing([], representativeDelivery, representativeDelivery2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(representativeDelivery);
        expect(expectedResult).toContain(representativeDelivery2);
      });

      it('should accept null and undefined values', () => {
        const representativeDelivery: IRepresentativeDelivery = { id: 123 };
        expectedResult = service.addRepresentativeDeliveryToCollectionIfMissing([], null, representativeDelivery, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(representativeDelivery);
      });

      it('should return initial array if no RepresentativeDelivery is added', () => {
        const representativeDeliveryCollection: IRepresentativeDelivery[] = [{ id: 123 }];
        expectedResult = service.addRepresentativeDeliveryToCollectionIfMissing(representativeDeliveryCollection, undefined, null);
        expect(expectedResult).toEqual(representativeDeliveryCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
