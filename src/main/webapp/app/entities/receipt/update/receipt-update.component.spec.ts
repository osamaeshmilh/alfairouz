import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReceiptService } from '../service/receipt.service';
import { IReceipt, Receipt } from '../receipt.model';
import { ISpecimen } from 'app/entities/specimen/specimen.model';
import { SpecimenService } from 'app/entities/specimen/service/specimen.service';
import { IPatient } from 'app/entities/patient/patient.model';
import { PatientService } from 'app/entities/patient/service/patient.service';

import { ReceiptUpdateComponent } from './receipt-update.component';

describe('Receipt Management Update Component', () => {
  let comp: ReceiptUpdateComponent;
  let fixture: ComponentFixture<ReceiptUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let receiptService: ReceiptService;
  let specimenService: SpecimenService;
  let patientService: PatientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReceiptUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ReceiptUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReceiptUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    receiptService = TestBed.inject(ReceiptService);
    specimenService = TestBed.inject(SpecimenService);
    patientService = TestBed.inject(PatientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Specimen query and add missing value', () => {
      const receipt: IReceipt = { id: 456 };
      const specimen: ISpecimen = { id: 93726 };
      receipt.specimen = specimen;

      const specimenCollection: ISpecimen[] = [{ id: 74311 }];
      jest.spyOn(specimenService, 'query').mockReturnValue(of(new HttpResponse({ body: specimenCollection })));
      const additionalSpecimen = [specimen];
      const expectedCollection: ISpecimen[] = [...additionalSpecimen, ...specimenCollection];
      jest.spyOn(specimenService, 'addSpecimenToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ receipt });
      comp.ngOnInit();

      expect(specimenService.query).toHaveBeenCalled();
      expect(specimenService.addSpecimenToCollectionIfMissing).toHaveBeenCalledWith(specimenCollection, ...additionalSpecimen);
      expect(comp.specimenSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Patient query and add missing value', () => {
      const receipt: IReceipt = { id: 456 };
      const patient: IPatient = { id: 69799 };
      receipt.patient = patient;

      const patientCollection: IPatient[] = [{ id: 5673 }];
      jest.spyOn(patientService, 'query').mockReturnValue(of(new HttpResponse({ body: patientCollection })));
      const additionalPatients = [patient];
      const expectedCollection: IPatient[] = [...additionalPatients, ...patientCollection];
      jest.spyOn(patientService, 'addPatientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ receipt });
      comp.ngOnInit();

      expect(patientService.query).toHaveBeenCalled();
      expect(patientService.addPatientToCollectionIfMissing).toHaveBeenCalledWith(patientCollection, ...additionalPatients);
      expect(comp.patientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const receipt: IReceipt = { id: 456 };
      const specimen: ISpecimen = { id: 5249 };
      receipt.specimen = specimen;
      const patient: IPatient = { id: 86645 };
      receipt.patient = patient;

      activatedRoute.data = of({ receipt });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(receipt));
      expect(comp.specimenSharedCollection).toContain(specimen);
      expect(comp.patientsSharedCollection).toContain(patient);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Receipt>>();
      const receipt = { id: 123 };
      jest.spyOn(receiptService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receipt });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: receipt }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(receiptService.update).toHaveBeenCalledWith(receipt);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Receipt>>();
      const receipt = new Receipt();
      jest.spyOn(receiptService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receipt });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: receipt }));
      saveSubject.complete();

      // THEN
      expect(receiptService.create).toHaveBeenCalledWith(receipt);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Receipt>>();
      const receipt = { id: 123 };
      jest.spyOn(receiptService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receipt });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(receiptService.update).toHaveBeenCalledWith(receipt);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSpecimenById', () => {
      it('Should return tracked Specimen primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSpecimenById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPatientById', () => {
      it('Should return tracked Patient primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPatientById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
