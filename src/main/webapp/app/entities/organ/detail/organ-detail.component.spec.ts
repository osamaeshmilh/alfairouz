import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrganDetailComponent } from './organ-detail.component';

describe('Organ Management Detail Component', () => {
  let comp: OrganDetailComponent;
  let fixture: ComponentFixture<OrganDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrganDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ organ: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OrganDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrganDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load organ on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.organ).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
