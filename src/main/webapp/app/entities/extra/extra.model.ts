import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { ExtraAction } from 'app/entities/enumerations/extra-action.model';

export interface IExtra {
  id?: number;
  dateAt?: dayjs.Dayjs | null;
  extraAction?: ExtraAction | null;
  details?: string | null;
  amount?: number | null;
  employee?: IEmployee | null;
}

export class Extra implements IExtra {
  constructor(
    public id?: number,
    public dateAt?: dayjs.Dayjs | null,
    public extraAction?: ExtraAction | null,
    public details?: string | null,
    public amount?: number | null,
    public employee?: IEmployee | null
  ) {}
}

export function getExtraIdentifier(extra: IExtra): number | undefined {
  return extra.id;
}
