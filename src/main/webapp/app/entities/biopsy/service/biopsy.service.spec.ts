import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBiopsy, Biopsy } from '../biopsy.model';

import { BiopsyService } from './biopsy.service';

describe('Biopsy Service', () => {
  let service: BiopsyService;
  let httpMock: HttpTestingController;
  let elemDefault: IBiopsy;
  let expectedResult: IBiopsy | IBiopsy[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BiopsyService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
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

    it('should create a Biopsy', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Biopsy()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Biopsy', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Biopsy', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
        },
        new Biopsy()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Biopsy', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
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

    it('should delete a Biopsy', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBiopsyToCollectionIfMissing', () => {
      it('should add a Biopsy to an empty array', () => {
        const biopsy: IBiopsy = { id: 123 };
        expectedResult = service.addBiopsyToCollectionIfMissing([], biopsy);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(biopsy);
      });

      it('should not add a Biopsy to an array that contains it', () => {
        const biopsy: IBiopsy = { id: 123 };
        const biopsyCollection: IBiopsy[] = [
          {
            ...biopsy,
          },
          { id: 456 },
        ];
        expectedResult = service.addBiopsyToCollectionIfMissing(biopsyCollection, biopsy);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Biopsy to an array that doesn't contain it", () => {
        const biopsy: IBiopsy = { id: 123 };
        const biopsyCollection: IBiopsy[] = [{ id: 456 }];
        expectedResult = service.addBiopsyToCollectionIfMissing(biopsyCollection, biopsy);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(biopsy);
      });

      it('should add only unique Biopsy to an array', () => {
        const biopsyArray: IBiopsy[] = [{ id: 123 }, { id: 456 }, { id: 19651 }];
        const biopsyCollection: IBiopsy[] = [{ id: 123 }];
        expectedResult = service.addBiopsyToCollectionIfMissing(biopsyCollection, ...biopsyArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const biopsy: IBiopsy = { id: 123 };
        const biopsy2: IBiopsy = { id: 456 };
        expectedResult = service.addBiopsyToCollectionIfMissing([], biopsy, biopsy2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(biopsy);
        expect(expectedResult).toContain(biopsy2);
      });

      it('should accept null and undefined values', () => {
        const biopsy: IBiopsy = { id: 123 };
        expectedResult = service.addBiopsyToCollectionIfMissing([], null, biopsy, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(biopsy);
      });

      it('should return initial array if no Biopsy is added', () => {
        const biopsyCollection: IBiopsy[] = [{ id: 123 }];
        expectedResult = service.addBiopsyToCollectionIfMissing(biopsyCollection, undefined, null);
        expect(expectedResult).toEqual(biopsyCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
