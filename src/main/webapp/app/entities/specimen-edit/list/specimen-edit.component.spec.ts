import {ComponentFixture, TestBed} from '@angular/core/testing';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {ActivatedRoute} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {of} from 'rxjs';

import {SpecimenEditService} from '../service/specimen-edit.service';

import {SpecimenEditComponent} from './specimen-edit.component';

describe('SpecimenEdit Management Component', () => {
    let comp: SpecimenEditComponent;
    let fixture: ComponentFixture<SpecimenEditComponent>;
    let service: SpecimenEditService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [RouterTestingModule.withRoutes([{
                path: 'specimen-edit',
                component: SpecimenEditComponent
            }]), HttpClientTestingModule],
            declarations: [SpecimenEditComponent],
            providers: [
                {
                    provide: ActivatedRoute,
                    useValue: {
                        data: of({
                            defaultSort: 'id,asc',
                        }),
                        queryParamMap: of(
                            jest.requireActual('@angular/router').convertToParamMap({
                                page: '1',
                                size: '1',
                                sort: 'id,desc',
                            })
                        ),
                    },
                },
            ],
        })
            .overrideTemplate(SpecimenEditComponent, '')
            .compileComponents();

        fixture = TestBed.createComponent(SpecimenEditComponent);
        comp = fixture.componentInstance;
        service = TestBed.inject(SpecimenEditService);

        const headers = new HttpHeaders();
        jest.spyOn(service, 'query').mockReturnValue(
            of(
                new HttpResponse({
                    body: [{id: 123}],
                    headers,
                })
            )
        );
    });

    it('Should call load all on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(service.query).toHaveBeenCalled();
        expect(comp.specimenEdits?.[0]).toEqual(expect.objectContaining({id: 123}));
    });

    it('should load a page', () => {
        // WHEN
        comp.loadPage(1);

        // THEN
        expect(service.query).toHaveBeenCalled();
        expect(comp.specimenEdits?.[0]).toEqual(expect.objectContaining({id: 123}));
    });

    it('should calculate the sort attribute for an id', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(service.query).toHaveBeenCalledWith(expect.objectContaining({sort: ['id,desc']}));
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
        // INIT
        comp.ngOnInit();

        // GIVEN
        comp.predicate = 'name';

        // WHEN
        comp.loadPage(1);

        // THEN
        expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({sort: ['name,desc', 'id']}));
    });
});
