import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SpecimenService } from '../service/specimen.service';
import { ISpecimen, Specimen } from '../specimen.model';
import { IPatient } from 'app/entities/patient/patient.model';
import { PatientService } from 'app/entities/patient/service/patient.service';
import { IBiopsy } from 'app/entities/biopsy/biopsy.model';
import { BiopsyService } from 'app/entities/biopsy/service/biopsy.service';
import { ICytology } from 'app/entities/cytology/cytology.model';
import { CytologyService } from 'app/entities/cytology/service/cytology.service';
import { IOrgan } from 'app/entities/organ/organ.model';
import { OrganService } from 'app/entities/organ/service/organ.service';
import { ISpecimenType } from 'app/entities/specimen-type/specimen-type.model';
import { SpecimenTypeService } from 'app/entities/specimen-type/service/specimen-type.service';
import { ISize } from 'app/entities/size/size.model';
import { SizeService } from 'app/entities/size/service/size.service';
import { IReferringCenter } from 'app/entities/referring-center/referring-center.model';
import { ReferringCenterService } from 'app/entities/referring-center/service/referring-center.service';
import { IDoctor } from 'app/entities/doctor/doctor.model';
import { DoctorService } from 'app/entities/doctor/service/doctor.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';

import { SpecimenUpdateComponent } from './specimen-update.component';

