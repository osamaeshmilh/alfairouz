import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RepresentativeDeliveryService } from '../service/representative-delivery.service';
import { IRepresentativeDelivery, RepresentativeDelivery } from '../representative-delivery.model';

import { RepresentativeDeliveryUpdateComponent } from './representative-delivery-update.component';

describe('RepresentativeDelivery Management Update Component', () => {
  let comp: RepresentativeDeliveryUpdateComponent;
  let fixture: ComponentFixture<RepresentativeDeliveryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let representativeDeliveryService: RepresentativeDeliveryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RepresentativeDeliveryUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(RepresentativeDeliveryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RepresentativeDeliveryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    representativeDeliveryService = TestBed.inject(RepresentativeDeliveryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const representativeDelivery: IRepresentativeDelivery = { id: 456 };

      activatedRoute.data = of({ representativeDelivery });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(representativeDelivery));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RepresentativeDelivery>>();
      const representativeDelivery = { id: 123 };
      jest.spyOn(representativeDeliveryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ representativeDelivery });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: representativeDelivery }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(representativeDeliveryService.update).toHaveBeenCalledWith(representativeDelivery);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RepresentativeDelivery>>();
      const representativeDelivery = new RepresentativeDelivery();
      jest.spyOn(representativeDeliveryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ representativeDelivery });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: representativeDelivery }));
      saveSubject.complete();

      // THEN
      expect(representativeDeliveryService.create).toHaveBeenCalledWith(representativeDelivery);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RepresentativeDelivery>>();
      const representativeDelivery = { id: 123 };
      jest.spyOn(representativeDeliveryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ representativeDelivery });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(representativeDeliveryService.update).toHaveBeenCalledWith(representativeDelivery);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
