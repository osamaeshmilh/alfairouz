import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';

import {SpecimenEditDetailComponent} from './specimen-edit-detail.component';

describe('SpecimenEdit Management Detail Component', () => {
  let comp: SpecimenEditDetailComponent;
  let fixture: ComponentFixture<SpecimenEditDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SpecimenEditDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {data: of({specimenEdit: {id: 123}})},
        },
      ],
    })
      .overrideTemplate(SpecimenEditDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SpecimenEditDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load specimenEdit on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.specimenEdit).toEqual(expect.objectContaining({id: 123}));
    });
  });
});
