import dayjs from 'dayjs/esm';
import { IPatient } from 'app/entities/patient/patient.model';
import { IBiopsy } from 'app/entities/biopsy/biopsy.model';
import { ICytology } from 'app/entities/cytology/cytology.model';
import { IOrgan } from 'app/entities/organ/organ.model';
import { ISpecimenType } from 'app/entities/specimen-type/specimen-type.model';
import { ISize } from 'app/entities/size/size.model';
import { IReferringCenter } from 'app/entities/referring-center/referring-center.model';
import { IDoctor } from 'app/entities/doctor/doctor.model';
import { IEmployee } from 'app/entities/employee/employee.model';
import { LabRef } from 'app/entities/enumerations/lab-ref.model';
import { ContractType } from 'app/entities/enumerations/contract-type.model';
import { PaymentType } from 'app/entities/enumerations/payment-type.model';
import { Results } from 'app/entities/enumerations/results.model';

export interface ISpecimen {
  id?: number;
  labRefNo?: string | null;
  labQr?: string | null;
  labRef?: LabRef | null;
  pdfFileContentType?: string | null;
  pdfFile?: string | null;
  pdfFileUrl?: string | null;
  samples?: number | null;
  blocks?: number | null;
  slides?: number | null;
  samplingDate?: dayjs.Dayjs | null;
  receivingDate?: dayjs.Dayjs | null;
  contractType?: ContractType | null;
  isWithdrawn?: boolean | null;
  withdrawDate?: dayjs.Dayjs | null;
  fileNo?: string | null;
  paymentType?: PaymentType | null;
  price?: number | null;
  paid?: number | null;
  notPaid?: number | null;
  urgentSample?: boolean | null;
  revisionDate?: dayjs.Dayjs | null;
  reportDate?: dayjs.Dayjs | null;
  clinicalData?: string | null;
  clinicalDate?: dayjs.Dayjs | null;
  grossExamination?: string | null;
  grossDate?: dayjs.Dayjs | null;
  microscopicData?: string | null;
  microscopicDate?: dayjs.Dayjs | null;
  results?: Results | null;
  conclusion?: string | null;
  conclusionDate?: dayjs.Dayjs | null;
  notes?: string | null;
  patient?: IPatient | null;
  biopsy?: IBiopsy | null;
  cytology?: ICytology | null;
  organ?: IOrgan | null;
  specimenType?: ISpecimenType | null;
  size?: ISize | null;
  referringCenter?: IReferringCenter | null;
  grossingDoctor?: IDoctor | null;
  referringDoctor?: IDoctor | null;
  pathologistDoctor?: IDoctor | null;
  operatorEmployee?: IEmployee | null;
  correctorEmployee?: IEmployee | null;
}

export class Specimen implements ISpecimen {
  constructor(
    public id?: number,
    public labRefNo?: string | null,
    public labQr?: string | null,
    public labRef?: LabRef | null,
    public pdfFileContentType?: string | null,
    public pdfFile?: string | null,
    public pdfFileUrl?: string | null,
    public samples?: number | null,
    public blocks?: number | null,
    public slides?: number | null,
    public samplingDate?: dayjs.Dayjs | null,
    public receivingDate?: dayjs.Dayjs | null,
    public contractType?: ContractType | null,
    public isWithdrawn?: boolean | null,
    public withdrawDate?: dayjs.Dayjs | null,
    public fileNo?: string | null,
    public paymentType?: PaymentType | null,
    public price?: number | null,
    public paid?: number | null,
    public notPaid?: number | null,
    public urgentSample?: boolean | null,
    public revisionDate?: dayjs.Dayjs | null,
    public reportDate?: dayjs.Dayjs | null,
    public clinicalData?: string | null,
    public clinicalDate?: dayjs.Dayjs | null,
    public grossExamination?: string | null,
    public grossDate?: dayjs.Dayjs | null,
    public microscopicData?: string | null,
    public microscopicDate?: dayjs.Dayjs | null,
    public results?: Results | null,
    public conclusion?: string | null,
    public conclusionDate?: dayjs.Dayjs | null,
    public notes?: string | null,
    public patient?: IPatient | null,
    public biopsy?: IBiopsy | null,
    public cytology?: ICytology | null,
    public organ?: IOrgan | null,
    public specimenType?: ISpecimenType | null,
    public size?: ISize | null,
    public referringCenter?: IReferringCenter | null,
    public grossingDoctor?: IDoctor | null,
    public referringDoctor?: IDoctor | null,
    public pathologistDoctor?: IDoctor | null,
    public operatorEmployee?: IEmployee | null,
    public correctorEmployee?: IEmployee | null
  ) {
    this.isWithdrawn = this.isWithdrawn ?? false;
    this.urgentSample = this.urgentSample ?? false;
  }
}

export function getSpecimenIdentifier(specimen: ISpecimen): number | undefined {
  return specimen.id;
}