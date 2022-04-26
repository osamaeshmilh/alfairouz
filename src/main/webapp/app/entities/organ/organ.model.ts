export interface IOrgan {
  id?: number;
  name?: string | null;
}

export class Organ implements IOrgan {
  constructor(public id?: number, public name?: string | null) {}
}

export function getOrganIdentifier(organ: IOrgan): number | undefined {
  return organ.id;
}
