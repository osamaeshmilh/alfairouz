import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';

import {ReferringCenterPriceDetailComponent} from './referring-center-price-detail.component';

describe('ReferringCenterPrice Management Detail Component', () => {
  let comp: ReferringCenterPriceDetailComponent;
  let fixture: ComponentFixture<ReferringCenterPriceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReferringCenterPriceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {data: of({referringCenterPrice: {id: 123}})},
        },
      ],
    })
      .overrideTemplate(ReferringCenterPriceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ReferringCenterPriceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load referringCenterPrice on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.referringCenterPrice).toEqual(expect.objectContaining({id: 123}));
    });
  });
});
