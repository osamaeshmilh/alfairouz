import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReferringCenterService } from '../service/referring-center.service';
import { IReferringCenter, ReferringCenter } from '../referring-center.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { ReferringCenterUpdateComponent } from './referring-center-update.component';

describe('ReferringCenter Management Update Component', () => {
  let comp: ReferringCenterUpdateComponent;
  let fixture: ComponentFixture<ReferringCenterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let referringCenterService: ReferringCenterService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReferringCenterUpdateComponent],
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
      .overrideTemplate(ReferringCenterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReferringCenterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    referringCenterService = TestBed.inject(ReferringCenterService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const referringCenter: IReferringCenter = { id: 456 };
      const internalUser: IUser = { id: 24208 };
      referringCenter.internalUser = internalUser;

      const userCollection: IUser[] = [{ id: 42857 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [internalUser];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ referringCenter });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const referringCenter: IReferringCenter = { id: 456 };
      const internalUser: IUser = { id: 51695 };
      referringCenter.internalUser = internalUser;

      activatedRoute.data = of({ referringCenter });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(referringCenter));
      expect(comp.usersSharedCollection).toContain(internalUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ReferringCenter>>();
      const referringCenter = { id: 123 };
      jest.spyOn(referringCenterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ referringCenter });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: referringCenter }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(referringCenterService.update).toHaveBeenCalledWith(referringCenter);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ReferringCenter>>();
      const referringCenter = new ReferringCenter();
      jest.spyOn(referringCenterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ referringCenter });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: referringCenter }));
      saveSubject.complete();

      // THEN
      expect(referringCenterService.create).toHaveBeenCalledWith(referringCenter);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ReferringCenter>>();
      const referringCenter = { id: 123 };
      jest.spyOn(referringCenterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ referringCenter });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(referringCenterService.update).toHaveBeenCalledWith(referringCenter);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackUserById', () => {
      it('Should return tracked User primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
