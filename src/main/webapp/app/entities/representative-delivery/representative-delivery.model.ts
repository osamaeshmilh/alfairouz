import dayjs from 'dayjs/esm';

export interface IRepresentativeDelivery {
  id?: number;
  dateAt?: dayjs.Dayjs | null;
  details?: string | null;
  total?: number | null;
}

export class RepresentativeDelivery implements IRepresentativeDelivery {
  constructor(public id?: number, public dateAt?: dayjs.Dayjs | null, public details?: string | null, public total?: number | null) {}
}

export function getRepresentativeDeliveryIdentifier(representativeDelivery: IRepresentativeDelivery): number | undefined {
  return representativeDelivery.id;
}
