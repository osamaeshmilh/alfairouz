import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SpecimenTypeService } from '../service/specimen-type.service';
import { ISpecimenType, SpecimenType } from '../specimen-type.model';

import { SpecimenTypeUpdateComponent } from './specimen-type-update.component';

describe('SpecimenType Management Update Component', () => {
  let comp: SpecimenTypeUpdateComponent;
  let fixture: ComponentFixture<SpecimenTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let specimenTypeService: SpecimenTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SpecimenTypeUpdateComponent],
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
      .overrideTemplate(SpecimenTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SpecimenTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    specimenTypeService = TestBed.inject(SpecimenTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const specimenType: ISpecimenType = { id: 456 };

      activatedRoute.data = of({ specimenType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(specimenType));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SpecimenType>>();
      const specimenType = { id: 123 };
      jest.spyOn(specimenTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ specimenType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: specimenType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(specimenTypeService.update).toHaveBeenCalledWith(specimenType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SpecimenType>>();
      const specimenType = new SpecimenType();
      jest.spyOn(specimenTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ specimenType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: specimenType }));
      saveSubject.complete();

      // THEN
      expect(specimenTypeService.create).toHaveBeenCalledWith(specimenType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SpecimenType>>();
      const specimenType = { id: 123 };
      jest.spyOn(specimenTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ specimenType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(specimenTypeService.update).toHaveBeenCalledWith(specimenType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
