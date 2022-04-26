export interface ISpecimenType {
  id?: number;
  name?: string | null;
  category?: string | null;
  price?: number | null;
}

export class SpecimenType implements ISpecimenType {
  constructor(public id?: number, public name?: string | null, public category?: string | null, public price?: number | null) {}
}

export function getSpecimenTypeIdentifier(specimenType: ISpecimenType): number | undefined {
  return specimenType.id;
}
