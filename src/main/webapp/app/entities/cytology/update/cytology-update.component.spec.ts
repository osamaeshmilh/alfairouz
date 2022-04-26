import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CytologyService } from '../service/cytology.service';
import { ICytology, Cytology } from '../cytology.model';

import { CytologyUpdateComponent } from './cytology-update.component';

describe('Cytology Management Update Component', () => {
  let comp: CytologyUpdateComponent;
  let fixture: ComponentFixture<CytologyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cytologyService: CytologyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CytologyUpdateComponent],
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
      .overrideTemplate(CytologyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CytologyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cytologyService = TestBed.inject(CytologyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cytology: ICytology = { id: 456 };

      activatedRoute.data = of({ cytology });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(cytology));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Cytology>>();
      const cytology = { id: 123 };
      jest.spyOn(cytologyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cytology });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cytology }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(cytologyService.update).toHaveBeenCalledWith(cytology);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Cytology>>();
      const cytology = new Cytology();
      jest.spyOn(cytologyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cytology });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cytology }));
      saveSubject.complete();

      // THEN
      expect(cytologyService.create).toHaveBeenCalledWith(cytology);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Cytology>>();
      const cytology = { id: 123 };
      jest.spyOn(cytologyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cytology });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cytologyService.update).toHaveBeenCalledWith(cytology);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
