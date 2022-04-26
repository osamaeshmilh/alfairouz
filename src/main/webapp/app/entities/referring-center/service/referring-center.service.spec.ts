import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ContractType } from 'app/entities/enumerations/contract-type.model';
import { IReferringCenter, ReferringCenter } from '../referring-center.model';

import { ReferringCenterService } from './referring-center.service';

describe('ReferringCenter Service', () => {
  let service: ReferringCenterService;
  let httpMock: HttpTestingController;
  let elemDefault: IReferringCenter;
  let expectedResult: IReferringCenter | IReferringCenter[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ReferringCenterService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      nameAr: 'AAAAAAA',
      mobileNumber: 'AAAAAAA',
      email: 'AAAAAAA',
      onlineReport: false,
      contractType: ContractType.SIZE,
      discount: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ReferringCenter', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ReferringCenter()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ReferringCenter', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          nameAr: 'BBBBBB',
          mobileNumber: 'BBBBBB',
          email: 'BBBBBB',
          onlineReport: true,
          contractType: 'BBBBBB',
          discount: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ReferringCenter', () => {
      const patchObject = Object.assign(
        {
          nameAr: 'BBBBBB',
          email: 'BBBBBB',
          onlineReport: true,
          discount: 1,
        },
        new ReferringCenter()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ReferringCenter', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          nameAr: 'BBBBBB',
          mobileNumber: 'BBBBBB',
          email: 'BBBBBB',
          onlineReport: true,
          contractType: 'BBBBBB',
          discount: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ReferringCenter', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addReferringCenterToCollectionIfMissing', () => {
      it('should add a ReferringCenter to an empty array', () => {
        const referringCenter: IReferringCenter = { id: 123 };
        expectedResult = service.addReferringCenterToCollectionIfMissing([], referringCenter);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(referringCenter);
      });

      it('should not add a ReferringCenter to an array that contains it', () => {
        const referringCenter: IReferringCenter = { id: 123 };
        const referringCenterCollection: IReferringCenter[] = [
          {
            ...referringCenter,
          },
          { id: 456 },
        ];
        expectedResult = service.addReferringCenterToCollectionIfMissing(referringCenterCollection, referringCenter);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ReferringCenter to an array that doesn't contain it", () => {
        const referringCenter: IReferringCenter = { id: 123 };
        const referringCenterCollection: IReferringCenter[] = [{ id: 456 }];
        expectedResult = service.addReferringCenterToCollectionIfMissing(referringCenterCollection, referringCenter);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(referringCenter);
      });

      it('should add only unique ReferringCenter to an array', () => {
        const referringCenterArray: IReferringCenter[] = [{ id: 123 }, { id: 456 }, { id: 92811 }];
        const referringCenterCollection: IReferringCenter[] = [{ id: 123 }];
        expectedResult = service.addReferringCenterToCollectionIfMissing(referringCenterCollection, ...referringCenterArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const referringCenter: IReferringCenter = { id: 123 };
        const referringCenter2: IReferringCenter = { id: 456 };
        expectedResult = service.addReferringCenterToCollectionIfMissing([], referringCenter, referringCenter2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(referringCenter);
        expect(expectedResult).toContain(referringCenter2);
      });

      it('should accept null and undefined values', () => {
        const referringCenter: IReferringCenter = { id: 123 };
        expectedResult = service.addReferringCenterToCollectionIfMissing([], null, referringCenter, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(referringCenter);
      });

      it('should return initial array if no ReferringCenter is added', () => {
        const referringCenterCollection: IReferringCenter[] = [{ id: 123 }];
        expectedResult = service.addReferringCenterToCollectionIfMissing(referringCenterCollection, undefined, null);
        expect(expectedResult).toEqual(referringCenterCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
