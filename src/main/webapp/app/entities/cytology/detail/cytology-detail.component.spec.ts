import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CytologyDetailComponent } from './cytology-detail.component';

describe('Cytology Management Detail Component', () => {
  let comp: CytologyDetailComponent;
  let fixture: ComponentFixture<CytologyDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CytologyDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ cytology: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CytologyDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CytologyDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cytology on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.cytology).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
