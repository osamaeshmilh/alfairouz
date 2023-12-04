import {SpecimenStatus} from 'app/entities/enumerations/specimen-status.model';

export interface ISpecimenEdit {
  id?: number;
  specimenId?: number | null;
  labRefNo?: string | null;
  specimenStatusFrom?: SpecimenStatus | null;
  specimenStatusTo?: SpecimenStatus | null;
  userType?: string | null;
  createdBy?: string | null;
  createdDate?: Date | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: Date | null;
}

export class SpecimenEdit implements ISpecimenEdit {
  constructor(
    public id?: number,
    public specimenId?: number | null,
    public labRefNo?: string | null,
    public specimenStatusFrom?: SpecimenStatus | null,
    public specimenStatusTo?: SpecimenStatus | null,
    public userType?: string | null,
    public createdBy?: string | null,
    public createdDate?: Date | null,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: Date | null,
  ) {
  }
}

export function getSpecimenEditIdentifier(specimenEdit: ISpecimenEdit): number | undefined {
  return specimenEdit.id;
}
