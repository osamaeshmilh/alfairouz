import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BiopsyService } from '../service/biopsy.service';
import { IBiopsy, Biopsy } from '../biopsy.model';

import { BiopsyUpdateComponent } from './biopsy-update.component';

describe('Biopsy Management Update Component', () => {
  let comp: BiopsyUpdateComponent;
  let fixture: ComponentFixture<BiopsyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let biopsyService: BiopsyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BiopsyUpdateComponent],
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
      .overrideTemplate(BiopsyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BiopsyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    biopsyService = TestBed.inject(BiopsyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const biopsy: IBiopsy = { id: 456 };

      activatedRoute.data = of({ biopsy });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(biopsy));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Biopsy>>();
      const biopsy = { id: 123 };
      jest.spyOn(biopsyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ biopsy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: biopsy }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(biopsyService.update).toHaveBeenCalledWith(biopsy);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Biopsy>>();
      const biopsy = new Biopsy();
      jest.spyOn(biopsyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ biopsy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: biopsy }));
      saveSubject.complete();

      // THEN
      expect(biopsyService.create).toHaveBeenCalledWith(biopsy);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Biopsy>>();
      const biopsy = { id: 123 };
      jest.spyOn(biopsyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ biopsy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(biopsyService.update).toHaveBeenCalledWith(biopsy);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
