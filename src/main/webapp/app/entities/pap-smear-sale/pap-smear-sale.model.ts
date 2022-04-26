import dayjs from 'dayjs/esm';
import { IReferringCenter } from 'app/entities/referring-center/referring-center.model';
import { PaymentType } from 'app/entities/enumerations/payment-type.model';

export interface IPapSmearSale {
  id?: number;
  dateAt?: dayjs.Dayjs | null;
  details?: string | null;
  paymentType?: PaymentType | null;
  quantity?: number | null;
  total?: number | null;
  referringCenter?: IReferringCenter | null;
}

export class PapSmearSale implements IPapSmearSale {
  constructor(
    public id?: number,
    public dateAt?: dayjs.Dayjs | null,
    public details?: string | null,
    public paymentType?: PaymentType | null,
    public quantity?: number | null,
    public total?: number | null,
    public referringCenter?: IReferringCenter | null
  ) {}
}

export function getPapSmearSaleIdentifier(papSmearSale: IPapSmearSale): number | undefined {
  return papSmearSale.id;
}