describe('Specimen Management Update Component', () => {
  let comp: SpecimenUpdateComponent;
  let fixture: ComponentFixture<SpecimenUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let specimenService: SpecimenService;
  let patientService: PatientService;
  let biopsyService: BiopsyService;
  let cytologyService: CytologyService;
  let organService: OrganService;
  let specimenTypeService: SpecimenTypeService;
  let sizeService: SizeService;
  let referringCenterService: ReferringCenterService;
  let doctorService: DoctorService;
  let employeeService: EmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SpecimenUpdateComponent],
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
      .overrideTemplate(SpecimenUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SpecimenUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    specimenService = TestBed.inject(SpecimenService);
    patientService = TestBed.inject(PatientService);
    biopsyService = TestBed.inject(BiopsyService);
    cytologyService = TestBed.inject(CytologyService);
    organService = TestBed.inject(OrganService);
    specimenTypeService = TestBed.inject(SpecimenTypeService);
    sizeService = TestBed.inject(SizeService);
    referringCenterService = TestBed.inject(ReferringCenterService);
    doctorService = TestBed.inject(DoctorService);
    employeeService = TestBed.inject(EmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Patient query and add missing value', () => {
      const specimen: ISpecimen = { id: 456 };
      const patient: IPatient = { id: 94940 };
      specimen.patient = patient;

      const patientCollection: IPatient[] = [{ id: 40732 }];
      jest.spyOn(patientService, 'query').mockReturnValue(of(new HttpResponse({ body: patientCollection })));
      const additionalPatients = [patient];
      const expectedCollection: IPatient[] = [...additionalPatients, ...patientCollection];
      jest.spyOn(patientService, 'addPatientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ specimen });
      comp.ngOnInit();

      expect(patientService.query).toHaveBeenCalled();
      expect(patientService.addPatientToCollectionIfMissing).toHaveBeenCalledWith(patientCollection, ...additionalPatients);
      expect(comp.patientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Biopsy query and add missing value', () => {
      const specimen: ISpecimen = { id: 456 };
      const biopsy: IBiopsy = { id: 60671 };
      specimen.biopsy = biopsy;

      const biopsyCollection: IBiopsy[] = [{ id: 6096 }];
      jest.spyOn(biopsyService, 'query').mockReturnValue(of(new HttpResponse({ body: biopsyCollection })));
      const additionalBiopsies = [biopsy];
      const expectedCollection: IBiopsy[] = [...additionalBiopsies, ...biopsyCollection];
      jest.spyOn(biopsyService, 'addBiopsyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ specimen });
      comp.ngOnInit();

      expect(biopsyService.query).toHaveBeenCalled();
      expect(biopsyService.addBiopsyToCollectionIfMissing).toHaveBeenCalledWith(biopsyCollection, ...additionalBiopsies);
      expect(comp.biopsiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Cytology query and add missing value', () => {
      const specimen: ISpecimen = { id: 456 };
      const cytology: ICytology = { id: 49534 };
      specimen.cytology = cytology;

      const cytologyCollection: ICytology[] = [{ id: 64326 }];
      jest.spyOn(cytologyService, 'query').mockReturnValue(of(new HttpResponse({ body: cytologyCollection })));
      const additionalCytologies = [cytology];
      const expectedCollection: ICytology[] = [...additionalCytologies, ...cytologyCollection];
      jest.spyOn(cytologyService, 'addCytologyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ specimen });
      comp.ngOnInit();

      expect(cytologyService.query).toHaveBeenCalled();
      expect(cytologyService.addCytologyToCollectionIfMissing).toHaveBeenCalledWith(cytologyCollection, ...additionalCytologies);
      expect(comp.cytologiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Organ query and add missing value', () => {
      const specimen: ISpecimen = { id: 456 };
      const organ: IOrgan = { id: 16726 };
      specimen.organ = organ;

      const organCollection: IOrgan[] = [{ id: 46945 }];
      jest.spyOn(organService, 'query').mockReturnValue(of(new HttpResponse({ body: organCollection })));
      const additionalOrgans = [organ];
      const expectedCollection: IOrgan[] = [...additionalOrgans, ...organCollection];
      jest.spyOn(organService, 'addOrganToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ specimen });
      comp.ngOnInit();

      expect(organService.query).toHaveBeenCalled();
      expect(organService.addOrganToCollectionIfMissing).toHaveBeenCalledWith(organCollection, ...additionalOrgans);
      expect(comp.organsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SpecimenType query and add missing value', () => {
      const specimen: ISpecimen = { id: 456 };
      const specimenType: ISpecimenType = { id: 67956 };
      specimen.specimenType = specimenType;

      const specimenTypeCollection: ISpecimenType[] = [{ id: 81232 }];
      jest.spyOn(specimenTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: specimenTypeCollection })));
      const additionalSpecimenTypes = [specimenType];
      const expectedCollection: ISpecimenType[] = [...additionalSpecimenTypes, ...specimenTypeCollection];
      jest.spyOn(specimenTypeService, 'addSpecimenTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ specimen });
      comp.ngOnInit();

      expect(specimenTypeService.query).toHaveBeenCalled();
      expect(specimenTypeService.addSpecimenTypeToCollectionIfMissing).toHaveBeenCalledWith(
        specimenTypeCollection,
        ...additionalSpecimenTypes
      );
      expect(comp.specimenTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Size query and add missing value', () => {
      const specimen: ISpecimen = { id: 456 };
      const size: ISize = { id: 22868 };
      specimen.size = size;

      const sizeCollection: ISize[] = [{ id: 92164 }];
      jest.spyOn(sizeService, 'query').mockReturnValue(of(new HttpResponse({ body: sizeCollection })));
      const additionalSizes = [size];
      const expectedCollection: ISize[] = [...additionalSizes, ...sizeCollection];
      jest.spyOn(sizeService, 'addSizeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ specimen });
      comp.ngOnInit();

      expect(sizeService.query).toHaveBeenCalled();
      expect(sizeService.addSizeToCollectionIfMissing).toHaveBeenCalledWith(sizeCollection, ...additionalSizes);
      expect(comp.sizesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ReferringCenter query and add missing value', () => {
      const specimen: ISpecimen = { id: 456 };
      const referringCenter: IReferringCenter = { id: 28342 };
      specimen.referringCenter = referringCenter;

      const referringCenterCollection: IReferringCenter[] = [{ id: 82953 }];
      jest.spyOn(referringCenterService, 'query').mockReturnValue(of(new HttpResponse({ body: referringCenterCollection })));
      const additionalReferringCenters = [referringCenter];
      const expectedCollection: IReferringCenter[] = [...additionalReferringCenters, ...referringCenterCollection];
      jest.spyOn(referringCenterService, 'addReferringCenterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ specimen });
      comp.ngOnInit();

      expect(referringCenterService.query).toHaveBeenCalled();
      expect(referringCenterService.addReferringCenterToCollectionIfMissing).toHaveBeenCalledWith(
        referringCenterCollection,
        ...additionalReferringCenters
      );
      expect(comp.referringCentersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Doctor query and add missing value', () => {
      const specimen: ISpecimen = { id: 456 };
      const grossingDoctor: IDoctor = { id: 6564 };
      specimen.grossingDoctor = grossingDoctor;
      const referringDoctor: IDoctor = { id: 9913 };
      specimen.referringDoctor = referringDoctor;
      const pathologistDoctor: IDoctor = { id: 56083 };
      specimen.pathologistDoctor = pathologistDoctor;

      const doctorCollection: IDoctor[] = [{ id: 59348 }];
      jest.spyOn(doctorService, 'query').mockReturnValue(of(new HttpResponse({ body: doctorCollection })));
      const additionalDoctors = [grossingDoctor, referringDoctor, pathologistDoctor];
      const expectedCollection: IDoctor[] = [...additionalDoctors, ...doctorCollection];
      jest.spyOn(doctorService, 'addDoctorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ specimen });
      comp.ngOnInit();

      expect(doctorService.query).toHaveBeenCalled();
      expect(doctorService.addDoctorToCollectionIfMissing).toHaveBeenCalledWith(doctorCollection, ...additionalDoctors);
      expect(comp.doctorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Employee query and add missing value', () => {
      const specimen: ISpecimen = { id: 456 };
      const operatorEmployee: IEmployee = { id: 37489 };
      specimen.operatorEmployee = operatorEmployee;
      const correctorEmployee: IEmployee = { id: 1351 };
      specimen.correctorEmployee = correctorEmployee;

      const employeeCollection: IEmployee[] = [{ id: 68032 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [operatorEmployee, correctorEmployee];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ specimen });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(employeeCollection, ...additionalEmployees);
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const specimen: ISpecimen = { id: 456 };
      const patient: IPatient = { id: 42269 };
      specimen.patient = patient;
      const biopsy: IBiopsy = { id: 55011 };
      specimen.biopsy = biopsy;
      const cytology: ICytology = { id: 91432 };
      specimen.cytology = cytology;
      const organ: IOrgan = { id: 86790 };
      specimen.organ = organ;
      const specimenType: ISpecimenType = { id: 12797 };
      specimen.specimenType = specimenType;
      const size: ISize = { id: 31393 };
      specimen.size = size;
      const referringCenter: IReferringCenter = { id: 36510 };
      specimen.referringCenter = referringCenter;
      const grossingDoctor: IDoctor = { id: 7702 };
      specimen.grossingDoctor = grossingDoctor;
      const referringDoctor: IDoctor = { id: 87909 };
      specimen.referringDoctor = referringDoctor;
      const pathologistDoctor: IDoctor = { id: 36362 };
      specimen.pathologistDoctor = pathologistDoctor;
      const operatorEmployee: IEmployee = { id: 31691 };
      specimen.operatorEmployee = operatorEmployee;
      const correctorEmployee: IEmployee = { id: 34242 };
      specimen.correctorEmployee = correctorEmployee;

      activatedRoute.data = of({ specimen });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(specimen));
      expect(comp.patientsSharedCollection).toContain(patient);
      expect(comp.biopsiesSharedCollection).toContain(biopsy);
      expect(comp.cytologiesSharedCollection).toContain(cytology);
      expect(comp.organsSharedCollection).toContain(organ);
      expect(comp.specimenTypesSharedCollection).toContain(specimenType);
      expect(comp.sizesSharedCollection).toContain(size);
      expect(comp.referringCentersSharedCollection).toContain(referringCenter);
      expect(comp.doctorsSharedCollection).toContain(grossingDoctor);
      expect(comp.doctorsSharedCollection).toContain(referringDoctor);
      expect(comp.doctorsSharedCollection).toContain(pathologistDoctor);
      expect(comp.employeesSharedCollection).toContain(operatorEmployee);
      expect(comp.employeesSharedCollection).toContain(correctorEmployee);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Specimen>>();
      const specimen = { id: 123 };
      jest.spyOn(specimenService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ specimen });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: specimen }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(specimenService.update).toHaveBeenCalledWith(specimen);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Specimen>>();
      const specimen = new Specimen();
      jest.spyOn(specimenService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ specimen });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: specimen }));
      saveSubject.complete();

      // THEN
      expect(specimenService.create).toHaveBeenCalledWith(specimen);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Specimen>>();
      const specimen = { id: 123 };
      jest.spyOn(specimenService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ specimen });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(specimenService.update).toHaveBeenCalledWith(specimen);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPatientById', () => {
      it('Should return tracked Patient primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPatientById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackBiopsyById', () => {
      it('Should return tracked Biopsy primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackBiopsyById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCytologyById', () => {
      it('Should return tracked Cytology primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCytologyById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackOrganById', () => {
      it('Should return tracked Organ primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackOrganById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSpecimenTypeById', () => {
      it('Should return tracked SpecimenType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSpecimenTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSizeById', () => {
      it('Should return tracked Size primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSizeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackReferringCenterById', () => {
      it('Should return tracked ReferringCenter primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackReferringCenterById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDoctorById', () => {
      it('Should return tracked Doctor primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDoctorById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEmployeeById', () => {
      it('Should return tracked Employee primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEmployeeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
