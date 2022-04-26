import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PapSmearSaleService } from '../service/pap-smear-sale.service';
import { IPapSmearSale, PapSmearSale } from '../pap-smear-sale.model';
import { IReferringCenter } from 'app/entities/referring-center/referring-center.model';
import { ReferringCenterService } from 'app/entities/referring-center/service/referring-center.service';

import { PapSmearSaleUpdateComponent } from './pap-smear-sale-update.component';

describe('PapSmearSale Management Update Component', () => {
  let comp: PapSmearSaleUpdateComponent;
  let fixture: ComponentFixture<PapSmearSaleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let papSmearSaleService: PapSmearSaleService;
  let referringCenterService: ReferringCenterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PapSmearSaleUpdateComponent],
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
      .overrideTemplate(PapSmearSaleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PapSmearSaleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    papSmearSaleService = TestBed.inject(PapSmearSaleService);
    referringCenterService = TestBed.inject(ReferringCenterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ReferringCenter query and add missing value', () => {
      const papSmearSale: IPapSmearSale = { id: 456 };
      const referringCenter: IReferringCenter = { id: 48373 };
      papSmearSale.referringCenter = referringCenter;

      const referringCenterCollection: IReferringCenter[] = [{ id: 15117 }];
      jest.spyOn(referringCenterService, 'query').mockReturnValue(of(new HttpResponse({ body: referringCenterCollection })));
      const additionalReferringCenters = [referringCenter];
      const expectedCollection: IReferringCenter[] = [...additionalReferringCenters, ...referringCenterCollection];
      jest.spyOn(referringCenterService, 'addReferringCenterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ papSmearSale });
      comp.ngOnInit();

      expect(referringCenterService.query).toHaveBeenCalled();
      expect(referringCenterService.addReferringCenterToCollectionIfMissing).toHaveBeenCalledWith(
        referringCenterCollection,
        ...additionalReferringCenters
      );
      expect(comp.referringCentersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const papSmearSale: IPapSmearSale = { id: 456 };
      const referringCenter: IReferringCenter = { id: 37255 };
      papSmearSale.referringCenter = referringCenter;

      activatedRoute.data = of({ papSmearSale });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(papSmearSale));
      expect(comp.referringCentersSharedCollection).toContain(referringCenter);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PapSmearSale>>();
      const papSmearSale = { id: 123 };
      jest.spyOn(papSmearSaleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ papSmearSale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: papSmearSale }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(papSmearSaleService.update).toHaveBeenCalledWith(papSmearSale);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PapSmearSale>>();
      const papSmearSale = new PapSmearSale();
      jest.spyOn(papSmearSaleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ papSmearSale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: papSmearSale }));
      saveSubject.complete();

      // THEN
      expect(papSmearSaleService.create).toHaveBeenCalledWith(papSmearSale);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PapSmearSale>>();
      const papSmearSale = { id: 123 };
      jest.spyOn(papSmearSaleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ papSmearSale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(papSmearSaleService.update).toHaveBeenCalledWith(papSmearSale);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackReferringCenterById', () => {
      it('Should return tracked ReferringCenter primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackReferringCenterById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
