export interface ISize {
  id?: number;
  name?: string | null;
  price?: number | null;
}

export class Size implements ISize {
  constructor(public id?: number, public name?: string | null, public price?: number | null) {}
}

export function getSizeIdentifier(size: ISize): number | undefined {
  return size.id;
}
