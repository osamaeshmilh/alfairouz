import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BiopsyDetailComponent } from './biopsy-detail.component';

describe('Biopsy Management Detail Component', () => {
  let comp: BiopsyDetailComponent;
  let fixture: ComponentFixture<BiopsyDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BiopsyDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ biopsy: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BiopsyDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BiopsyDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load biopsy on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.biopsy).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
