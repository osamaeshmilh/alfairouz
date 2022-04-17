import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import {MatModule} from "./material-config/mat.module";

@NgModule({
  imports: [NgbModule, FontAwesomeModule, MatModule],
  exports: [FormsModule, CommonModule, NgbModule, InfiniteScrollModule, FontAwesomeModule, ReactiveFormsModule, TranslateModule, MatModule],
})
export class SharedLibsModule {}
