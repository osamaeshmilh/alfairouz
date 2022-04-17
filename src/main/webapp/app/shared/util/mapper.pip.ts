import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'translateMe',
})
export class TranslateMePipe implements PipeTransform {
  translations: any;

  constructor() {
    this.translations = new Map<string, string>();
    this.translations.set('ROLE_ADMIN', 'مدير نظام');
    this.translations.set('ROLE_USER', 'مستخدم');
  }

  transform(value: any): unknown {
    return this.translations.get(value) ?? value;
  }
}

@Pipe({
  name: 'getColor',
})
export class GetColorPipe implements PipeTransform {
  colors: any;

  constructor() {
    this.colors = new Map<string, string>();

    this.colors.set('PENDING', 'text-orange');
    this.colors.set('APPROVED', 'text-green');
    this.colors.set('CANCELED', 'text-red');
    this.colors.set('REJECTED', 'text-red');

    this.colors.set('ACTIVE', 'text-green');
    this.colors.set('READY_NOT_ACTIVE', 'text-orange');
    this.colors.set('UNDER_DEVELOPMENT', 'text-orange');
  }

  transform(value: any): unknown {
    return this.colors.get(value) ?? value;
  }
}
