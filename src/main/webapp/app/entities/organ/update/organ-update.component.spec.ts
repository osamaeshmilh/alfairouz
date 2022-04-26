import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrganService } from '../service/organ.service';
import { IOrgan, Organ } from '../organ.model';

import { OrganUpdateComponent } from './organ-update.component';

describe('Organ Management Update Component', () => {
  let comp: OrganUpdateComponent;
  let fixture: ComponentFixture<OrganUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let organService: OrganService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrganUpdateComponent],
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
      .overrideTemplate(OrganUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    organService = TestBed.inject(OrganService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const organ: IOrgan = { id: 456 };

      activatedRoute.data = of({ organ });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(organ));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Organ>>();
      const organ = { id: 123 };
      jest.spyOn(organService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organ });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organ }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(organService.update).toHaveBeenCalledWith(organ);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Organ>>();
      const organ = new Organ();
      jest.spyOn(organService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organ });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organ }));
      saveSubject.complete();

      // THEN
      expect(organService.create).toHaveBeenCalledWith(organ);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Organ>>();
      const organ = { id: 123 };
      jest.spyOn(organService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organ });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(organService.update).toHaveBeenCalledWith(organ);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
