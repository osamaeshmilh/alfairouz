import {ComponentFixture, TestBed} from '@angular/core/testing';
import {HttpResponse} from '@angular/common/http';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {FormBuilder} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {of, Subject, from} from 'rxjs';

import {ReferringCenterPriceService} from '../service/referring-center-price.service';
import {IReferringCenterPrice, ReferringCenterPrice} from '../referring-center-price.model';
import {ISpecimenType} from 'app/entities/specimen-type/specimen-type.model';
import {SpecimenTypeService} from 'app/entities/specimen-type/service/specimen-type.service';
import {ISize} from 'app/entities/size/size.model';
import {SizeService} from 'app/entities/size/service/size.service';
import {IReferringCenter} from 'app/entities/referring-center/referring-center.model';
import {ReferringCenterService} from 'app/entities/referring-center/service/referring-center.service';

import {ReferringCenterPriceUpdateComponent} from './referring-center-price-update.component';

describe('ReferringCenterPrice Management Update Component', () => {
  let comp: ReferringCenterPriceUpdateComponent;
  let fixture: ComponentFixture<ReferringCenterPriceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let referringCenterPriceService: ReferringCenterPriceService;
  let specimenTypeService: SpecimenTypeService;
  let sizeService: SizeService;
  let referringCenterService: ReferringCenterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReferringCenterPriceUpdateComponent],
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
      .overrideTemplate(ReferringCenterPriceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReferringCenterPriceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    referringCenterPriceService = TestBed.inject(ReferringCenterPriceService);
    specimenTypeService = TestBed.inject(SpecimenTypeService);
    sizeService = TestBed.inject(SizeService);
    referringCenterService = TestBed.inject(ReferringCenterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SpecimenType query and add missing value', () => {
      const referringCenterPrice: IReferringCenterPrice = {id: 456};
      const specimenType: ISpecimenType = {id: 99160};
      referringCenterPrice.specimenType = specimenType;

      const specimenTypeCollection: ISpecimenType[] = [{id: 32606}];
      jest.spyOn(specimenTypeService, 'query').mockReturnValue(of(new HttpResponse({body: specimenTypeCollection})));
      const additionalSpecimenTypes = [specimenType];
      const expectedCollection: ISpecimenType[] = [...additionalSpecimenTypes, ...specimenTypeCollection];
      jest.spyOn(specimenTypeService, 'addSpecimenTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({referringCenterPrice});
      comp.ngOnInit();

      expect(specimenTypeService.query).toHaveBeenCalled();
      expect(specimenTypeService.addSpecimenTypeToCollectionIfMissing).toHaveBeenCalledWith(
        specimenTypeCollection,
        ...additionalSpecimenTypes
      );
      expect(comp.specimenTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Size query and add missing value', () => {
      const referringCenterPrice: IReferringCenterPrice = {id: 456};
      const size: ISize = {id: 68357};
      referringCenterPrice.size = size;

      const sizeCollection: ISize[] = [{id: 28888}];
      jest.spyOn(sizeService, 'query').mockReturnValue(of(new HttpResponse({body: sizeCollection})));
      const additionalSizes = [size];
      const expectedCollection: ISize[] = [...additionalSizes, ...sizeCollection];
      jest.spyOn(sizeService, 'addSizeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({referringCenterPrice});
      comp.ngOnInit();

      expect(sizeService.query).toHaveBeenCalled();
      expect(sizeService.addSizeToCollectionIfMissing).toHaveBeenCalledWith(sizeCollection, ...additionalSizes);
      expect(comp.sizesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ReferringCenter query and add missing value', () => {
      const referringCenterPrice: IReferringCenterPrice = {id: 456};
      const referringCenter: IReferringCenter = {id: 66062};
      referringCenterPrice.referringCenter = referringCenter;

      const referringCenterCollection: IReferringCenter[] = [{id: 5574}];
      jest.spyOn(referringCenterService, 'query').mockReturnValue(of(new HttpResponse({body: referringCenterCollection})));
      const additionalReferringCenters = [referringCenter];
      const expectedCollection: IReferringCenter[] = [...additionalReferringCenters, ...referringCenterCollection];
      jest.spyOn(referringCenterService, 'addReferringCenterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({referringCenterPrice});
      comp.ngOnInit();

      expect(referringCenterService.query).toHaveBeenCalled();
      expect(referringCenterService.addReferringCenterToCollectionIfMissing).toHaveBeenCalledWith(
        referringCenterCollection,
        ...additionalReferringCenters
      );
      expect(comp.referringCentersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const referringCenterPrice: IReferringCenterPrice = {id: 456};
      const specimenType: ISpecimenType = {id: 19187};
      referringCenterPrice.specimenType = specimenType;
      const size: ISize = {id: 63996};
      referringCenterPrice.size = size;
      const referringCenter: IReferringCenter = {id: 31126};
      referringCenterPrice.referringCenter = referringCenter;

      activatedRoute.data = of({referringCenterPrice});
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(referringCenterPrice));
      expect(comp.specimenTypesSharedCollection).toContain(specimenType);
      expect(comp.sizesSharedCollection).toContain(size);
      expect(comp.referringCentersSharedCollection).toContain(referringCenter);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ReferringCenterPrice>>();
      const referringCenterPrice = {id: 123};
      jest.spyOn(referringCenterPriceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({referringCenterPrice});
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({body: referringCenterPrice}));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(referringCenterPriceService.update).toHaveBeenCalledWith(referringCenterPrice);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ReferringCenterPrice>>();
      const referringCenterPrice = new ReferringCenterPrice();
      jest.spyOn(referringCenterPriceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({referringCenterPrice});
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({body: referringCenterPrice}));
      saveSubject.complete();

      // THEN
      expect(referringCenterPriceService.create).toHaveBeenCalledWith(referringCenterPrice);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ReferringCenterPrice>>();
      const referringCenterPrice = {id: 123};
      jest.spyOn(referringCenterPriceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({referringCenterPrice});
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(referringCenterPriceService.update).toHaveBeenCalledWith(referringCenterPrice);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSpecimenTypeById', () => {
      it('Should return tracked SpecimenType primary key', () => {
        const entity = {id: 123};
        const trackResult = comp.trackSpecimenTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSizeById', () => {
      it('Should return tracked Size primary key', () => {
        const entity = {id: 123};
        const trackResult = comp.trackSizeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackReferringCenterById', () => {
      it('Should return tracked ReferringCenter primary key', () => {
        const entity = {id: 123};
        const trackResult = comp.trackReferringCenterById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
