import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOrgan, Organ } from '../organ.model';

import { OrganService } from './organ.service';

describe('Organ Service', () => {
  let service: OrganService;
  let httpMock: HttpTestingController;
  let elemDefault: IOrgan;
  let expectedResult: IOrgan | IOrgan[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrganService);
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

    it('should create a Organ', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Organ()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Organ', () => {
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

    it('should partial update a Organ', () => {
      const patchObject = Object.assign({}, new Organ());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Organ', () => {
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

    it('should delete a Organ', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addOrganToCollectionIfMissing', () => {
      it('should add a Organ to an empty array', () => {
        const organ: IOrgan = { id: 123 };
        expectedResult = service.addOrganToCollectionIfMissing([], organ);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organ);
      });

      it('should not add a Organ to an array that contains it', () => {
        const organ: IOrgan = { id: 123 };
        const organCollection: IOrgan[] = [
          {
            ...organ,
          },
          { id: 456 },
        ];
        expectedResult = service.addOrganToCollectionIfMissing(organCollection, organ);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Organ to an array that doesn't contain it", () => {
        const organ: IOrgan = { id: 123 };
        const organCollection: IOrgan[] = [{ id: 456 }];
        expectedResult = service.addOrganToCollectionIfMissing(organCollection, organ);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organ);
      });

      it('should add only unique Organ to an array', () => {
        const organArray: IOrgan[] = [{ id: 123 }, { id: 456 }, { id: 25673 }];
        const organCollection: IOrgan[] = [{ id: 123 }];
        expectedResult = service.addOrganToCollectionIfMissing(organCollection, ...organArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const organ: IOrgan = { id: 123 };
        const organ2: IOrgan = { id: 456 };
        expectedResult = service.addOrganToCollectionIfMissing([], organ, organ2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organ);
        expect(expectedResult).toContain(organ2);
      });

      it('should accept null and undefined values', () => {
        const organ: IOrgan = { id: 123 };
        expectedResult = service.addOrganToCollectionIfMissing([], null, organ, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organ);
      });

      it('should return initial array if no Organ is added', () => {
        const organCollection: IOrgan[] = [{ id: 123 }];
        expectedResult = service.addOrganToCollectionIfMissing(organCollection, undefined, null);
        expect(expectedResult).toEqual(organCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
