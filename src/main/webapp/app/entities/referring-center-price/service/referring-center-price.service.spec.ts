import {TestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';

import {ContractType} from 'app/entities/enumerations/contract-type.model';
import {IReferringCenterPrice, ReferringCenterPrice} from '../referring-center-price.model';

import {ReferringCenterPriceService} from './referring-center-price.service';

describe('ReferringCenterPrice Service', () => {
  let service: ReferringCenterPriceService;
  let httpMock: HttpTestingController;
  let elemDefault: IReferringCenterPrice;
  let expectedResult: IReferringCenterPrice | IReferringCenterPrice[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ReferringCenterPriceService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      pricingType: ContractType.SIZE,
      price: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({method: 'GET'});
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ReferringCenterPrice', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ReferringCenterPrice()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({method: 'POST'});
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ReferringCenterPrice', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          pricingType: 'BBBBBB',
          price: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({method: 'PUT'});
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ReferringCenterPrice', () => {
      const patchObject = Object.assign(
        {
          pricingType: 'BBBBBB',
          price: 1,
        },
        new ReferringCenterPrice()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({method: 'PATCH'});
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ReferringCenterPrice', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          pricingType: 'BBBBBB',
          price: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({method: 'GET'});
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ReferringCenterPrice', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({method: 'DELETE'});
      req.flush({status: 200});
      expect(expectedResult);
    });

    describe('addReferringCenterPriceToCollectionIfMissing', () => {
      it('should add a ReferringCenterPrice to an empty array', () => {
        const referringCenterPrice: IReferringCenterPrice = {id: 123};
        expectedResult = service.addReferringCenterPriceToCollectionIfMissing([], referringCenterPrice);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(referringCenterPrice);
      });

      it('should not add a ReferringCenterPrice to an array that contains it', () => {
        const referringCenterPrice: IReferringCenterPrice = {id: 123};
        const referringCenterPriceCollection: IReferringCenterPrice[] = [
          {
            ...referringCenterPrice,
          },
          {id: 456},
        ];
        expectedResult = service.addReferringCenterPriceToCollectionIfMissing(referringCenterPriceCollection, referringCenterPrice);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ReferringCenterPrice to an array that doesn't contain it", () => {
        const referringCenterPrice: IReferringCenterPrice = {id: 123};
        const referringCenterPriceCollection: IReferringCenterPrice[] = [{id: 456}];
        expectedResult = service.addReferringCenterPriceToCollectionIfMissing(referringCenterPriceCollection, referringCenterPrice);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(referringCenterPrice);
      });

      it('should add only unique ReferringCenterPrice to an array', () => {
        const referringCenterPriceArray: IReferringCenterPrice[] = [{id: 123}, {id: 456}, {id: 10887}];
        const referringCenterPriceCollection: IReferringCenterPrice[] = [{id: 123}];
        expectedResult = service.addReferringCenterPriceToCollectionIfMissing(referringCenterPriceCollection, ...referringCenterPriceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const referringCenterPrice: IReferringCenterPrice = {id: 123};
        const referringCenterPrice2: IReferringCenterPrice = {id: 456};
        expectedResult = service.addReferringCenterPriceToCollectionIfMissing([], referringCenterPrice, referringCenterPrice2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(referringCenterPrice);
        expect(expectedResult).toContain(referringCenterPrice2);
      });

      it('should accept null and undefined values', () => {
        const referringCenterPrice: IReferringCenterPrice = {id: 123};
        expectedResult = service.addReferringCenterPriceToCollectionIfMissing([], null, referringCenterPrice, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(referringCenterPrice);
      });

      it('should return initial array if no ReferringCenterPrice is added', () => {
        const referringCenterPriceCollection: IReferringCenterPrice[] = [{id: 123}];
        expectedResult = service.addReferringCenterPriceToCollectionIfMissing(referringCenterPriceCollection, undefined, null);
        expect(expectedResult).toEqual(referringCenterPriceCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
