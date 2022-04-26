import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PapSmearSaleDetailComponent } from './pap-smear-sale-detail.component';

describe('PapSmearSale Management Detail Component', () => {
  let comp: PapSmearSaleDetailComponent;
  let fixture: ComponentFixture<PapSmearSaleDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PapSmearSaleDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ papSmearSale: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PapSmearSaleDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PapSmearSaleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load papSmearSale on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.papSmearSale).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
