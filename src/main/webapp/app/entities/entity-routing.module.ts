import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'dashboard',
        loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule),
      },
      {
        path: 'reports',
        loadChildren: () => import('./reports/reports.module').then(m => m.ReportsModule),
      },
      {
        path: 'biopsy',
        data: {pageTitle: 'alfairouzApp.biopsy.home.title'},
        loadChildren: () => import('./biopsy/biopsy.module').then(m => m.BiopsyModule),
      },
      {
        path: 'cytology',
        data: {pageTitle: 'alfairouzApp.cytology.home.title'},
        loadChildren: () => import('./cytology/cytology.module').then(m => m.CytologyModule),
      },
      {
        path: 'organ',
        data: { pageTitle: 'alfairouzApp.organ.home.title' },
        loadChildren: () => import('./organ/organ.module').then(m => m.OrganModule),
      },
      {
        path: 'specimen',
        data: { pageTitle: 'alfairouzApp.specimen.home.title' },
        loadChildren: () => import('./specimen/specimen.module').then(m => m.SpecimenModule),
      },
      {
        path: 'block-withdraw',
        data: { pageTitle: 'alfairouzApp.blockWithdraw.home.title' },
        loadChildren: () => import('./block-withdraw/block-withdraw.module').then(m => m.BlockWithdrawModule),
      },
      {
        path: 'patient',
        data: { pageTitle: 'alfairouzApp.patient.home.title' },
        loadChildren: () => import('./patient/patient.module').then(m => m.PatientModule),
      },
      {
        path: 'doctor',
        data: { pageTitle: 'alfairouzApp.doctor.home.title' },
        loadChildren: () => import('./doctor/doctor.module').then(m => m.DoctorModule),
      },
      {
        path: 'specimen-type',
        data: { pageTitle: 'alfairouzApp.specimenType.home.title' },
        loadChildren: () => import('./specimen-type/specimen-type.module').then(m => m.SpecimenTypeModule),
      },
      {
        path: 'size',
        data: { pageTitle: 'alfairouzApp.size.home.title' },
        loadChildren: () => import('./size/size.module').then(m => m.SizeModule),
      },
      {
        path: 'employee',
        data: { pageTitle: 'alfairouzApp.employee.home.title' },
        loadChildren: () => import('./employee/employee.module').then(m => m.EmployeeModule),
      },
      {
        path: 'pap-smear-sale',
        data: { pageTitle: 'alfairouzApp.papSmearSale.home.title' },
        loadChildren: () => import('./pap-smear-sale/pap-smear-sale.module').then(m => m.PapSmearSaleModule),
      },
      {
        path: 'representative-delivery',
        data: { pageTitle: 'alfairouzApp.representativeDelivery.home.title' },
        loadChildren: () => import('./representative-delivery/representative-delivery.module').then(m => m.RepresentativeDeliveryModule),
      },
      {
        path: 'referring-center',
        data: { pageTitle: 'alfairouzApp.referringCenter.home.title' },
        loadChildren: () => import('./referring-center/referring-center.module').then(m => m.ReferringCenterModule),
      },
      {
        path: 'extra',
        data: { pageTitle: 'alfairouzApp.extra.home.title' },
        loadChildren: () => import('./extra/extra.module').then(m => m.ExtraModule),
      },
      {
        path: 'expense',
        data: {pageTitle: 'alfairouzApp.expense.home.title'},
        loadChildren: () => import('./expense/expense.module').then(m => m.ExpenseModule),
      },
      {
        path: 'receipt',
        data: {pageTitle: 'alfairouzApp.receipt.home.title'},
        loadChildren: () => import('./receipt/receipt.module').then(m => m.ReceiptModule),
      },
      {
        path: 'referring-center-price',
        data: {pageTitle: 'alfairouzApp.referringCenterPrice.home.title'},
        loadChildren: () => import('./referring-center-price/referring-center-price.module').then(m => m.ReferringCenterPriceModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
