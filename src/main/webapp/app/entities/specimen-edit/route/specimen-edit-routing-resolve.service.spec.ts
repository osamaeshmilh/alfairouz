import {TestBed} from '@angular/core/testing';
import {HttpResponse} from '@angular/common/http';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {of} from 'rxjs';

import {ISpecimenEdit, SpecimenEdit} from '../specimen-edit.model';
import {SpecimenEditService} from '../service/specimen-edit.service';

import {SpecimenEditRoutingResolveService} from './specimen-edit-routing-resolve.service';

describe('SpecimenEdit routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SpecimenEditRoutingResolveService;
  let service: SpecimenEditService;
  let resultSpecimenEdit: ISpecimenEdit | undefined;

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
    routingResolveService = TestBed.inject(SpecimenEditRoutingResolveService);
    service = TestBed.inject(SpecimenEditService);
    resultSpecimenEdit = undefined;
  });

  describe('resolve', () => {
    it('should return ISpecimenEdit returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({body: {id}})));
      mockActivatedRouteSnapshot.params = {id: 123};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSpecimenEdit = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSpecimenEdit).toEqual({id: 123});
    });

    it('should return new ISpecimenEdit if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSpecimenEdit = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSpecimenEdit).toEqual(new SpecimenEdit());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({body: null as unknown as SpecimenEdit})));
      mockActivatedRouteSnapshot.params = {id: 123};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSpecimenEdit = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSpecimenEdit).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
