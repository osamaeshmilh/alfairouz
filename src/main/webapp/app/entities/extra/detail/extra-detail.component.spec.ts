import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ExtraDetailComponent } from './extra-detail.component';

describe('Extra Management Detail Component', () => {
  let comp: ExtraDetailComponent;
  let fixture: ComponentFixture<ExtraDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExtraDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ extra: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ExtraDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ExtraDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load extra on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.extra).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
