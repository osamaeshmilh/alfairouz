import {ISpecimenType} from 'app/entities/specimen-type/specimen-type.model';
import {ISize} from 'app/entities/size/size.model';
import {IReferringCenter} from 'app/entities/referring-center/referring-center.model';
import {ContractType} from 'app/entities/enumerations/contract-type.model';

export interface IReferringCenterPrice {
  id?: number;
  pricingType?: ContractType | null;
  price?: number | null;
  specimenType?: ISpecimenType | null;
  size?: ISize | null;
  referringCenter?: IReferringCenter | null;
}

export class ReferringCenterPrice implements IReferringCenterPrice {
  constructor(
    public id?: number,
    public pricingType?: ContractType | null,
    public price?: number | null,
    public specimenType?: ISpecimenType | null,
    public size?: ISize | null,
    public referringCenter?: IReferringCenter | null
  ) {
  }
}

export function getReferringCenterPriceIdentifier(referringCenterPrice: IReferringCenterPrice): number | undefined {
  return referringCenterPrice.id;
}
