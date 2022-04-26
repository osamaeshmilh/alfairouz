import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RepresentativeDeliveryDetailComponent } from './representative-delivery-detail.component';

describe('RepresentativeDelivery Management Detail Component', () => {
  let comp: RepresentativeDeliveryDetailComponent;
  let fixture: ComponentFixture<RepresentativeDeliveryDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RepresentativeDeliveryDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ representativeDelivery: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RepresentativeDeliveryDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RepresentativeDeliveryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load representativeDelivery on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.representativeDelivery).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
