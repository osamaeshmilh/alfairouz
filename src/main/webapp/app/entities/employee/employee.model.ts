import { IUser } from 'app/entities/user/user.model';
import { JobTitle } from 'app/entities/enumerations/job-title.model';

export interface IEmployee {
  id?: number;
  name?: string | null;
  jobTitle?: JobTitle | null;
  salary?: number | null;
  internalUser?: IUser | null;
  newPassword?: string | null;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public name?: string | null,
    public jobTitle?: JobTitle | null,
    public salary?: number | null,
    public internalUser?: IUser | null,
    public newPassword?: string | null,
  ) {}
}

export function getEmployeeIdentifier(employee: IEmployee): number | undefined {
  return employee.id;
}
