import dayjs from 'dayjs/esm';
import { ISpecimen } from 'app/entities/specimen/specimen.model';
import { WithdrawType } from 'app/entities/enumerations/withdraw-type.model';

export interface IBlockWithdraw {
  id?: number;
  personName?: string | null;
  personId?: string | null;
  quantity?: number | null;
  withdrawDate?: dayjs.Dayjs | null;
  withdrawType?: WithdrawType | null;
  pdfFileContentType?: string | null;
  pdfFile?: string | null;
  pdfFileUrl?: string | null;
  specimen?: ISpecimen | null;
}

export class BlockWithdraw implements IBlockWithdraw {
  constructor(
    public id?: number,
    public personName?: string | null,
    public personId?: string | null,
    public quantity?: number | null,
    public withdrawDate?: dayjs.Dayjs | null,
    public withdrawType?: WithdrawType | null,
    public pdfFileContentType?: string | null,
    public pdfFile?: string | null,
    public pdfFileUrl?: string | null,
    public specimen?: ISpecimen | null
  ) {}
}

export function getBlockWithdrawIdentifier(blockWithdraw: IBlockWithdraw): number | undefined {
  return blockWithdraw.id;
}
