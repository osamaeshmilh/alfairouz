import dayjs from 'dayjs/esm';
import { ISpecimen } from 'app/entities/specimen/specimen.model';
import { IPatient } from 'app/entities/patient/patient.model';

export interface IReceipt {
  id?: number;
  dateAt?: dayjs.Dayjs | null;
  details?: string | null;
  paymentMethod?: string | null;
  price?: number | null;
  paid?: number | null;
  notPaid?: number | null;
  specimen?: ISpecimen | null;
  patient?: IPatient | null;
}

export class Receipt implements IReceipt {
  constructor(
    public id?: number,
    public dateAt?: dayjs.Dayjs | null,
    public details?: string | null,
    public paymentMethod?: string | null,
    public price?: number | null,
    public paid?: number | null,
    public notPaid?: number | null,
    public specimen?: ISpecimen | null,
    public patient?: IPatient | null
  ) {}
}

export function getReceiptIdentifier(receipt: IReceipt): number | undefined {
  return receipt.id;
}
