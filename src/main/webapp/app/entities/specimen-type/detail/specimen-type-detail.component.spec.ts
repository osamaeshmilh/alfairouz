import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SpecimenTypeDetailComponent } from './specimen-type-detail.component';

describe('SpecimenType Management Detail Component', () => {
  let comp: SpecimenTypeDetailComponent;
  let fixture: ComponentFixture<SpecimenTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SpecimenTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ specimenType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SpecimenTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SpecimenTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load specimenType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.specimenType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
