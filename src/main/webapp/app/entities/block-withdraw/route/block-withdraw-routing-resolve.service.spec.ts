import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IBlockWithdraw, BlockWithdraw } from '../block-withdraw.model';
import { BlockWithdrawService } from '../service/block-withdraw.service';

import { BlockWithdrawRoutingResolveService } from './block-withdraw-routing-resolve.service';

describe('BlockWithdraw routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: BlockWithdrawRoutingResolveService;
  let service: BlockWithdrawService;
  let resultBlockWithdraw: IBlockWithdraw | undefined;

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
    routingResolveService = TestBed.inject(BlockWithdrawRoutingResolveService);
    service = TestBed.inject(BlockWithdrawService);
    resultBlockWithdraw = undefined;
  });

  describe('resolve', () => {
    it('should return IBlockWithdraw returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBlockWithdraw = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBlockWithdraw).toEqual({ id: 123 });
    });

    it('should return new IBlockWithdraw if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBlockWithdraw = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultBlockWithdraw).toEqual(new BlockWithdraw());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as BlockWithdraw })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBlockWithdraw = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBlockWithdraw).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
