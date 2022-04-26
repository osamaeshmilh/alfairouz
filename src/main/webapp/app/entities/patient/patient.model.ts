import dayjs from 'dayjs/esm';
import { Gender } from 'app/entities/enumerations/gender.model';

export interface IPatient {
  id?: number;
  name?: string | null;
  nameAr?: string | null;
  birthDate?: dayjs.Dayjs | null;
  gender?: Gender | null;
  mobileNumber?: string | null;
  nationality?: string | null;
  motherName?: string | null;
  address?: string | null;
  notes?: string | null;
}

export class Patient implements IPatient {
  constructor(
    public id?: number,
    public name?: string | null,
    public nameAr?: string | null,
    public birthDate?: dayjs.Dayjs | null,
    public gender?: Gender | null,
    public mobileNumber?: string | null,
    public nationality?: string | null,
    public motherName?: string | null,
    public address?: string | null,
    public notes?: string | null
  ) {}
}

export function getPatientIdentifier(patient: IPatient): number | undefined {
  return patient.id;
}
