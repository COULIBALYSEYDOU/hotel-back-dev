import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./finance-dashboard/finance-dashboard.component').then(m => m.FinanceDashboardComponent)
  }
];
