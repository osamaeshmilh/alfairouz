import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IRepresentativeDelivery, RepresentativeDelivery } from '../representative-delivery.model';
import { RepresentativeDeliveryService } from '../service/representative-delivery.service';

import { RepresentativeDeliveryRoutingResolveService } from './representative-delivery-routing-resolve.service';

describe('RepresentativeDelivery routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: RepresentativeDeliveryRoutingResolveService;
  let service: RepresentativeDeliveryService;
  let resultRepresentativeDelivery: IRepresentativeDelivery | undefined;

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
    routingResolveService = TestBed.inject(RepresentativeDeliveryRoutingResolveService);
    service = TestBed.inject(RepresentativeDeliveryService);
    resultRepresentativeDelivery = undefined;
  });

  describe('resolve', () => {
    it('should return IRepresentativeDelivery returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRepresentativeDelivery = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRepresentativeDelivery).toEqual({ id: 123 });
    });

    it('should return new IRepresentativeDelivery if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRepresentativeDelivery = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultRepresentativeDelivery).toEqual(new RepresentativeDelivery());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as RepresentativeDelivery })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRepresentativeDelivery = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRepresentativeDelivery).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
