import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISpecimenType, SpecimenType } from '../specimen-type.model';

import { SpecimenTypeService } from './specimen-type.service';

describe('SpecimenType Service', () => {
  let service: SpecimenTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: ISpecimenType;
  let expectedResult: ISpecimenType | ISpecimenType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SpecimenTypeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      category: 'AAAAAAA',
      price: 0,
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

    it('should create a SpecimenType', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SpecimenType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SpecimenType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          category: 'BBBBBB',
          price: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SpecimenType', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          category: 'BBBBBB',
        },
        new SpecimenType()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SpecimenType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          category: 'BBBBBB',
          price: 1,
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

    it('should delete a SpecimenType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSpecimenTypeToCollectionIfMissing', () => {
      it('should add a SpecimenType to an empty array', () => {
        const specimenType: ISpecimenType = { id: 123 };
        expectedResult = service.addSpecimenTypeToCollectionIfMissing([], specimenType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(specimenType);
      });

      it('should not add a SpecimenType to an array that contains it', () => {
        const specimenType: ISpecimenType = { id: 123 };
        const specimenTypeCollection: ISpecimenType[] = [
          {
            ...specimenType,
          },
          { id: 456 },
        ];
        expectedResult = service.addSpecimenTypeToCollectionIfMissing(specimenTypeCollection, specimenType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SpecimenType to an array that doesn't contain it", () => {
        const specimenType: ISpecimenType = { id: 123 };
        const specimenTypeCollection: ISpecimenType[] = [{ id: 456 }];
        expectedResult = service.addSpecimenTypeToCollectionIfMissing(specimenTypeCollection, specimenType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(specimenType);
      });

      it('should add only unique SpecimenType to an array', () => {
        const specimenTypeArray: ISpecimenType[] = [{ id: 123 }, { id: 456 }, { id: 91391 }];
        const specimenTypeCollection: ISpecimenType[] = [{ id: 123 }];
        expectedResult = service.addSpecimenTypeToCollectionIfMissing(specimenTypeCollection, ...specimenTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const specimenType: ISpecimenType = { id: 123 };
        const specimenType2: ISpecimenType = { id: 456 };
        expectedResult = service.addSpecimenTypeToCollectionIfMissing([], specimenType, specimenType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(specimenType);
        expect(expectedResult).toContain(specimenType2);
      });

      it('should accept null and undefined values', () => {
        const specimenType: ISpecimenType = { id: 123 };
        expectedResult = service.addSpecimenTypeToCollectionIfMissing([], null, specimenType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(specimenType);
      });

      it('should return initial array if no SpecimenType is added', () => {
        const specimenTypeCollection: ISpecimenType[] = [{ id: 123 }];
        expectedResult = service.addSpecimenTypeToCollectionIfMissing(specimenTypeCollection, undefined, null);
        expect(expectedResult).toEqual(specimenTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
