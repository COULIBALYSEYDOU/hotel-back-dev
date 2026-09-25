import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login/login.component').then(m => m.LoginComponent),
  },
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      {
        path: 'dashboard',
        loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent),
      },
      {
        path: 'reservations',
        loadChildren: () => import('./features/reservations/reservations.routes').then(m => m.routes),
      },
      {
        path: 'planning',
        loadChildren: () => import('./features/planning/planning.routes').then(m => m.routes),
      },
      {
        path: 'finance',
        loadChildren: () => import('./features/finance/finance.routes').then(m => m.routes),
      },
      {
        path: 'clientele',
        loadChildren: () => import('./features/clientele/clientele.routes').then(m => m.routes),
      },
      {
        path: 'restauration',
        loadChildren: () => import('./features/restauration/restauration.routes').then(m => m.routes),
      },
      {
        path: 'maintenance',
        loadChildren: () => import('./features/maintenance/maintenance.routes').then(m => m.routes),
      },
      {
        path: 'rh',
        loadChildren: () => import('./features/rh/rh.routes').then(m => m.routes),
      },
      {
        path: 'reporting',
        loadChildren: () => import('./features/reporting/reporting.routes').then(m => m.routes),
      },
      {
        path: 'admin',
        loadChildren: () => import('./features/admin/admin.routes').then(m => m.routes),
      },
    ],
  },
  { path: '**', redirectTo: '' },
];
