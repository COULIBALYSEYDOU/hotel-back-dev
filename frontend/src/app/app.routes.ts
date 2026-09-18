import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent),
    canActivate: [authGuard]
  },
  {
    path: 'rh',
    loadChildren: () => import('./features/rh/rh.routes').then(m => m.routes),
    canActivate: [authGuard]
  },
  {
    path: 'admin',
    loadChildren: () => import('./features/admin/admin.routes').then(m => m.routes),
    canActivate: [authGuard]
  },
  {
    path: 'reservations',
    loadChildren: () => import('./features/reservations/reservations.routes').then(m => m.routes),
    canActivate: [authGuard]
  },
  {
    path: 'finance',
    loadChildren: () => import('./features/finance/finance.routes').then(m => m.routes),
    canActivate: [authGuard]
  },
  {
    path: 'clientele',
    loadChildren: () => import('./features/clientele/clientele.routes').then(m => m.routes),
    canActivate: [authGuard]
  },
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: '**',
    redirectTo: '/dashboard'
  }
];
