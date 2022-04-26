import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPapSmearSale, PapSmearSale } from '../pap-smear-sale.model';
import { PapSmearSaleService } from '../service/pap-smear-sale.service';

import { PapSmearSaleRoutingResolveService } from './pap-smear-sale-routing-resolve.service';

describe('PapSmearSale routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PapSmearSaleRoutingResolveService;
  let service: PapSmearSaleService;
  let resultPapSmearSale: IPapSmearSale | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(PapSmearSaleRoutingResolveService);
    service = TestBed.inject(PapSmearSaleService);
    resultPapSmearSale = undefined;
  });

  describe('resolve', () => {
    it('should return IPapSmearSale returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPapSmearSale = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPapSmearSale).toEqual({ id: 123 });
    });

    it('should return new IPapSmearSale if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPapSmearSale = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPapSmearSale).toEqual(new PapSmearSale());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PapSmearSale })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPapSmearSale = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPapSmearSale).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
