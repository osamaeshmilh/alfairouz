import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ExpenseType } from 'app/entities/enumerations/expense-type.model';
import { IExpense, Expense } from '../expense.model';

import { ExpenseService } from './expense.service';

describe('Expense Service', () => {
  let service: ExpenseService;
  let httpMock: HttpTestingController;
  let elemDefault: IExpense;
  let expectedResult: IExpense | IExpense[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ExpenseService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      dateAt: currentDate,
      details: 'AAAAAAA',
      amount: 0,
      expenseType: ExpenseType.MANAGMENT,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateAt: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Expense', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateAt: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAt: currentDate,
        },
        returnedFromService
      );

      service.create(new Expense()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Expense', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateAt: currentDate.format(DATE_FORMAT),
          details: 'BBBBBB',
          amount: 1,
          expenseType: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAt: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Expense', () => {
      const patchObject = Object.assign(
        {
          dateAt: currentDate.format(DATE_FORMAT),
          amount: 1,
        },
        new Expense()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateAt: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Expense', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dateAt: currentDate.format(DATE_FORMAT),
          details: 'BBBBBB',
          amount: 1,
          expenseType: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAt: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Expense', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addExpenseToCollectionIfMissing', () => {
      it('should add a Expense to an empty array', () => {
        const expense: IExpense = { id: 123 };
        expectedResult = service.addExpenseToCollectionIfMissing([], expense);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(expense);
      });

      it('should not add a Expense to an array that contains it', () => {
        const expense: IExpense = { id: 123 };
        const expenseCollection: IExpense[] = [
          {
            ...expense,
          },
          { id: 456 },
        ];
        expectedResult = service.addExpenseToCollectionIfMissing(expenseCollection, expense);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Expense to an array that doesn't contain it", () => {
        const expense: IExpense = { id: 123 };
        const expenseCollection: IExpense[] = [{ id: 456 }];
        expectedResult = service.addExpenseToCollectionIfMissing(expenseCollection, expense);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(expense);
      });

      it('should add only unique Expense to an array', () => {
        const expenseArray: IExpense[] = [{ id: 123 }, { id: 456 }, { id: 97617 }];
        const expenseCollection: IExpense[] = [{ id: 123 }];
        expectedResult = service.addExpenseToCollectionIfMissing(expenseCollection, ...expenseArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const expense: IExpense = { id: 123 };
        const expense2: IExpense = { id: 456 };
        expectedResult = service.addExpenseToCollectionIfMissing([], expense, expense2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(expense);
        expect(expectedResult).toContain(expense2);
      });

      it('should accept null and undefined values', () => {
        const expense: IExpense = { id: 123 };
        expectedResult = service.addExpenseToCollectionIfMissing([], null, expense, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(expense);
      });

      it('should return initial array if no Expense is added', () => {
        const expenseCollection: IExpense[] = [{ id: 123 }];
        expectedResult = service.addExpenseToCollectionIfMissing(expenseCollection, undefined, null);
        expect(expectedResult).toEqual(expenseCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
