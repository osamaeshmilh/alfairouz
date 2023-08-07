import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISpecimen, Specimen } from '../specimen.model';
import { SpecimenService } from '../service/specimen.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
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
import {ReferringCenterService} from 'app/entities/referring-center/service/referring-center.service';
import {IDoctor} from 'app/entities/doctor/doctor.model';
import {DoctorService} from 'app/entities/doctor/service/doctor.service';
import {IEmployee} from 'app/entities/employee/employee.model';
import {EmployeeService} from 'app/entities/employee/service/employee.service';
import {LabRef} from 'app/entities/enumerations/lab-ref.model';
import {ContractType} from 'app/entities/enumerations/contract-type.model';
import {PaymentType} from 'app/entities/enumerations/payment-type.model';
import {Results} from 'app/entities/enumerations/results.model';
import {SpecimenStatus} from 'app/entities/enumerations/specimen-status.model';
import {Gender} from "../../enumerations/gender.model";
import dayjs from "dayjs/esm";
import {IReferringCenterPrice} from "../../referring-center-price/referring-center-price.model";
import {ReferringCenterPriceService} from "../../referring-center-price/service/referring-center-price.service";
import {Validators, Editor, Toolbar} from 'ngx-editor';

@Component({
  selector: 'jhi-specimen-update',
  templateUrl: './specimen-update.component.html',
})
export class SpecimenUpdateComponent implements OnInit {
  isSaving = false;
  labRefValues = Object.keys(LabRef);
  contractTypeValues = Object.keys(ContractType);
  paymentTypeValues = Object.keys(PaymentType);
  resultsValues = Object.keys(Results);
  specimenStatusValues = Object.keys(SpecimenStatus);

  patientsSharedCollection: IPatient[] = [];
  biopsiesSharedCollection: IBiopsy[] = [];
  cytologiesSharedCollection: ICytology[] = [];
  organsSharedCollection: IOrgan[] = [];
  specimenTypesSharedCollection: ISpecimenType[] = [];
  sizesSharedCollection: ISize[] = [];
  referringCentersSharedCollection: IReferringCenter[] = [];
  employeesSharedCollection: IEmployee[] = [];

  gDoctorsSharedCollection: IDoctor[] = [];
  rDoctorsSharedCollection: IDoctor[] = [];
  pDoctorsSharedCollection: IDoctor[] = [];

  editForm = this.fb.group({
    id: [],
    labRefNo: [],
    labRefOrder: [],
    labQr: [],
    labRef: [],
    pdfFile: [],
    pdfFileContentType: [],
    pdfFileUrl: [],
    samples: [],
    blocks: [],
    slides: [],
    samplingDate: [],
    receivingDate: [],
    contractType: [],
    isWithdrawn: [],
    withdrawDate: [],
    fileNo: [],
    paymentType: [],
    price: [],
    paid: [],
    notPaid: [],
    urgentSample: [],
    revisionDate: [],
    reportDate: [],
    clinicalData: [],
    clinicalDate: [],
    grossExamination: [],
    grossDate: [],
    microscopicData: [],
    microscopicDate: [],
    results: [],
    conclusion: [],
    conclusionDate: [],
    notes: [],
    specimenStatus: [],
    newBlocksRequested: [],
    receivedInFormalin: [],
    reserve: [],
    printedOut: [],
    smsSent: [],
    onlineReport: [],
    patient: [],
    biopsy: [],
    cytology: [],
    organ: [],
    specimenType: [],
    size: [],
    referringCenter: [],
    grossingDoctor: [],
    referringDoctor: [],
    pathologistDoctor: [],
    pathologistDoctorTwo: [],
    operatorEmployee: [],
    correctorEmployee: [],

    newPatient: [],

    patientName: [],
    patientNameAr: [],
    patientMobileNumber: [],
    patientNationality: [],
    patientMotherName: [],
    patientAddress: [],
    patientGender: [],
    patientBirthDate: [],

  });
  paymentType: any;
  selectedOption = 0;

