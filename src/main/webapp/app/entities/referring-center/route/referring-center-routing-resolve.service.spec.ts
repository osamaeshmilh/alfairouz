import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IReferringCenter, ReferringCenter } from '../referring-center.model';
import { ReferringCenterService } from '../service/referring-center.service';

import { ReferringCenterRoutingResolveService } from './referring-center-routing-resolve.service';

describe('ReferringCenter routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ReferringCenterRoutingResolveService;
  let service: ReferringCenterService;
  let resultReferringCenter: IReferringCenter | undefined;

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
    routingResolveService = TestBed.inject(ReferringCenterRoutingResolveService);
    service = TestBed.inject(ReferringCenterService);
    resultReferringCenter = undefined;
  });

  describe('resolve', () => {
    it('should return IReferringCenter returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReferringCenter = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultReferringCenter).toEqual({ id: 123 });
    });

    it('should return new IReferringCenter if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReferringCenter = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultReferringCenter).toEqual(new ReferringCenter());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ReferringCenter })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultReferringCenter = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultReferringCenter).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
