import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { Gender } from 'app/entities/enumerations/gender.model';
import { IPatient, Patient } from '../patient.model';

import { PatientService } from './patient.service';

describe('Patient Service', () => {
  let service: PatientService;
  let httpMock: HttpTestingController;
  let elemDefault: IPatient;
  let expectedResult: IPatient | IPatient[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PatientService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      nameAr: 'AAAAAAA',
      birthDate: currentDate,
      gender: Gender.MALE,
      mobileNumber: 'AAAAAAA',
      nationality: 'AAAAAAA',
      motherName: 'AAAAAAA',
      address: 'AAAAAAA',
      notes: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          birthDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Patient', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          birthDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          birthDate: currentDate,
        },
        returnedFromService
      );

      service.create(new Patient()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Patient', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          nameAr: 'BBBBBB',
          birthDate: currentDate.format(DATE_FORMAT),
          gender: 'BBBBBB',
          mobileNumber: 'BBBBBB',
          nationality: 'BBBBBB',
          motherName: 'BBBBBB',
          address: 'BBBBBB',
          notes: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          birthDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Patient', () => {
      const patchObject = Object.assign(
        {
          nameAr: 'BBBBBB',
          birthDate: currentDate.format(DATE_FORMAT),
          motherName: 'BBBBBB',
          notes: 'BBBBBB',
        },
        new Patient()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          birthDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Patient', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          nameAr: 'BBBBBB',
          birthDate: currentDate.format(DATE_FORMAT),
          gender: 'BBBBBB',
          mobileNumber: 'BBBBBB',
          nationality: 'BBBBBB',
          motherName: 'BBBBBB',
          address: 'BBBBBB',
          notes: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          birthDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Patient', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPatientToCollectionIfMissing', () => {
      it('should add a Patient to an empty array', () => {
        const patient: IPatient = { id: 123 };
        expectedResult = service.addPatientToCollectionIfMissing([], patient);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(patient);
      });

      it('should not add a Patient to an array that contains it', () => {
        const patient: IPatient = { id: 123 };
        const patientCollection: IPatient[] = [
          {
            ...patient,
          },
          { id: 456 },
        ];
        expectedResult = service.addPatientToCollectionIfMissing(patientCollection, patient);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Patient to an array that doesn't contain it", () => {
        const patient: IPatient = { id: 123 };
        const patientCollection: IPatient[] = [{ id: 456 }];
        expectedResult = service.addPatientToCollectionIfMissing(patientCollection, patient);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(patient);
      });

      it('should add only unique Patient to an array', () => {
        const patientArray: IPatient[] = [{ id: 123 }, { id: 456 }, { id: 25929 }];
        const patientCollection: IPatient[] = [{ id: 123 }];
        expectedResult = service.addPatientToCollectionIfMissing(patientCollection, ...patientArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const patient: IPatient = { id: 123 };
        const patient2: IPatient = { id: 456 };
        expectedResult = service.addPatientToCollectionIfMissing([], patient, patient2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(patient);
        expect(expectedResult).toContain(patient2);
      });

      it('should accept null and undefined values', () => {
        const patient: IPatient = { id: 123 };
        expectedResult = service.addPatientToCollectionIfMissing([], null, patient, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(patient);
      });

      it('should return initial array if no Patient is added', () => {
        const patientCollection: IPatient[] = [{ id: 123 }];
        expectedResult = service.addPatientToCollectionIfMissing(patientCollection, undefined, null);
        expect(expectedResult).toEqual(patientCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