  editor: Editor = new Editor();
  toolbar: Toolbar = [
    ['bold', 'italic'],
    ['underline', 'strike'],
    ['code', 'blockquote'],
    ['ordered_list', 'bullet_list'],
    [{heading: ['h1', 'h2', 'h3', 'h4', 'h5', 'h6']}],
    ['link', 'image'],
    ['text_color', 'background_color'],
    ['align_left', 'align_center', 'align_right', 'align_justify'],
  ];

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected specimenService: SpecimenService,
    protected patientService: PatientService,
    protected biopsyService: BiopsyService,
    protected cytologyService: CytologyService,
    protected organService: OrganService,
    protected specimenTypeService: SpecimenTypeService,
    protected sizeService: SizeService,
    protected referringCenterService: ReferringCenterService,
    protected doctorService: DoctorService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    protected referringCenterPriceService: ReferringCenterPriceService,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.editor = new Editor();

    this.activatedRoute.data.subscribe(({ specimen }) => {
      this.paymentType = this.activatedRoute.snapshot.queryParams['paymentType'] ? this.activatedRoute.snapshot.queryParams['paymentType'] : '';
      if (this.activatedRoute.snapshot.queryParams['paymentType']) {
        specimen.paymentType = this.paymentType;
      }
      this.updateForm(specimen);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('alfairouzApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const specimen = this.createFromForm();
    if (specimen.id !== undefined) {
      this.subscribeToSaveResponse(this.specimenService.update(specimen));
    } else {
      this.subscribeToSaveResponse(this.specimenService.create(specimen));
    }
  }

  trackPatientById(_index: number, item: IPatient): number {
    return item.id!;
  }

  trackBiopsyById(_index: number, item: IBiopsy): number {
    return item.id!;
  }

  trackCytologyById(_index: number, item: ICytology): number {
    return item.id!;
  }

  trackOrganById(_index: number, item: IOrgan): number {
    return item.id!;
  }

  trackSpecimenTypeById(_index: number, item: ISpecimenType): number {
    return item.id!;
  }

  trackSizeById(_index: number, item: ISize): number {
    return item.id!;
  }

  trackReferringCenterById(_index: number, item: IReferringCenter): number {
    return item.id!;
  }

  trackDoctorById(_index: number, item: IDoctor): number {
    return item.id!;
  }

  trackEmployeeById(_index: number, item: IEmployee): number {
    return item.id!;
  }

  getCenterPrices(): void {
    this.editForm.get('contractType')!.setValue(this.editForm.get('referringCenter')!.value?.contractType);
    if (this.paymentType === 'MONTHLY') {
      /* eslint-disable no-console */
      console.log("")
      // this.specimenTypeService
      //   .queryByCenter(this.editForm.get('referringCenter')!.value?.id)
      //   .pipe(map((res: HttpResponse<ISpecimenType[]>) => res.body ?? []))
      //   .pipe(
      //     map((specimenTypes: ISpecimenType[]) =>
      //       this.specimenTypeService.addSpecimenTypeToCollectionIfMissing(specimenTypes, this.editForm.get('specimenType')!.value)
      //     )
      //   )
      //   .subscribe((specimenTypes: ISpecimenType[]) => (this.specimenTypesSharedCollection = specimenTypes));
      //
      // this.sizeService
      //   .queryByCenter(this.editForm.get('referringCenter')!.value?.id)
      //   .pipe(map((res: HttpResponse<ISize[]>) => res.body ?? []))
      //   .pipe(map((sizes: ISize[]) => this.sizeService.addSizeToCollectionIfMissing(sizes, this.editForm.get('size')!.value)))
      //   .subscribe((sizes: ISize[]) => (this.sizesSharedCollection = sizes));
    }
  }

  getPriceByType($event: any): void {
    /* eslint-disable no-console */
    console.log($event.target.value)
    if (this.paymentType === 'MONTHLY') {
      const referringCenterId = this.editForm.get('referringCenter')!.value?.id;
      const specimenTypeId = this.editForm.get('specimenType')!.value?.id;

      if (referringCenterId && specimenTypeId) {
        this.referringCenterPriceService.query({
          'referringCenterId.equals': referringCenterId,
          'specimenTypeId.equals': specimenTypeId,
        })
          .subscribe((response: HttpResponse<IReferringCenterPrice[]>) => {
            const price = response.body?.[0]?.price ?? 0;
            this.editForm.get('price')!.setValue(price);
          });
      }
    } else {
      this.editForm.get('price')!.setValue(this.editForm.get('specimenType')!.value?.price)
    }
  }

  getPriceBySize($event: any): void {
    /* eslint-disable no-console */

    if (this.paymentType === 'MONTHLY') {
      const referringCenterId = this.editForm.get('referringCenter')!.value?.id;
      const sizeId = this.editForm.get('size')!.value?.id;

      if (referringCenterId && sizeId) {
        this.referringCenterPriceService.query({
          'referringCenterId.equals': referringCenterId,
          'sizeId.equals': sizeId,
        })
          .subscribe((response: HttpResponse<IReferringCenterPrice[]>) => {
            const price = response.body?.[0]?.price ?? 0;
            this.editForm.get('price')!.setValue(price);
          });
      }
    } else {
      this.editForm.get('price')!.setValue(this.editForm.get('size')!.value?.price)
    }
  }

  updatePayed(): void {
    /* eslint-disable no-console */
    this.editForm.get('notPaid')!.setValue(Number(this.editForm.get('price')!.value ?? 0) - Number(this.editForm.get('paid')!.value ?? 0))
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecimen>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(specimen: ISpecimen): void {
    this.editForm.patchValue({
      id: specimen.id,
      labRefNo: specimen.labRefNo,
      labRefOrder: specimen.labRefOrder,
      labQr: specimen.labQr,
      labRef: specimen.labRef,
      pdfFile: specimen.pdfFile,
      pdfFileContentType: specimen.pdfFileContentType,
      pdfFileUrl: specimen.pdfFileUrl,
      samples: specimen.samples,
      blocks: specimen.blocks,
      slides: specimen.slides,
      samplingDate: specimen.samplingDate,
      receivingDate: specimen.receivingDate,
      contractType: specimen.contractType,
      isWithdrawn: specimen.isWithdrawn,
      withdrawDate: specimen.withdrawDate,
      fileNo: specimen.fileNo,
      paymentType: specimen.paymentType,
      price: specimen.price,
      paid: specimen.paid,
      notPaid: specimen.notPaid,
      urgentSample: specimen.urgentSample,
      revisionDate: specimen.revisionDate,
      reportDate: specimen.reportDate,
      clinicalData: specimen.clinicalData,
      clinicalDate: specimen.clinicalDate,
      grossExamination: specimen.grossExamination,
      grossDate: specimen.grossDate,
      microscopicData: specimen.microscopicData,
      microscopicDate: specimen.microscopicDate,
      results: specimen.results,
      conclusion: specimen.conclusion,
      conclusionDate: specimen.conclusionDate,
      notes: specimen.notes,
      specimenStatus: specimen.specimenStatus,
      newBlocksRequested: specimen.newBlocksRequested,
      receivedInFormalin: specimen.receivedInFormalin,
      reserve: specimen.reserve,
      printedOut: specimen.printedOut,
      smsSent: specimen.smsSent,
      onlineReport: specimen.onlineReport,
      patient: specimen.patient,
      biopsy: specimen.biopsy,
      cytology: specimen.cytology,
      organ: specimen.organ,
      specimenType: specimen.specimenType,
      size: specimen.size,
      referringCenter: specimen.referringCenter,
      grossingDoctor: specimen.grossingDoctor,
      referringDoctor: specimen.referringDoctor,
      pathologistDoctor: specimen.pathologistDoctor,
      pathologistDoctorTwo: specimen.pathologistDoctorTwo,
      operatorEmployee: specimen.operatorEmployee,
      correctorEmployee: specimen.correctorEmployee,

      patientName: specimen.patientName,
      patientNameAr: specimen.patientNameAr,
      patientMobileNumber: specimen.patientMobileNumber,
      patientNationality: specimen.patientNationality,
      patientMotherName: specimen.patientMotherName,
      patientAddress: specimen.patientAddress,
      patientGender: specimen.patientGender,
      patientBirthDate: specimen.patientBirthDate,
    });

    this.patientsSharedCollection = this.patientService.addPatientToCollectionIfMissing(this.patientsSharedCollection, specimen.patient);
    this.biopsiesSharedCollection = this.biopsyService.addBiopsyToCollectionIfMissing(this.biopsiesSharedCollection, specimen.biopsy);
    this.cytologiesSharedCollection = this.cytologyService.addCytologyToCollectionIfMissing(
      this.cytologiesSharedCollection,
      specimen.cytology
    );
    this.organsSharedCollection = this.organService.addOrganToCollectionIfMissing(this.organsSharedCollection, specimen.organ);
    this.specimenTypesSharedCollection = this.specimenTypeService.addSpecimenTypeToCollectionIfMissing(
      this.specimenTypesSharedCollection,
      specimen.specimenType
    );
    this.sizesSharedCollection = this.sizeService.addSizeToCollectionIfMissing(this.sizesSharedCollection, specimen.size);
    this.referringCentersSharedCollection = this.referringCenterService.addReferringCenterToCollectionIfMissing(
      this.referringCentersSharedCollection,
      specimen.referringCenter
    );
    this.gDoctorsSharedCollection = this.doctorService.addDoctorToCollectionIfMissing(
      this.gDoctorsSharedCollection,
      specimen.grossingDoctor,
    );
    this.rDoctorsSharedCollection = this.doctorService.addDoctorToCollectionIfMissing(
      this.rDoctorsSharedCollection,
      specimen.referringDoctor,
    );
    this.pDoctorsSharedCollection = this.doctorService.addDoctorToCollectionIfMissing(
      this.pDoctorsSharedCollection,
      specimen.pathologistDoctor
    );
    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing(
      this.employeesSharedCollection,
      specimen.operatorEmployee,
      specimen.correctorEmployee
    );
  }

  protected loadRelationshipsOptions(): void {
    this.patientService
      .query({size: 200})
      .pipe(map((res: HttpResponse<IPatient[]>) => res.body ?? []))
      .pipe(
        map((patients: IPatient[]) => this.patientService.addPatientToCollectionIfMissing(patients, this.editForm.get('patient')!.value))
      )
      .subscribe((patients: IPatient[]) => (this.patientsSharedCollection = patients));

    this.biopsyService
      .query({size: 200})
      .pipe(map((res: HttpResponse<IBiopsy[]>) => res.body ?? []))
      .pipe(map((biopsies: IBiopsy[]) => this.biopsyService.addBiopsyToCollectionIfMissing(biopsies, this.editForm.get('biopsy')!.value)))
      .subscribe((biopsies: IBiopsy[]) => (this.biopsiesSharedCollection = biopsies));

    this.cytologyService
      .query({size: 200})
      .pipe(map((res: HttpResponse<ICytology[]>) => res.body ?? []))
      .pipe(
        map((cytologies: ICytology[]) =>
          this.cytologyService.addCytologyToCollectionIfMissing(cytologies, this.editForm.get('cytology')!.value)
        )
      )
      .subscribe((cytologies: ICytology[]) => (this.cytologiesSharedCollection = cytologies));

    this.organService
      .query({size: 200})
      .pipe(map((res: HttpResponse<IOrgan[]>) => res.body ?? []))
      .pipe(map((organs: IOrgan[]) => this.organService.addOrganToCollectionIfMissing(organs, this.editForm.get('organ')!.value)))
      .subscribe((organs: IOrgan[]) => (this.organsSharedCollection = organs));

    this.specimenTypeService
      .query({size: 200})
      .pipe(map((res: HttpResponse<ISpecimenType[]>) => res.body ?? []))
      .pipe(
        map((specimenTypes: ISpecimenType[]) =>
          this.specimenTypeService.addSpecimenTypeToCollectionIfMissing(specimenTypes, this.editForm.get('specimenType')!.value)
        )
      )
      .subscribe((specimenTypes: ISpecimenType[]) => (this.specimenTypesSharedCollection = specimenTypes));

    this.sizeService
      .query({size: 200})
      .pipe(map((res: HttpResponse<ISize[]>) => res.body ?? []))
      .pipe(map((sizes: ISize[]) => this.sizeService.addSizeToCollectionIfMissing(sizes, this.editForm.get('size')!.value)))
      .subscribe((sizes: ISize[]) => (this.sizesSharedCollection = sizes));

    this.referringCenterService
      .query({size: 200})
      .pipe(map((res: HttpResponse<IReferringCenter[]>) => res.body ?? []))
      .pipe(
        map((referringCenters: IReferringCenter[]) =>
          this.referringCenterService.addReferringCenterToCollectionIfMissing(referringCenters, this.editForm.get('referringCenter')!.value)
        )
      )
      .subscribe((referringCenters: IReferringCenter[]) => (this.referringCentersSharedCollection = referringCenters));

    this.doctorService
      .query({size: 200, 'doctorType.equals': 'GROSSING'})
      .pipe(map((res: HttpResponse<IDoctor[]>) => res.body ?? []))
      .pipe(
        map((doctors: IDoctor[]) =>
          this.doctorService.addDoctorToCollectionIfMissing(
            doctors,
            this.editForm.get('grossingDoctor')!.value
          )
        )
      )
      .subscribe((doctors: IDoctor[]) => (this.gDoctorsSharedCollection = doctors));

    this.doctorService
      .query({size: 200, 'doctorType.equals': 'REFERRING'})
      .pipe(map((res: HttpResponse<IDoctor[]>) => res.body ?? []))
      .pipe(
        map((doctors: IDoctor[]) =>
          this.doctorService.addDoctorToCollectionIfMissing(
            doctors,
            this.editForm.get('referringDoctor')!.value
          )
        )
      )
      .subscribe((doctors: IDoctor[]) => (this.rDoctorsSharedCollection = doctors));

    this.doctorService
      .query({size: 200, 'doctorType.equals': 'PATHOLOGIST'})
      .pipe(map((res: HttpResponse<IDoctor[]>) => res.body ?? []))
      .pipe(
        map((doctors: IDoctor[]) =>
          this.doctorService.addDoctorToCollectionIfMissing(
            doctors,
            this.editForm.get('pathologistDoctor')!.value
          )
        )
      )
      .subscribe((doctors: IDoctor[]) => (this.pDoctorsSharedCollection = doctors));

    this.employeeService
      .query({size: 200})
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing(
            employees,
            this.editForm.get('operatorEmployee')!.value,
            this.editForm.get('correctorEmployee')!.value
          )
        )
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));
  }

