import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {ISpecimenEdit} from '../specimen-edit.model';

@Component({
    selector: 'jhi-specimen-edit-detail',
    templateUrl: './specimen-edit-detail.component.html',
})
export class SpecimenEditDetailComponent implements OnInit {
    specimenEdit: ISpecimenEdit | null = null;

    constructor(protected activatedRoute: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.activatedRoute.data.subscribe(({specimenEdit}) => {
            this.specimenEdit = specimenEdit;
        });
    }

    previousState(): void {
        window.history.back();
    }
}
