import { IUser } from 'app/entities/user/user.model';
import { DoctorType } from 'app/entities/enumerations/doctor-type.model';

export interface IDoctor {
  id?: number;
  name?: string | null;
  nameAr?: string | null;
  description?: string | null;
  mobileNo?: string | null;
  email?: string | null;
  onlineReport?: boolean | null;
  emailReport?: boolean | null;
  percentage?: number | null;
  newPassword?: string | null;
  doctorType?: DoctorType | null;
  internalUser?: IUser | null;
}

export class Doctor implements IDoctor {
  constructor(
    public id?: number,
    public name?: string | null,
    public nameAr?: string | null,
    public description?: string | null,
    public mobileNo?: string | null,
    public email?: string | null,
    public onlineReport?: boolean | null,
    public emailReport?: boolean | null,
    public percentage?: number | null,
    public newPassword?: string | null,
    public doctorType?: DoctorType | null,
    public internalUser?: IUser | null
  ) {
    this.onlineReport = this.onlineReport ?? false;
    this.emailReport = this.emailReport ?? false;
  }
}

export function getDoctorIdentifier(doctor: IDoctor): number | undefined {
  return doctor.id;
}
