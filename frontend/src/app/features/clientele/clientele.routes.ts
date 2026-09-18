import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./clientele-dashboard/clientele-dashboard.component').then(m => m.ClienteleDashboardComponent)
  }
];
