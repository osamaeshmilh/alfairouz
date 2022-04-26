export interface ICytology {
  id?: number;
  name?: string | null;
}

export class Cytology implements ICytology {
  constructor(public id?: number, public name?: string | null) {}
}

export function getCytologyIdentifier(cytology: ICytology): number | undefined {
  return cytology.id;
}
