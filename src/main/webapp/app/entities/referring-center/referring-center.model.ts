import { IUser } from 'app/entities/user/user.model';
import { ContractType } from 'app/entities/enumerations/contract-type.model';

export interface IReferringCenter {
  id?: number;
  name?: string | null;
  nameAr?: string | null;
  mobileNumber?: string | null;
  email?: string | null;
  onlineReport?: boolean | null;
  contractType?: ContractType | null;
  discount?: number | null;
  internalUser?: IUser | null;
}

export class ReferringCenter implements IReferringCenter {
  constructor(
    public id?: number,
    public name?: string | null,
    public nameAr?: string | null,
    public mobileNumber?: string | null,
    public email?: string | null,
    public onlineReport?: boolean | null,
    public contractType?: ContractType | null,
    public discount?: number | null,
    public internalUser?: IUser | null
  ) {
    this.onlineReport = this.onlineReport ?? false;
  }
}

export function getReferringCenterIdentifier(referringCenter: IReferringCenter): number | undefined {
  return referringCenter.id;
}
