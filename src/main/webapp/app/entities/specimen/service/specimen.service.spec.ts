import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { LabRef } from 'app/entities/enumerations/lab-ref.model';
import { ContractType } from 'app/entities/enumerations/contract-type.model';
import { PaymentType } from 'app/entities/enumerations/payment-type.model';
import { Results } from 'app/entities/enumerations/results.model';
import { ISpecimen, Specimen } from '../specimen.model';

import { SpecimenService } from './specimen.service';

describe('Specimen Service', () => {
  let service: SpecimenService;
  let httpMock: HttpTestingController;
  let elemDefault: ISpecimen;
  let expectedResult: ISpecimen | ISpecimen[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SpecimenService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      labRefNo: 'AAAAAAA',
      labQr: 'AAAAAAA',
      labRef: LabRef.H,
      pdfFileContentType: 'image/png',
      pdfFile: 'AAAAAAA',
      pdfFileUrl: 'AAAAAAA',
      samples: 0,
      blocks: 0,
      slides: 0,
      samplingDate: currentDate,
      receivingDate: currentDate,
      contractType: ContractType.SIZE,
      isWithdrawn: false,
      withdrawDate: currentDate,
      fileNo: 'AAAAAAA',
      paymentType: PaymentType.CASH,
      price: 0,
      paid: 0,
      notPaid: 0,
      urgentSample: false,
      revisionDate: currentDate,
      reportDate: currentDate,
      clinicalData: 'AAAAAAA',
      clinicalDate: currentDate,
      grossExamination: 'AAAAAAA',
      grossDate: currentDate,
      microscopicData: 'AAAAAAA',
      microscopicDate: currentDate,
      results: Results.BENIGN,
      conclusion: 'AAAAAAA',
      conclusionDate: currentDate,
      notes: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          samplingDate: currentDate.format(DATE_FORMAT),
          receivingDate: currentDate.format(DATE_FORMAT),
          withdrawDate: currentDate.format(DATE_FORMAT),
          revisionDate: currentDate.format(DATE_FORMAT),
          reportDate: currentDate.format(DATE_FORMAT),
          clinicalDate: currentDate.format(DATE_FORMAT),
          grossDate: currentDate.format(DATE_FORMAT),
          microscopicDate: currentDate.format(DATE_FORMAT),
          conclusionDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Specimen', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          samplingDate: currentDate.format(DATE_FORMAT),
          receivingDate: currentDate.format(DATE_FORMAT),
          withdrawDate: currentDate.format(DATE_FORMAT),
          revisionDate: currentDate.format(DATE_FORMAT),
          reportDate: currentDate.format(DATE_FORMAT),
          clinicalDate: currentDate.format(DATE_FORMAT),
          grossDate: currentDate.format(DATE_FORMAT),
          microscopicDate: currentDate.format(DATE_FORMAT),
          conclusionDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          samplingDate: currentDate,
          receivingDate: currentDate,
          withdrawDate: currentDate,
          revisionDate: currentDate,
          reportDate: currentDate,
          clinicalDate: currentDate,
          grossDate: currentDate,
          microscopicDate: currentDate,
          conclusionDate: currentDate,
        },
        returnedFromService
      );

      service.create(new Specimen()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Specimen', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          labRefNo: 'BBBBBB',
          labQr: 'BBBBBB',
          labRef: 'BBBBBB',
          pdfFile: 'BBBBBB',
          pdfFileUrl: 'BBBBBB',
          samples: 1,
          blocks: 1,
          slides: 1,
          samplingDate: currentDate.format(DATE_FORMAT),
          receivingDate: currentDate.format(DATE_FORMAT),
          contractType: 'BBBBBB',
          isWithdrawn: true,
          withdrawDate: currentDate.format(DATE_FORMAT),
          fileNo: 'BBBBBB',
          paymentType: 'BBBBBB',
          price: 1,
          paid: 1,
          notPaid: 1,
          urgentSample: true,
          revisionDate: currentDate.format(DATE_FORMAT),
          reportDate: currentDate.format(DATE_FORMAT),
          clinicalData: 'BBBBBB',
          clinicalDate: currentDate.format(DATE_FORMAT),
          grossExamination: 'BBBBBB',
          grossDate: currentDate.format(DATE_FORMAT),
          microscopicData: 'BBBBBB',
          microscopicDate: currentDate.format(DATE_FORMAT),
          results: 'BBBBBB',
          conclusion: 'BBBBBB',
          conclusionDate: currentDate.format(DATE_FORMAT),
          notes: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          samplingDate: currentDate,
          receivingDate: currentDate,
          withdrawDate: currentDate,
          revisionDate: currentDate,
          reportDate: currentDate,
          clinicalDate: currentDate,
          grossDate: currentDate,
          microscopicDate: currentDate,
          conclusionDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Specimen', () => {
      const patchObject = Object.assign(
        {
          labRefNo: 'BBBBBB',
          labQr: 'BBBBBB',
          labRef: 'BBBBBB',
          pdfFileUrl: 'BBBBBB',
          blocks: 1,
          slides: 1,
          receivingDate: currentDate.format(DATE_FORMAT),
          paymentType: 'BBBBBB',
          reportDate: currentDate.format(DATE_FORMAT),
          clinicalDate: currentDate.format(DATE_FORMAT),
          grossExamination: 'BBBBBB',
          microscopicData: 'BBBBBB',
          microscopicDate: currentDate.format(DATE_FORMAT),
          conclusion: 'BBBBBB',
        },
        new Specimen()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          samplingDate: currentDate,
          receivingDate: currentDate,
          withdrawDate: currentDate,
          revisionDate: currentDate,
          reportDate: currentDate,
          clinicalDate: currentDate,
          grossDate: currentDate,
          microscopicDate: currentDate,
          conclusionDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Specimen', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          labRefNo: 'BBBBBB',
          labQr: 'BBBBBB',
          labRef: 'BBBBBB',
          pdfFile: 'BBBBBB',
          pdfFileUrl: 'BBBBBB',
          samples: 1,
          blocks: 1,
          slides: 1,
          samplingDate: currentDate.format(DATE_FORMAT),
          receivingDate: currentDate.format(DATE_FORMAT),
          contractType: 'BBBBBB',
          isWithdrawn: true,
          withdrawDate: currentDate.format(DATE_FORMAT),
          fileNo: 'BBBBBB',
          paymentType: 'BBBBBB',
          price: 1,
          paid: 1,
          notPaid: 1,
          urgentSample: true,
          revisionDate: currentDate.format(DATE_FORMAT),
          reportDate: currentDate.format(DATE_FORMAT),
          clinicalData: 'BBBBBB',
          clinicalDate: currentDate.format(DATE_FORMAT),
          grossExamination: 'BBBBBB',
          grossDate: currentDate.format(DATE_FORMAT),
          microscopicData: 'BBBBBB',
          microscopicDate: currentDate.format(DATE_FORMAT),
          results: 'BBBBBB',
          conclusion: 'BBBBBB',
          conclusionDate: currentDate.format(DATE_FORMAT),
          notes: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          samplingDate: currentDate,
          receivingDate: currentDate,
          withdrawDate: currentDate,
          revisionDate: currentDate,
          reportDate: currentDate,
          clinicalDate: currentDate,
          grossDate: currentDate,
          microscopicDate: currentDate,
          conclusionDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Specimen', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSpecimenToCollectionIfMissing', () => {
      it('should add a Specimen to an empty array', () => {
        const specimen: ISpecimen = { id: 123 };
        expectedResult = service.addSpecimenToCollectionIfMissing([], specimen);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(specimen);
      });

      it('should not add a Specimen to an array that contains it', () => {
        const specimen: ISpecimen = { id: 123 };
        const specimenCollection: ISpecimen[] = [
          {
            ...specimen,
          },
          { id: 456 },
        ];
        expectedResult = service.addSpecimenToCollectionIfMissing(specimenCollection, specimen);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Specimen to an array that doesn't contain it", () => {
        const specimen: ISpecimen = { id: 123 };
        const specimenCollection: ISpecimen[] = [{ id: 456 }];
        expectedResult = service.addSpecimenToCollectionIfMissing(specimenCollection, specimen);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(specimen);
      });

      it('should add only unique Specimen to an array', () => {
        const specimenArray: ISpecimen[] = [{ id: 123 }, { id: 456 }, { id: 80904 }];
        const specimenCollection: ISpecimen[] = [{ id: 123 }];
        expectedResult = service.addSpecimenToCollectionIfMissing(specimenCollection, ...specimenArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const specimen: ISpecimen = { id: 123 };
        const specimen2: ISpecimen = { id: 456 };
        expectedResult = service.addSpecimenToCollectionIfMissing([], specimen, specimen2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(specimen);
        expect(expectedResult).toContain(specimen2);
      });

      it('should accept null and undefined values', () => {
        const specimen: ISpecimen = { id: 123 };
        expectedResult = service.addSpecimenToCollectionIfMissing([], null, specimen, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(specimen);
      });

      it('should return initial array if no Specimen is added', () => {
        const specimenCollection: ISpecimen[] = [{ id: 123 }];
        expectedResult = service.addSpecimenToCollectionIfMissing(specimenCollection, undefined, null);
        expect(expectedResult).toEqual(specimenCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
