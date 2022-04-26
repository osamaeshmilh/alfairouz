import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReferringCenterDetailComponent } from './referring-center-detail.component';

describe('ReferringCenter Management Detail Component', () => {
  let comp: ReferringCenterDetailComponent;
  let fixture: ComponentFixture<ReferringCenterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReferringCenterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ referringCenter: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ReferringCenterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ReferringCenterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load referringCenter on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.referringCenter).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