  protected createFromForm(): ISpecimen {
    return {
      ...new Specimen(),
      id: this.editForm.get(['id'])!.value,
      labRefNo: this.editForm.get(['labRefNo'])!.value,
      labRefOrder: this.editForm.get(['labRefOrder'])!.value,
      labQr: this.editForm.get(['labQr'])!.value,
      labRef: this.editForm.get(['labRef'])!.value,
      pdfFileContentType: this.editForm.get(['pdfFileContentType'])!.value,
      pdfFile: this.editForm.get(['pdfFile'])!.value,
      pdfFileUrl: this.editForm.get(['pdfFileUrl'])!.value,
      samples: this.editForm.get(['samples'])!.value,
      blocks: this.editForm.get(['blocks'])!.value,
      slides: this.editForm.get(['slides'])!.value,
      samplingDate: this.editForm.get(['samplingDate'])!.value,
      receivingDate: this.editForm.get(['receivingDate'])!.value,
      contractType: this.editForm.get(['contractType'])!.value,
      isWithdrawn: this.editForm.get(['isWithdrawn'])!.value,
      withdrawDate: this.editForm.get(['withdrawDate'])!.value,
      fileNo: this.editForm.get(['fileNo'])!.value,
      paymentType: this.editForm.get(['paymentType'])!.value,
      price: this.editForm.get(['price'])!.value,
      paid: this.editForm.get(['paid'])!.value,
      notPaid: this.editForm.get(['notPaid'])!.value,
      urgentSample: this.editForm.get(['urgentSample'])!.value,
      revisionDate: this.editForm.get(['revisionDate'])!.value,
      reportDate: this.editForm.get(['reportDate'])!.value,
      clinicalData: this.editForm.get(['clinicalData'])!.value,
      clinicalDate: this.editForm.get(['clinicalDate'])!.value,
      grossExamination: this.editForm.get(['grossExamination'])!.value,
      grossDate: this.editForm.get(['grossDate'])!.value,
      microscopicData: this.editForm.get(['microscopicData'])!.value,
      microscopicDate: this.editForm.get(['microscopicDate'])!.value,
      results: this.editForm.get(['results'])!.value,
      conclusion: this.editForm.get(['conclusion'])!.value,
      conclusionDate: this.editForm.get(['conclusionDate'])!.value,
      notes: this.editForm.get(['notes'])!.value,
      specimenStatus: this.editForm.get(['specimenStatus'])!.value,
      newBlocksRequested: this.editForm.get(['newBlocksRequested'])!.value,
      receivedInFormalin: this.editForm.get(['receivedInFormalin'])!.value,
      reserve: this.editForm.get(['reserve'])!.value,
      printedOut: this.editForm.get(['printedOut'])!.value,
      smsSent: this.editForm.get(['smsSent'])!.value,
      onlineReport: this.editForm.get(['onlineReport'])!.value,
      patient: this.editForm.get(['patient'])!.value,
      biopsy: this.editForm.get(['biopsy'])!.value,
      cytology: this.editForm.get(['cytology'])!.value,
      organ: this.editForm.get(['organ'])!.value,
      specimenType: this.editForm.get(['specimenType'])!.value,
      size: this.editForm.get(['size'])!.value,
      referringCenter: this.editForm.get(['referringCenter'])!.value,
      grossingDoctor: this.editForm.get(['grossingDoctor'])!.value,
      referringDoctor: this.editForm.get(['referringDoctor'])!.value,
      pathologistDoctor: this.editForm.get(['pathologistDoctor'])!.value,
      pathologistDoctorTwo: this.editForm.get(['pathologistDoctorTwo'])!.value,
      operatorEmployee: this.editForm.get(['operatorEmployee'])!.value,
      correctorEmployee: this.editForm.get(['correctorEmployee'])!.value,

      patientName: this.editForm.get(['patientName'])!.value,
      patientNameAr: this.editForm.get(['patientNameAr'])!.value,
      patientMobileNumber: this.editForm.get(['patientMobileNumber'])!.value,
      patientNationality: this.editForm.get(['patientNationality'])!.value,
      patientMotherName: this.editForm.get(['patientMotherName'])!.value,
      patientAddress: this.editForm.get(['patientAddress'])!.value,
      patientGender: this.editForm.get(['patientGender'])!.value,
      patientBirthDate: this.editForm.get(['patientBirthDate'])!.value,
    };
  }


}
