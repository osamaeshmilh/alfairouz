import {ComponentFixture, TestBed} from '@angular/core/testing';
import {HttpResponse} from '@angular/common/http';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {FormBuilder} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {of, Subject, from} from 'rxjs';

import {SpecimenEditService} from '../service/specimen-edit.service';
import {ISpecimenEdit, SpecimenEdit} from '../specimen-edit.model';

import {SpecimenEditUpdateComponent} from './specimen-edit-update.component';

describe('SpecimenEdit Management Update Component', () => {
  let comp: SpecimenEditUpdateComponent;
  let fixture: ComponentFixture<SpecimenEditUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let specimenEditService: SpecimenEditService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SpecimenEditUpdateComponent],
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
      .overrideTemplate(SpecimenEditUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SpecimenEditUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    specimenEditService = TestBed.inject(SpecimenEditService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const specimenEdit: ISpecimenEdit = {id: 456};

      activatedRoute.data = of({specimenEdit});
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(specimenEdit));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SpecimenEdit>>();
      const specimenEdit = {id: 123};
      jest.spyOn(specimenEditService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({specimenEdit});
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({body: specimenEdit}));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(specimenEditService.update).toHaveBeenCalledWith(specimenEdit);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SpecimenEdit>>();
      const specimenEdit = new SpecimenEdit();
      jest.spyOn(specimenEditService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({specimenEdit});
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({body: specimenEdit}));
      saveSubject.complete();

      // THEN
      expect(specimenEditService.create).toHaveBeenCalledWith(specimenEdit);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SpecimenEdit>>();
      const specimenEdit = {id: 123};
      jest.spyOn(specimenEditService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({specimenEdit});
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(specimenEditService.update).toHaveBeenCalledWith(specimenEdit);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
