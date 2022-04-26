export interface IBiopsy {
  id?: number;
  name?: string | null;
}

export class Biopsy implements IBiopsy {
  constructor(public id?: number, public name?: string | null) {}
}

export function getBiopsyIdentifier(biopsy: IBiopsy): number | undefined {
  return biopsy.id;
}
