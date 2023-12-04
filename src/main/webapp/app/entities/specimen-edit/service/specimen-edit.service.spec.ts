import {TestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';

import {SpecimenStatus} from 'app/entities/enumerations/specimen-status.model';
import {ISpecimenEdit, SpecimenEdit} from '../specimen-edit.model';

import {SpecimenEditService} from './specimen-edit.service';

describe('SpecimenEdit Service', () => {
  let service: SpecimenEditService;
  let httpMock: HttpTestingController;
  let elemDefault: ISpecimenEdit;
  let expectedResult: ISpecimenEdit | ISpecimenEdit[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SpecimenEditService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      specimenId: 0,
      labRefNo: 'AAAAAAA',
      specimenStatusFrom: SpecimenStatus.RECEIVED,
      specimenStatusTo: SpecimenStatus.RECEIVED,
      userType: 'AAAAAAA',
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

    it('should create a SpecimenEdit', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SpecimenEdit()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({method: 'POST'});
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SpecimenEdit', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          specimenId: 1,
          labRefNo: 'BBBBBB',
          specimenStatusFrom: 'BBBBBB',
          specimenStatusTo: 'BBBBBB',
          userType: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({method: 'PUT'});
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SpecimenEdit', () => {
      const patchObject = Object.assign(
        {
          labRefNo: 'BBBBBB',
          specimenStatusTo: 'BBBBBB',
          userType: 'BBBBBB',
        },
        new SpecimenEdit()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({method: 'PATCH'});
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SpecimenEdit', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          specimenId: 1,
          labRefNo: 'BBBBBB',
          specimenStatusFrom: 'BBBBBB',
          specimenStatusTo: 'BBBBBB',
          userType: 'BBBBBB',
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

    it('should delete a SpecimenEdit', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({method: 'DELETE'});
      req.flush({status: 200});
      expect(expectedResult);
    });

    describe('addSpecimenEditToCollectionIfMissing', () => {
      it('should add a SpecimenEdit to an empty array', () => {
        const specimenEdit: ISpecimenEdit = {id: 123};
        expectedResult = service.addSpecimenEditToCollectionIfMissing([], specimenEdit);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(specimenEdit);
      });

      it('should not add a SpecimenEdit to an array that contains it', () => {
        const specimenEdit: ISpecimenEdit = {id: 123};
        const specimenEditCollection: ISpecimenEdit[] = [
          {
            ...specimenEdit,
          },
          {id: 456},
        ];
        expectedResult = service.addSpecimenEditToCollectionIfMissing(specimenEditCollection, specimenEdit);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SpecimenEdit to an array that doesn't contain it", () => {
        const specimenEdit: ISpecimenEdit = {id: 123};
        const specimenEditCollection: ISpecimenEdit[] = [{id: 456}];
        expectedResult = service.addSpecimenEditToCollectionIfMissing(specimenEditCollection, specimenEdit);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(specimenEdit);
      });

      it('should add only unique SpecimenEdit to an array', () => {
        const specimenEditArray: ISpecimenEdit[] = [{id: 123}, {id: 456}, {id: 37398}];
        const specimenEditCollection: ISpecimenEdit[] = [{id: 123}];
        expectedResult = service.addSpecimenEditToCollectionIfMissing(specimenEditCollection, ...specimenEditArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const specimenEdit: ISpecimenEdit = {id: 123};
        const specimenEdit2: ISpecimenEdit = {id: 456};
        expectedResult = service.addSpecimenEditToCollectionIfMissing([], specimenEdit, specimenEdit2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(specimenEdit);
        expect(expectedResult).toContain(specimenEdit2);
      });

      it('should accept null and undefined values', () => {
        const specimenEdit: ISpecimenEdit = {id: 123};
        expectedResult = service.addSpecimenEditToCollectionIfMissing([], null, specimenEdit, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(specimenEdit);
      });

      it('should return initial array if no SpecimenEdit is added', () => {
        const specimenEditCollection: ISpecimenEdit[] = [{id: 123}];
        expectedResult = service.addSpecimenEditToCollectionIfMissing(specimenEditCollection, undefined, null);
        expect(expectedResult).toEqual(specimenEditCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
