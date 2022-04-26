import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICytology, Cytology } from '../cytology.model';

import { CytologyService } from './cytology.service';

describe('Cytology Service', () => {
  let service: CytologyService;
  let httpMock: HttpTestingController;
  let elemDefault: ICytology;
  let expectedResult: ICytology | ICytology[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CytologyService);
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

    it('should create a Cytology', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Cytology()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Cytology', () => {
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

    it('should partial update a Cytology', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
        },
        new Cytology()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Cytology', () => {
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

    it('should delete a Cytology', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCytologyToCollectionIfMissing', () => {
      it('should add a Cytology to an empty array', () => {
        const cytology: ICytology = { id: 123 };
        expectedResult = service.addCytologyToCollectionIfMissing([], cytology);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cytology);
      });

      it('should not add a Cytology to an array that contains it', () => {
        const cytology: ICytology = { id: 123 };
        const cytologyCollection: ICytology[] = [
          {
            ...cytology,
          },
          { id: 456 },
        ];
        expectedResult = service.addCytologyToCollectionIfMissing(cytologyCollection, cytology);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Cytology to an array that doesn't contain it", () => {
        const cytology: ICytology = { id: 123 };
        const cytologyCollection: ICytology[] = [{ id: 456 }];
        expectedResult = service.addCytologyToCollectionIfMissing(cytologyCollection, cytology);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cytology);
      });

      it('should add only unique Cytology to an array', () => {
        const cytologyArray: ICytology[] = [{ id: 123 }, { id: 456 }, { id: 15296 }];
        const cytologyCollection: ICytology[] = [{ id: 123 }];
        expectedResult = service.addCytologyToCollectionIfMissing(cytologyCollection, ...cytologyArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cytology: ICytology = { id: 123 };
        const cytology2: ICytology = { id: 456 };
        expectedResult = service.addCytologyToCollectionIfMissing([], cytology, cytology2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cytology);
        expect(expectedResult).toContain(cytology2);
      });

      it('should accept null and undefined values', () => {
        const cytology: ICytology = { id: 123 };
        expectedResult = service.addCytologyToCollectionIfMissing([], null, cytology, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cytology);
      });

      it('should return initial array if no Cytology is added', () => {
        const cytologyCollection: ICytology[] = [{ id: 123 }];
        expectedResult = service.addCytologyToCollectionIfMissing(cytologyCollection, undefined, null);
        expect(expectedResult).toEqual(cytologyCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
