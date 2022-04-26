import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { ExpenseType } from 'app/entities/enumerations/expense-type.model';

export interface IExpense {
  id?: number;
  dateAt?: dayjs.Dayjs | null;
  details?: string | null;
  amount?: number | null;
  expenseType?: ExpenseType | null;
  employee?: IEmployee | null;
}

export class Expense implements IExpense {
  constructor(
    public id?: number,
    public dateAt?: dayjs.Dayjs | null,
    public details?: string | null,
    public amount?: number | null,
    public expenseType?: ExpenseType | null,
    public employee?: IEmployee | null
  ) {}
}

export function getExpenseIdentifier(expense: IExpense): number | undefined {
  return expense.id;
}
