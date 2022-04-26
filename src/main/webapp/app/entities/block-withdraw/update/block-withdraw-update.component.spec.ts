import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BlockWithdrawService } from '../service/block-withdraw.service';
import { IBlockWithdraw, BlockWithdraw } from '../block-withdraw.model';
import { ISpecimen } from 'app/entities/specimen/specimen.model';
import { SpecimenService } from 'app/entities/specimen/service/specimen.service';

import { BlockWithdrawUpdateComponent } from './block-withdraw-update.component';

describe('BlockWithdraw Management Update Component', () => {
  let comp: BlockWithdrawUpdateComponent;
  let fixture: ComponentFixture<BlockWithdrawUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let blockWithdrawService: BlockWithdrawService;
  let specimenService: SpecimenService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BlockWithdrawUpdateComponent],
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
      .overrideTemplate(BlockWithdrawUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BlockWithdrawUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    blockWithdrawService = TestBed.inject(BlockWithdrawService);
    specimenService = TestBed.inject(SpecimenService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Specimen query and add missing value', () => {
      const blockWithdraw: IBlockWithdraw = { id: 456 };
      const specimen: ISpecimen = { id: 99885 };
      blockWithdraw.specimen = specimen;

      const specimenCollection: ISpecimen[] = [{ id: 43353 }];
      jest.spyOn(specimenService, 'query').mockReturnValue(of(new HttpResponse({ body: specimenCollection })));
      const additionalSpecimen = [specimen];
      const expectedCollection: ISpecimen[] = [...additionalSpecimen, ...specimenCollection];
      jest.spyOn(specimenService, 'addSpecimenToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ blockWithdraw });
      comp.ngOnInit();

      expect(specimenService.query).toHaveBeenCalled();
      expect(specimenService.addSpecimenToCollectionIfMissing).toHaveBeenCalledWith(specimenCollection, ...additionalSpecimen);
      expect(comp.specimenSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const blockWithdraw: IBlockWithdraw = { id: 456 };
      const specimen: ISpecimen = { id: 86426 };
      blockWithdraw.specimen = specimen;

      activatedRoute.data = of({ blockWithdraw });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(blockWithdraw));
      expect(comp.specimenSharedCollection).toContain(specimen);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BlockWithdraw>>();
      const blockWithdraw = { id: 123 };
      jest.spyOn(blockWithdrawService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ blockWithdraw });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: blockWithdraw }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(blockWithdrawService.update).toHaveBeenCalledWith(blockWithdraw);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BlockWithdraw>>();
      const blockWithdraw = new BlockWithdraw();
      jest.spyOn(blockWithdrawService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ blockWithdraw });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: blockWithdraw }));
      saveSubject.complete();

      // THEN
      expect(blockWithdrawService.create).toHaveBeenCalledWith(blockWithdraw);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BlockWithdraw>>();
      const blockWithdraw = { id: 123 };
      jest.spyOn(blockWithdrawService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ blockWithdraw });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(blockWithdrawService.update).toHaveBeenCalledWith(blockWithdraw);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSpecimenById', () => {
      it('Should return tracked Specimen primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSpecimenById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
